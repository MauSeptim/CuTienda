<script setup>
import axios from 'axios';
import Swal from 'sweetalert2';
import { defineProps, ref, onMounted } from 'vue';

const props = defineProps({
    producto: {
        type: Object,
        required: true
    }
});
var producto = ref(props.producto);
var nombreUsuario = ref('');
var foto = ref(null);

let fetchFoto = () => {
    axios.get(`http://localhost:8011/api/cutienda/${producto.value.id_usuario}`)
    .then(response => {
        nombreUsuario.value = `${response.data.nombre} ${response.data.apellidos}`;
    })
    .catch(error => {
        Swal.fire('Error', 'Error al obtener el nombre del usuario', 'error');
        console.error(error);
    });
    // Verificar que el ID del producto está definido antes de hacer la solicitud
    if (producto.value && producto.value.id_producto) {
        axios.get(`http://localhost:8010/cutienda/api/productos/foto/${producto.value.id_producto}`, { responseType: 'arraybuffer' })
        .then((response) => {
            // Convertir los datos binarios en base64 para que pueda mostrarse en la imagen
            const base64Image = btoa(String.fromCharCode(...new Uint8Array(response.data)));
            producto.value.foto = `data:image/jpeg;base64,${base64Image}`;
        }).catch((error) => {
            console.error("Error al obtener la foto:", error);
        });
    } else {
        console.error("El ID del producto no está definido.");
    }

};

// Cargar la foto al montar el componente
onMounted(fetchFoto);
</script>

<template>
<div class="container">
	<div class="table">
		<div class="table-header">
			<div class="header__item"><a id="name" class="filter__link" href="#">PRODUCTO</a></div>
			<div class="header__item"><a id="wins" class="filter__link filter__link--number" href="#">NOMBRE</a></div>
			<div class="header__item"><a id="draws" class="filter__link filter__link--number" href="#">PRECIO</a></div>
			<div class="header__item"><a id="losses" class="filter__link filter__link--number" href="#">VENDEDOR</a></div>
			<div class="header__item"><a id="total" class="filter__link filter__link--number" href="#">DESCRIPCION</a></div>
		</div>
		<div class="table-content">	
			<div class="table-row">		
				<div class="table-data"><img class="foto" :src="producto.foto" alt=""></div>
				<div class="table-data">{{ producto.nombreProducto }}</div>
				<div class="table-data">{{ producto.precio }}</div>
				<div class="table-data">{{ nombreUsuario }}</div>
				<div class="table-data">{{producto.descripcion}}</div>
			</div>
		</div>	
	</div>
</div>
</template>

<style>
.foto {
    max-width: 100%;
    max-height: 100%;
    object-fit: contain;
} 
/* ---------------------------------------------------------------*/
@import url('https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700');

:root {
    --base-spacing-unit: 24px;
    --alf-spacing-unit: calc(var(--base-spacing-unit) / 2); /* Usando CSS custom property */
    
    --color-alpha: #1772FF;
    --color-form-highlight: #001a52;
}

.foto {
    width: 12rem;
    height: 12rem;
}

*, *:before, *:after {
    box-sizing: border-box;
}

body {
    padding: var(--base-spacing-unit); /* Usando variable */
    font-family: 'Source Sans Pro', sans-serif;
    margin: 0;
}

h1, h2, h3, h4, h5, h6 {
    margin: 0;
}

.container {
    max-width: 1000px;
    width: 63vw;
    margin-right: auto;
    margin-left: auto;
    display: flex;
    justify-content: center;
    align-items: center;
    box-shadow: inset;
}

.table {
    width: 100%;
    border: 1px solid var(--color-form-highlight); /* Usando variable */
    box-shadow: 30px 28px 20px rgba(0, 0, 0, 0.2);
}

.table:hover .table-header, .table-row{
    transition: 0.3s ease;
    background-color: #262626;
    cursor: pointer;
}
.table:hover {
    transition: 0.3s ease;
    border: #18ebff 3px solid;
}
.table:hover a{
    transition: 0.9s ease;
    color: #18ebff;
}

.table-header {
    display: flex;
    width: 100%;
    background: #000;
    padding: calc(var(--alf-spacing-unit) * 1.5) 0; /* Usando variable */
}

.table-row {
    display: flex;
    width: 100%;
    padding: calc(var(--alf-spacing-unit) * 1.5) 0; /* Usando variable */
}

.table-row:nth-of-type(odd) {
    background: var(--color-form-highlight); /* Usando variable */
}

.table-data, .header__item {
    flex: 1 1 20%;
    display: flex;
    align-items: center ;
    justify-content: space-around;
    text-align: center;
}

.table-data {
    font-size: 1.5rem;
    color: white;
}

.header__item {
    text-transform: uppercase;
}

.filter__link {
    color: white;
    text-decoration: none;
    position: relative;
    display: inline-block;
    padding-left: var(--base-spacing-unit); /* Usando variable */
    padding-right: var(--base-spacing-unit); /* Usando variable */
}

.filter__link::after {
    content: '';
    position: absolute;
    right: calc(-1 * var(--alf-spacing-unit) * 1.5); /* Usando variable */
    color: white;
    font-size: var(--alf-spacing-unit); /* Usando variable */
    top: 50%;
    transform: translateY(-50%);
}

.filter__link.desc::after {
    content: '(desc)';
}

.filter__link.asc::after {
    content: '(asc)';
}

</style>