// --- 1. INICIALIZACIÓN Y CARGA DE DATOS ---

async function inicializarPaginaProductos() {
    let piezak = JSON.parse(localStorage.getItem('piezak'));

    if (!piezak) {
        try {
            const response = await fetch('datu_basea/piezak.json');
            if (!response.ok) throw new Error("Ezinda JSONa kargatu");
            piezak = await response.json();
            localStorage.setItem('piezak', JSON.stringify(piezak));
        } catch (error) {
            console.error("Errorea datuekin:", error);
            return;
        }
    }

    mostrarTablaPiezak();
}

/**
 * Muestra la tabla restando del stock lo que ya está en el carrito
 */
function mostrarTablaPiezak() {
    const tablaBody = document.querySelector('#tablaPiezak tbody');
    if (!tablaBody) return;
    
    tablaBody.innerHTML = '';
    const piezak = JSON.parse(localStorage.getItem('piezak'));
    const carrito = JSON.parse(localStorage.getItem('carritoActual')) || [];

    if (piezak) {
        piezak.forEach(pieza => {
            const itemEnCarrito = carrito.find(item => item.id === pieza.Id_pieza);
            const cantidadEnCarrito = itemEnCarrito ? itemEnCarrito.cantidad : 0;
            const stockDisponible = pieza.Stock - cantidadEnCarrito;

            const fila = document.createElement('tr');
            fila.innerHTML = `
                <td class="fw-bold">${pieza.Izena}</td>
                <td>${pieza.Deskribapena}</td>
                <td>${pieza.Pisua} g</td>
                <td>${pieza.Prezioa}€</td>
                <td>
                    <span class="badge ${stockDisponible > 0 ? 'bg-success' : 'bg-danger'}">
                        ${stockDisponible > 0 ? stockDisponible : 'Stock gabe'}
                    </span>
                </td>
                <td class="text-center">
                    <button onclick="verPieza(${pieza.Id_pieza})" 
                            class="btn-ver" 
                            ${stockDisponible <= 0 ? 'disabled style="opacity:0.5; cursor:not-allowed;"' : ''}>
                        Ikusi
                    </button>
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

// --- 2. DETALLE DE PRODUCTO ---

function fill_product_info() {
    const pieza = JSON.parse(localStorage.getItem('piezaSeleccionada'));
    const carrito = JSON.parse(localStorage.getItem('carritoActual')) || [];
    if (!pieza) return;

    const itemEnCarrito = carrito.find(item => item.id === pieza.Id_pieza);
    const cantidadYaEnCarrito = itemEnCarrito ? itemEnCarrito.cantidad : 0;
    const stockDisponibleReal = pieza.Stock - cantidadYaEnCarrito;

    const infoContainer = document.getElementById('info');
    if (infoContainer) {
        infoContainer.innerHTML = `
            <h3>${pieza.Izena}</h3>
            <ul>
                <li><strong>Prezioa</strong>: ${pieza.Prezioa} €</li>
                <li><strong>Pisua</strong>: ${pieza.Pisua} g</li>
                <li><strong>Stock totala</strong>: ${pieza.Stock}</li>
                <li><strong>Eskuragarri</strong>: <span class="text-primary fw-bold">${stockDisponibleReal}</span></li>
            </ul>
        `;
    }

    const descContainer = document.getElementById('description');
    if (descContainer) {
        descContainer.innerHTML = `<h2>Deskribapena</h2><p>${pieza.Deskribapena}</p>`;
    }

    const buyBtnContainer = document.getElementById('buy_product');
    if (buyBtnContainer) {
        if (stockDisponibleReal <= 0) {
            buyBtnContainer.innerHTML = `<button class="btn btn-secondary w-100" disabled>Stock gabe</button>`;
        } else {
            buyBtnContainer.innerHTML = `<button onclick="comprar()" class="btn btn-warning fw-bold w-100">Erosi</button>`;
        }
    }
}

// --- 3. GESTIÓN DEL CARRITO ---

function comprar() {
    const inputElement = document.getElementById('canti');
    const valorRaw = inputElement.value; 
    const pieza = JSON.parse(localStorage.getItem('piezaSeleccionada'));
    let carrito = JSON.parse(localStorage.getItem('carritoActual')) || [];

    if (pieza) {
        if (!/^\d+$/.test(valorRaw)) {
            mostrarAviso("Sartu zenbaki oso baliodun bat");
            return;
        }

        const cantidadPedida = parseInt(valorRaw);
        const itemEnCarrito = carrito.find(item => item.id === pieza.Id_pieza);
        const cantidadYaEnCarrito = itemEnCarrito ? itemEnCarrito.cantidad : 0;
        const stockDisponibleReal = pieza.Stock - cantidadYaEnCarrito;

        if (cantidadPedida <= 0) {
            mostrarAviso("Kopuruak 0 baino handiagoa izan behar du");
            return;
        }

        if (cantidadPedida > stockDisponibleReal) {
            mostrarAviso(`Ezin duzu kopuru hori gehitu. Eskuragarri: ${stockDisponibleReal}`);
            return;
        }

        const existeIndex = carrito.findIndex(item => item.id === pieza.Id_pieza);

        if (existeIndex !== -1) {
            carrito[existeIndex].cantidad += cantidadPedida;
            carrito[existeIndex].subtotal = carrito[existeIndex].cantidad * carrito[existeIndex].precioUnidad;
        } else {
            carrito.push({
                id: pieza.Id_pieza,
                nombre: pieza.Izena,
                precioUnidad: parseFloat(pieza.Prezioa),
                cantidad: cantidadPedida,
                subtotal: parseFloat(pieza.Prezioa) * cantidadPedida,
                envio: 5.00
            });
        }

        localStorage.setItem('carritoActual', JSON.stringify(carrito));

        if (typeof Toastify === 'function') {
            Toastify({
                text: "Produktua saskira gehitu da!",
                duration: 1500,
                style: { background: "linear-gradient(to right, #00b09b, #96c93d)" }
            }).showToast();
        }

        setTimeout(() => {
            window.location.href = 'payment_1.html';
        }, 1000);
    }
}

// --- 4. PASARELA DE PAGO ---

function fill_payment_cards1() {
    const carrito = JSON.parse(localStorage.getItem('carritoActual')) || [];
    const contenedorCards = document.getElementById('fill_cards');
    const displaySubtotal = document.getElementById('price');
    const displayTotal = document.getElementById('totalPrice');
    const displayItemsCount = document.getElementById('items');

    if (!contenedorCards) return;

    if (carrito.length === 0) {
        contenedorCards.innerHTML = '<p class="text-center py-4">Saskia hutsik dago.</p>';
        return;
    }

    let htmlContenido = "";
    let subtotalAcumulado = 0;

    carrito.forEach((datos, index) => {
        let nombreImg = datos.nombre.replace(/\s+/g, '-');
        let extension = ".jpg";
        const productosJpeg = ["Engranaje-helikoidala", "Lotura-plaka", "Zulaturiko-plaka"];
        if (productosJpeg.includes(nombreImg)) extension = ".jpeg";

        const rutaImagen = `assets/img/products/${nombreImg}${extension}`;
        subtotalAcumulado += datos.subtotal;

        htmlContenido += `
            <div class="row main align-items-center border-top border-bottom py-2">
                <div class="col-2">
                    <img class="img-fluid" src="${rutaImagen}" alt="${datos.nombre}" 
                         onerror="this.src='assets/img/migual-altuna-logo-header.png'">
                </div>
                <div class="col">
                    <div class="row text-muted">Produktua</div>
                    <div class="row">${datos.nombre}</div>
                </div>
                <div class="col text-center">
                    <span class="px-3 border">${datos.cantidad}</span>
                </div>
                <div class="col text-end">
                    ${datos.subtotal.toFixed(2)}€ 
                    <span class="close" onclick="eliminarDelCarrito(${index})" style="cursor:pointer; color:red; font-weight:bold; margin-left:10px;">&#10005;</span>
                </div>
            </div>
        `;
    });

    contenedorCards.innerHTML = htmlContenido;
    if (displayItemsCount) displayItemsCount.innerText = `${carrito.length} items`;
    if (displaySubtotal) displaySubtotal.innerText = `${subtotalAcumulado.toFixed(2)}€`;
    if (displayTotal) displayTotal.innerText = `${(subtotalAcumulado + 5).toFixed(2)}€`;
}

function eliminarDelCarrito(index) {
    let carrito = JSON.parse(localStorage.getItem('carritoActual')) || [];
    carrito.splice(index, 1);
    localStorage.setItem('carritoActual', JSON.stringify(carrito));
    fill_payment_cards1();
}

// --- 5. FINALIZAR COMPRA ---

function go2print() {
    const carrito = JSON.parse(localStorage.getItem('carritoActual')) || [];
    const inventario = JSON.parse(localStorage.getItem('piezak'));

    if (carrito.length > 0 && inventario) {
        const inventarioActualizado = inventario.map(pieza => {
            const itemEnCarrito = carrito.find(item => item.id === pieza.Id_pieza);
            if (itemEnCarrito) {
                pieza.Stock = pieza.Stock - itemEnCarrito.cantidad;
            }
            return pieza;
        });

        localStorage.setItem('piezak', JSON.stringify(inventarioActualizado));
        localStorage.removeItem('carritoActual');
        window.location.href = 'print.html';
    } else {
        mostrarAviso("Saskia hutsik dago!");
    }
}

// --- 6. UTILIDADES Y SESIÓN ---

function mostrarAviso(mensaje) {
    if (typeof Toastify === 'function') {
        Toastify({
            text: mensaje,
            duration: 3000,
            style: { background: "#e44d26" }
        }).showToast();
    } else {
        alert(mensaje);
    }
}

function bukatuSaioa() {
    localStorage.removeItem('piezaSeleccionada');
    localStorage.removeItem('carritoActual');
    localStorage.removeItem('username');
    localStorage.removeItem('piezak'); 
    window.location.href = 'index.html';
}

/**
 * NUEVA FUNCIÓN: Rellena el nombre del usuario en la pantalla final
 */
function bete_print() {
    const nombreUsuario = localStorage.getItem('username');
    const spanUser = document.getElementById('user');
    
    if (spanUser && nombreUsuario) {
        spanUser.innerText = nombreUsuario;
    } else if (spanUser) {
        spanUser.innerText = "Bezeroa"; // Nombre por defecto si algo falla
    }
}