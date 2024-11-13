const productos = []; // Lista de productos (puedes llenar esto si tienes datos iniciales)
let resultados = [];
let paginaActual = 0;
const productosPorPagina = 10;

async function buscarProductos() {
    const busqueda = document.getElementById('buscar-input').value // Usa el nuevo ID del input

    try {
        // Realizar una llamada a la API para buscar productos
        console.log(busqueda)
        const response = await fetch(`http://localhost:8076/cutienda/producto/${busqueda}/nombre`);
        if (!response.ok) {
            throw new Error('Error al buscar productos');
        }

        // Obtener los productos desde la API
        resultados = await response.json();

        // Reiniciar a la primera página
        paginaActual = 0;

    } catch (error) {
        console.error(error);
        alert('Ocurrió un error al buscar los productos.'); // Manejo de errores
    }
}

async function mostrarProductos() {
    await buscarProductos();
    const contenedor = document.getElementById('productos');
    contenedor.innerHTML = '';
    console.log(resultados);

    // Calcular el índice inicial y final
    const inicio = paginaActual * productosPorPagina;
    const fin = inicio + productosPorPagina;
    const productosPagina = resultados.slice(inicio, fin);

    // Iterar sobre los productos y cargar sus imágenes
    for (const producto of productosPagina) {
        let imagenSrc;

        try {
            const fotoResponse = await fetch(producto.foto);
            if (fotoResponse.ok) {
                // Crear un blob de la imagen
                const blob = await fotoResponse.blob();
                imagenSrc = URL.createObjectURL(blob); // Crear un URL para el blob
            } else {
                // Si no se encuentra la foto, usar una imagen por defecto
                imagenSrc = '../static/incognito.webp'; // Cambiar a tu imagen por defecto
            }
        } catch (error) {
            console.error(error);
            imagenSrc = '../static/incognito.webp'; // Cambiar a tu imagen por defecto
        }

        // Crear el HTML del producto
        const productoHTML = `
            <div id="cuadro-producto" class="cuadro-producto">
                <img src="${imagenSrc}" alt="${producto.nombre}" class="imagen">
                <div id="detalles" class="detalles">
                    <table class="tabla">
                        <tbody>
                            <tr>
                                <p></p>
                                <td class="txt-info">Nombre: </td>
                                <td><span class="txt-resp">${producto.nombre}</span></td>
                            </tr>
                            <tr>
                                <p></p>
                                <td class="txt-info">Tipo: </td>
                                <td><span class="txt-resp">${producto.tipo}</span></td>
                            </tr>

                            <tr>
                                <td class="txt-info">Precio: </td>
                                <td><span class="txt-resp">${producto.precio}$</span></td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="caja-comentario">
                        <span id="comentarios-txt-info" class="txt-info">Descripción</span>
                        <textarea readonly name="coment" class="coment" placeholder="${producto.descripcion}$"></textarea>
                    </div>

                    <button type="button" class="boton" style="width: 100%; font-family: Verdana, Geneva, Tahoma, sans-serif;" onclick="window.location.href='/cutienda/comentarios/${producto.vendedor}';">Califica al Vendedor</button>
                    <button type="button" class="boton" style="width: 100%; font-family: Verdana, Geneva, Tahoma, sans-serif;">CONTACTAR VENDEDOR</button>
                </div>
            </div>
        `;

        // Insertar el HTML en el contenedor
        contenedor.insertAdjacentHTML('beforeend', productoHTML);
    }


}
