// --- FUNCIONES ORIGINALES ---
function Piezak() {
    const contenedor = document.getElementById('Piezak');
    contenedor.innerHTML = '';
    fetch('datu_basea/piezak.json')
        .then(response => response.json())
        .then(data => {
            const ul = document.createElement('ul');
            data.forEach(pieza => {
                const li = document.createElement('li');
                li.textContent = `${pieza.Izena} - ${pieza.Deskribapena} | Peso: ${pieza.Pisua}g | Precio: ${pieza.Prezioa} | Stock: ${pieza.Stock}`;
                ul.appendChild(li);
            });
            contenedor.appendChild(ul);
        })
        .catch(error => { contenedor.textContent = 'Error: ' + error; });
}

function cargarPiezasEnLocalStorage() {
    fetch('datu_basea/piezak.json')
        .then(response => response.json())
        .then(data => {
            localStorage.setItem('piezak', JSON.stringify(data));
            console.log('Piezas guardadas en LocalStorage');
        })
        .catch(error => console.error('Error:', error));
}

function mostrarTablaPiezak() {
    const tablaBody = document.querySelector('#tablaPiezak tbody');
    if (!tablaBody) return;
    tablaBody.innerHTML = '';
    const piezak = JSON.parse(localStorage.getItem('piezak'));

    if (piezak) {
        piezak.forEach(pieza => {
            const fila = document.createElement('tr');
            fila.innerHTML = `
                <td>${pieza.Izena}</td>
                <td>${pieza.Deskribapena}</td>
                <td>${pieza.Pisua}</td>
                <td>${pieza.Prezioa}</td>
                <td>${pieza.Stock}</td>
                <td>
                    <button onclick="verPieza(${pieza.Id_pieza})" class="btn-ver">Ver</button>
                </td>
            `;
            tablaBody.appendChild(fila);
        });
    }
}

function verPieza(id) {
    const piezak = JSON.parse(localStorage.getItem('piezak'));
    const piezaSeleccionada = piezak.find(p => p.Id_pieza === id);

    if (piezaSeleccionada) {
        localStorage.setItem('piezaSeleccionada', JSON.stringify(piezaSeleccionada));
        window.location.href = 'product-details.html';
    }
}

function fill_product_info() {
    const pieza = JSON.parse(localStorage.getItem('piezaSeleccionada'));
    if (!pieza) return;

    const infoContainer = document.getElementById('info');
    if (infoContainer) {
        infoContainer.innerHTML = `
            <h3>${pieza.Izena}</h3>
            <ul>
                <li><strong>Prezioa</strong>: ${pieza.Prezioa} €</li>
                <li><strong>Pisua</strong>: ${pieza.Pisua} g</li>
                <li><strong>Stock</strong>: ${pieza.Stock}</li>
            </ul>
        `;
    }

    const descContainer = document.getElementById('description');
    if (descContainer) {
        descContainer.innerHTML = `
            <h2>Deskribapena</h2>
            <p>${pieza.Deskribapena}</p>
        `;
    }

    const buyBtnContainer = document.getElementById('buy_product');
    if (buyBtnContainer) {
        buyBtnContainer.innerHTML = `
            <button onclick="comprar()" class="btn btn-warning fw-bold w-100">Erosi</button>
        `;
    }
}

// --- COMPRA Y VALIDACIONES ---
function comprar() {
    const inputElement = document.getElementById('canti');
    const valorRaw = inputElement.value; 
    const pieza = JSON.parse(localStorage.getItem('piezaSeleccionada'));

    if (pieza) {
        if (!/^\d+$/.test(valorRaw)) {
            mostrarAviso("Sartu zenbaki oso baliodun bat (ez letrak, ez ikurrik, ezta kmarik ere)");
            return;
        }

        const cantidadPedida = parseInt(valorRaw);

        if (cantidadPedida <= 0) {
            mostrarAviso("Kopuruak 0 baino handiagoa izan behar du");
            return;
        }

        if (cantidadPedida > pieza.Stock) {
            mostrarAviso(`Ez dago nahikoa kopuru (Stock: ${pieza.Stock})`);
            return;
        }

        const compra = {
            id: pieza.Id_pieza,
            nombre: pieza.Izena,
            precioUnidad: parseFloat(pieza.Prezioa),
            cantidad: cantidadPedida,
            subtotal: parseFloat(pieza.Prezioa) * cantidadPedida,
            envio: 5.00
        };

        localStorage.setItem('carritoActual', JSON.stringify(compra));

        Toastify({
            text: "Eskerrik asko! Ordainketara bideratzen...",
            duration: 1500,
            style: { background: "linear-gradient(to right, #00b09b, #96c93d)" }
        }).showToast();

        setTimeout(() => {
            window.location.href = 'payment_1.html';
        }, 1000);
    }
}

function mostrarAviso(mensaje) {
    Toastify({
        text: mensaje,
        duration: 4000,
        gravity: "top",
        position: "right",
        style: { background: "linear-gradient(to right, #e44d26, #f16529)", color: "white" }
    }).showToast();
}

// --- PASARELA DE PAGO (Aquí se cambia la imagen) ---
function fill_payment_cards1() {
    const datos = JSON.parse(localStorage.getItem('carritoActual'));
    const contenedorCards = document.getElementById('fill_cards');
    const displaySubtotal = document.getElementById('price');
    const displayTotal = document.getElementById('totalPrice');
    const displayItemsCount = document.getElementById('items');

    if (datos && contenedorCards) {
        // 1. Lógica para generar el nombre del archivo de imagen
        // Reemplazamos espacios por guiones: "Allen torlojua" -> "Allen-torlojua"
        let nombreFormateado = datos.nombre.replace(/\s+/g, '-');
        
        // 2. Determinar la extensión correcta (.jpeg para placas y engranaje helikoidala, .jpg para el resto)
        let extension = ".jpg";
        const productosJpeg = ["Engranaje-helikoidala", "Lotura-plaka", "Zulaturiko-plaka"];
        
        if (productosJpeg.includes(nombreFormateado)) {
            extension = ".jpeg";
        }

        const rutaImagen = `assets/img/products/${nombreFormateado}${extension}`;

        // 3. Renderizar la tarjeta con la imagen dinámica
        contenedorCards.innerHTML = `
            <div class="row main align-items-center border-top border-bottom py-2">
                <div class="col-2">
                    <img class="img-fluid" src="${rutaImagen}" alt="${datos.nombre}" 
                         onerror="this.src='assets/img/migual-altuna-logo-header.png'">
                </div>
                <div class="col">
                    <div class="row text-muted">Produktuaren Izena</div>
                    <div class="row">${datos.nombre}</div>
                </div>
                <div class="col">
                    <span class="px-3 border">${datos.cantidad}</span>
                </div>
                <div class="col text-end">${datos.precioUnidad}€ <span class="close">&#10005;</span></div>
            </div>
        `;

        displayItemsCount.innerText = `${datos.cantidad} items`;
        displaySubtotal.innerText = `${datos.subtotal.toFixed(2)}€`;
        const totalFinal = datos.subtotal + datos.envio;
        displayTotal.innerText = `${totalFinal.toFixed(2)}€`;
    }
}

// --- FINALIZAR Y BAJAR STOCK ---
function go2print() {
    const compra = JSON.parse(localStorage.getItem('carritoActual'));
    const inventario = JSON.parse(localStorage.getItem('piezak'));

    if (compra && inventario) {
        const inventarioActualizado = inventario.map(pieza => {
            if (pieza.Id_pieza === compra.id || pieza.Izena === compra.nombre) {
                pieza.Stock = pieza.Stock - compra.cantidad;
            }
            return pieza;
        });

        localStorage.setItem('piezak', JSON.stringify(inventarioActualizado));
        window.location.href = 'print.html';
    }
}

function bete_print() {
    const nombreUsuario = localStorage.getItem('usuarioActual') || "Bezero";
    const userSpan = document.getElementById('user');
    if (userSpan) {
        userSpan.innerText = nombreUsuario;
    }
}