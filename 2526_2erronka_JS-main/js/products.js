// Función que se ejecuta al pulsar el botón
function Piezak() {
    const contenedor = document.getElementById('Piezak');
    contenedor.innerHTML = '';

    fetch('datu_basea/piezak.json')
        .then(response => response.json())
        .then(data => {
            const ul = document.createElement('ul');

            data.forEach(pieza => {
                const li = document.createElement('li');
                // Aquí usamos los nombres exactos del JSON
                li.textContent = `${pieza.Izena} - ${pieza.Deskribapena} | Peso: ${pieza.Pisua}g | Precio: ${pieza.Prezioa} | Stock: ${pieza.Stock}`;
                ul.appendChild(li);
            });

            contenedor.appendChild(ul);
        })
        .catch(error => {
            contenedor.textContent = 'Error al cargar los datos: ' + error;
        });
}



function create_products_list(){
  //Irakurri JSko datuak LocalStoretik eta sortu taula dinamikoki bat
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
    tablaBody.innerHTML = '';

    const piezak = JSON.parse(localStorage.getItem('piezak'));

    piezak.forEach(pieza => {
        const fila = document.createElement('tr');

        fila.innerHTML = `
            <td>${pieza.Izena}</td>
            <td>${pieza.Deskribapena}</td>
            <td>${pieza.Pisua}</td>
            <td>${pieza.Prezioa}</td>
            <td>${pieza.Stock}</td>
            <td>
                <button onclick="verPieza(${pieza.Id_pieza})">
                    Ver
                </button>
            </td>
        `;

        tablaBody.appendChild(fila);
    });
}







function verPieza(id) {
    const piezak = JSON.parse(localStorage.getItem('piezak'));
    const piezaSeleccionada = piezak.find(p => p.Id_pieza === id);

    localStorage.setItem('piezaSeleccionada', JSON.stringify(piezaSeleccionada));

    window.location.href = 'pieza.html';
}




// Debo crear una tabla dinámica en el archivo "products.js" que esté dedicada a los datos que tenga almacenados en el ‘LocalStorage’. Esta tabla dinámica tiene que aparecer en una página web (HTML) concreta. Y en dicha tabla habrá, por ejemplo, los nombres en una columna, las descripciones en otra columna, el peso en otra columna, y así sucesivamente con todos. No obstante, los datos correspondientes de cada pieza tienen que estar en la misma fila.

// Después, tengo que leer los datos que tengo guardados en el archivo "products.js" mediante el 'LocalStorage', y te explico el porqué: 
// Voy a tener algunos botones que me dirijan a otros HTML, (los botones me dirigirán a unos HTML que están dedicados para cada pieza concreta).
// Necesito almacenar o guardar la información que voy a necesitar en otras páginas mediante el 'LocalStorage', debido a que los voy a tener que usar posteriormente en dichos HTML que correspondan a cada pieza. Es decir, que cuando pulse un botón y me dirija a otra página, los datos que se hayan guardado previamente en el 'LocalStorage' tienen que estar presentes sin borrarse.