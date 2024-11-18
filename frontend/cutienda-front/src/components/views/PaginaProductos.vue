<script setup>
import {ref} from 'vue'
import CutiendaLeon from '../icons/CutiendaLeon.vue';
import BarraBusqueda from '../BarraBusqueda.vue';
import boton from '../Boton.vue';
import axios from 'axios';
import CuadroProducto from '../CuadroProducto.vue';
import { useRouter } from 'vue-router';
import { onMounted } from 'vue';
import { onBeforeRouteLeave } from 'vue-router';

let productos = ref([]);
const router = useRouter()
const idUsuario = ref('');

onMounted(() => {
  idUsuario.value = router.currentRoute.value.params.id;
});


const borrarProductos = () => {
  productos.value = [];
};

onBeforeRouteLeave(() => {
  borrarProductos();
});

// Función para buscar productos en la API
const buscarProductos = async (query) => {
  try {

    if (productos.value.length > 0) {
      borrarProductos();
    } 

    const url = query 
      ? `http://localhost:8010/cutienda/api/productos/buscar?nombre=${query}` 
      : 'http://localhost:8010/cutienda/api/productos';  // Si no hay query, obtienes todos los productos

    const response = await axios.get(url);   

    productos.value = response.data;  // Actualiza la lista de productos
    console.log(productos.value);
  } catch (error) {
    alert('No se pudo obtener todos los productos');
    console.error(error);
  }
};

const aPerfil = () => { 
  router.push({name: 'Usuario', params: {id: idUsuario.value}});
};

const notificacion = () => { 
  router.push({name: 'Notificaciones', params: {id: idUsuario.value}});
};

</script>

<template>
  <div>
    <nav class="navbar-imagen">
      <CutiendaLeon/>
      <h1>CuTienda</h1>
    </nav>
    <div class="botones-superiores" id="botones-superiores">
      <boton :texto="'Notificaciones'" @click="notificacion"/>
      <boton :texto="'Perfil'" @click="aPerfil"/>
    </div>
  
    <BarraBusqueda class="barra" @buscar-productos="buscarProductos"/>
  
    <div class="producto-container">
  
      <CuadroProducto v-for="producto in productos" 
                      :key="producto.id_producto" 
                      :producto="producto"
                      :id_user="idUsuario"
                      class="iter-cuadro"/>
    </div>  
  </div>
</template>

<style >
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap');

* {
  padding: 0;
  margin: 0;
  font-family: 'roboto', sans-serif;
}

body {
  background-color: #001F5F;
}

.producto-container {
  margin: auto auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 20px;
  width: 60%;
}

.navbar-imagen {
  display: flex;
  flex-flow: column ;
  position: relative;
  align-items: center;
  gap: 20px;
}
.navbar-imagen img {
  margin-top: 30px;
  width: 8rem;
  height: 8rem;
}
.navbar-imagen h1 {
  font-size: 3.5rem;
  color: #f5f5f5;
}

.barra {
  margin: auto;
  position: relative;
  height: 1rem;
  width: 54%;
  margin-top: 3rem;
  margin-bottom: 7rem;
}
.barra div input {
  font-size: 2px;
}

.botones-superiores {
  position: absolute;
  top: 0;      /* Alínea al borde superior */
  right: 0;    /* Alínea al borde derecho */
  display: flex;
  gap: 10px;   /* Espacio entre los botones */
  margin: 20px; /* Margen para separarlos un poco del borde */
}
.botones-superiores button {
  font-size: 1.2rem;
  padding: 1rem;
}


</style>
