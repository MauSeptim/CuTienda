<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import CutiendaLeon from '../icons/CutiendaLeon.vue';
import BarraBusqueda from '../BarraBusqueda.vue';
import boton from '../Boton.vue';
import CuadroProducto from '../CuadroProducto.vue';
import { onBeforeRouteLeave } from 'vue-router';

// Variables reactivas
let productos = ref([]);
const productosCache = ref([]); // Cache de productos
const isLoading = ref(false); // Estado de carga
const router = useRouter();
const idUsuario = ref('');

// Obtener el ID del usuario
onMounted(() => {
  idUsuario.value = +router.currentRoute.value.params.id;
  
  // Intentar cargar productos desde sessionStorage
  const cachedProductos = sessionStorage.getItem('productosCache');
  if (cachedProductos) {
    productos.value = JSON.parse(cachedProductos); // Usar productos de sessionStorage si existen
  }
});

// Función para buscar productos en la API
const buscarProductos = async (query) => {
  try {
    isLoading.value = true; // Mostrar indicador de carga

    // Si ya hay productos en el caché y no hay búsqueda, usa el caché
    if (productosCache.value.length > 0 && !query) {
      productos.value = productosCache.value;
      isLoading.value = false;
      return;
    }

    // Construir la URL dependiendo de si hay query o no
    const url = query
      ? `http://localhost:8010/cutienda/api/productos/buscar?nombre=${query}`
      : 'http://localhost:8010/cutienda/api/productos';

    // Hacer la solicitud a la API
    const response = await axios.get(url);
    productos.value = response.data; // Actualizar la lista de productos
    productosCache.value = response.data; // Guardar en caché
    sessionStorage.setItem('productosCache', JSON.stringify(productosCache.value)); // Guardar productos en sessionStorage
  } catch (error) {
    alert('No se pudo obtener los productos.');
    console.error(error);
  } finally {
    isLoading.value = false; // Ocultar indicador de carga
  }
};

// Función para limpiar productos
const borrarProductos = () => {
  productos.value = [];
  productosCache.value = [];
  sessionStorage.removeItem('productosCache'); // Eliminar el caché de sessionStorage
};

// Al salir de la página, limpiar productos visibles
onBeforeRouteLeave(() => {
  borrarProductos();
});

// Navegación a otras páginas
const aPerfil = () => {
  router.push({ name: 'Usuario', params: { id: idUsuario.value } });
};

const notificacion = () => {
  router.push({ name: 'Notificaciones', params: { id: idUsuario.value } });
};
</script>

<template>
  <div>
    <nav class="navbar-imagen">
      <CutiendaLeon />
      <h1>CuTienda</h1>
    </nav>

    <div class="botones-superiores" id="botones-superiores">
      <boton :texto="'Notificaciones'" @click="notificacion" />
      <boton :texto="'Perfil'" @click="aPerfil" />
    </div>

    <BarraBusqueda class="barra" @buscar-productos="buscarProductos" />

    <!-- Indicador de carga -->
    <div v-if="isLoading" class="loading">
      <p>Cargando productos...</p>
    </div>

    <!-- Mostrar productos -->
    <div v-else class="producto-container">
      <CuadroProducto
        v-for="producto in productos"
        :key="producto.id_producto"
        :producto="producto"
        :id_user="idUsuario"
        class="iter-cuadro"
      />
    </div>
  </div>
</template>


<style>
/* Estilo base */
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap');
* {
  padding: 0;
  margin: 0;
  font-family: 'roboto', sans-serif;
}
body {
  background-color: #001f5f;
}

/* Contenedor de productos */
.producto-container {
  margin: auto auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 20px;
  width: 60%;
}

/* Barra de navegación */
.navbar-imagen {
  display: flex;
  flex-flow: column;
  position: relative;
  align-items: center;
  gap: 20px;
}
.navbar-imagen h1 {
  font-size: 3.5rem;
  color: #f5f5f5;
}
.navbar-imagen img {
  margin-top: 25px;
  width: 7rem;
  height: 7rem;
}

/* Barra de búsqueda */
.barra {
  margin: auto;
  position: relative;
  height: 1rem;
  width: 54%;
  margin-top: 3rem;
  margin-bottom: 7rem;
}

/* Indicador de carga */
.loading {
  text-align: center;
  font-size: 1.5rem;
  color: #ffffff;
}

/* Botones superiores */
.botones-superiores {
  position: absolute;
  top: 0;
  right: 0;
  display: flex;
  gap: 10px;
  margin: 20px;
}
</style>
