<script setup>
import { ref, defineProps, watch, defineEmits } from 'vue';
import axios from 'axios';

const props = defineProps(['limpiarSugerencias']); // Nueva propiedad
const emit = defineEmits(['buscar-productos']);
let barraBusquedaTxt = ref('');
let sugerencias = ref([]);

// Función para obtener sugerencias desde la API
const obtenerSugerencias = async (query) => {
  if (!query) {
    sugerencias.value = [];
    return;
  }
  try {
    const response = await axios.get(`http://localhost:8010/cutienda/api/productos/buscar?nombre=${query}`);
    // Filtramos las sugerencias para no mostrar si ya está igual que la búsqueda
    sugerencias.value = response.data
      .map((producto) => producto.nombreProducto)
      .filter((producto) => producto.toLowerCase().startsWith(query.toLowerCase()) && producto.toLowerCase() !== query.toLowerCase());
  } catch (error) {
    console.error('Error al obtener sugerencias:', error);
    sugerencias.value = [];
  }
};

// Observar cambios en el texto de búsqueda
watch(barraBusquedaTxt, (nuevoValor) => {
  obtenerSugerencias(nuevoValor);
});

// Observar cambios en `limpiarSugerencias` para vaciar las sugerencias
watch(() => props.limpiarSugerencias, (nuevoValor) => {
  if (nuevoValor) sugerencias.value = [];
});

// Emitir evento al hacer clic en la lupa
const onBuscarClick = () => {
  emit('buscar-productos', barraBusquedaTxt.value);
};

// Seleccionar sugerencia
const seleccionarSugerencia = (sugerencia) => {
  barraBusquedaTxt.value = sugerencia;
  emit('buscar-productos', sugerencia);
  sugerencias.value = [];
};
</script>

<template>
  <div class="barra-busqueda">
    <input 
      type="text" 
      v-model="barraBusquedaTxt" 
      placeholder="Busca cualquier producto"
    />
    <a href="#" class="search" @click.prevent="onBuscarClick">
      <svg class="buscar-icono" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" viewBox="0 0 50 50">
        <path d="M 21 3 C 11.601563 3 4 10.601563 4 20 C 4 29.398438 11.601563 37 21 37 C 24.355469 37 27.460938 36.015625 30.09375 34.34375 L 42.375 46.625 L 46.625 42.375 L 34.5 30.28125 C 36.679688 27.421875 38 23.878906 38 20 C 38 10.601563 30.398438 3 21 3 Z M 21 7 C 28.199219 7 34 12.800781 34 20 C 34 27.199219 28.199219 33 21 33 C 13.800781 33 8 27.199219 8 20 C 8 12.800781 13.800781 7 21 7 Z"></path>
      </svg>
      </a>
    <ul v-if="sugerencias.length" class="lista-sugerencias">
      <li 
        v-for="sugerencia in sugerencias" 
        :key="sugerencia" 
        class="sugerencia-item"
        @click="seleccionarSugerencia(sugerencia)"
      >
        {{ sugerencia }}
      </li>
    </ul>
  </div>
</template>


<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');
/* Contenedor principal */
.barra-busqueda {
  position: relative; /* Necesario para que el dropdown se posicione correctamente */
  width: 80%;
  height: 3em;
  padding: 25px 10px;
  display: flex;
  justify-content: space-between;
  background-color: #f5f5f5;
  align-items: center;
  border-radius: 39px;
  box-shadow: 0 30px 25px rgba(0, 0, 0, 0.3);
  border: 1px #062743 solid;
}

/* Input de búsqueda */
.barra-busqueda input {
  outline: none;
  font-family: "Roboto", sans-serif;
  border: none;
  padding: 20px 30px;
  font-size: 1.5rem;
  background: transparent;
  font-weight: 400;
  width: 100%;
}

/* Botón de búsqueda */
.search {
  text-decoration: none;
  outline: none;
  cursor: pointer;
  background-color: transparent;
  border: none;
}

.buscar-icono {
  height: 2.5rem;
  width: 2.5rem;
  margin-right: 30px;
}

.search:hover .buscar-icono {
  fill: #4471C4;
}

.search:active .buscar-icono {
  fill: #F5004F;
}

/* Dropdown de sugerencias */
.lista-sugerencias {
  position: absolute; /* Permite que el dropdown se salga del contenedor */
  top: 100%; /* Aparece justo debajo del input */
  left: 0;
  width: 90%;
  margin: 0;
  margin-left: 30px;
  list-style: none;
  max-height: 400px; /* Limita la altura del dropdown */
  overflow-y: auto; /* Permite scroll si hay muchas sugerencias */
  background-color: #fff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 1000; /* Asegura que el dropdown esté encima de otros elementos */
  background: black;
  color: #f9f9f9
}

/* Elementos individuales */
.sugerencia-item {
  cursor: pointer;
  background-color: black;
  padding: 1rem;
  margin: 0;
}

.sugerencia-item:hover {
  background-color: #333;
}
</style>
