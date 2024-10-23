<script setup>
import {ref} from 'vue'
//import CuadroProducto from './ /components/CuadroProducto.vue';
import CutiendaLeon from '../icons/CutiendaLeon.vue';
import BarraBusqueda from '../BarraBusqueda.vue';
import boton from '../Boton.vue';
import axios from 'axios';
import CuadroProducto from '../CuadroProducto.vue';

let productos = ref([]);

// Función para buscar productos en la API
const buscarProductos = async (query) => {
  try {
    const response = await axios.get(`http://localhost:8076/cutienda/producto/${query}/nombre`);
    productos.value = response.data;  // Actualiza la lista de productos
  } catch (error) {
    alert('No se pudo obtener todos los productos');
    console.error(error);
  }
};

</script>

<template>
    
    <nav class="navbar-imagen">
      <CutiendaLeon/>
      <h1>CuTienda</h1>
    </nav>
    <div class="botones-superiores" id="botones-superiores">
      <boton :texto="'Notificaciones'"/>
      <boton :texto="'Perfil'"/>
    </div>
  
    <BarraBusqueda class="barra" @buscar-productos="buscarProductos"/>
  
    <div class="producto-container">
  
      <CuadroProducto v-for="producto in productos" 
                      :key="producto.id" 
                      :producto="producto"
                      class="iter-cuadro"/>
    </div>  
</template>

<style >
* {
  padding: 0;
  margin: 0;
}

body {
  background-color: #001F5F;
}

.producto-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center
}

.navbar-imagen {
  display: flex;
  flex-flow: column ;
  position: relative;
  align-items: center;
}
.navbar-imagen img {
  width: 20vw;
  height: 41vh;
}
.navbar-imagen h1 {
  margin-top: 5vh;
  font-size: 3rem;
  color: #f5f5f5;
}

.barra {
  margin: auto;
  position: relative;
  margin-top: 10vh;
}

.botones-superiores {
  position: absolute;
  top: 0;      /* Alínea al borde superior */
  right: 0;    /* Alínea al borde derecho */
  display: flex;
  gap: 10px;   /* Espacio entre los botones */
  margin: 20px; /* Margen para separarlos un poco del borde */

}

.iter-cuadro {
  margin-bottom: -38vh;
}
</style>
