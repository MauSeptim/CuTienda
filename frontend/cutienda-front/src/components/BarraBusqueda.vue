<script setup>
import { ref, defineEmits } from 'vue';

const emit = defineEmits(['buscar-productos'])
let barraBusquedaTxt = ref('');

// Emitir evento al hacer clic en el botón de búsqueda
const onBuscarClick = () => {
  // Emitir el texto de búsqueda al componente padre
  emit('buscar-productos', barraBusquedaTxt.value);
};
</script>

<template>
  <div class="barra-busqueda" id="barra-busqueda">
    <input 
      type="text" 
      name="buscar-input" 
      id="buscar-input" 
      placeholder="Busca cualquier producto"
      v-model="barraBusquedaTxt"
    >
    <a href="#" class="search" @click.prevent="onBuscarClick">
      <svg class="buscar-icono" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="100" height="100" viewBox="0 0 50 50">
        <path d="M 21 3 C 11.601563 3 4 10.601563 4 20 C 4 29.398438 11.601563 37 21 37 C 24.355469 37 27.460938 36.015625 30.09375 34.34375 L 42.375 46.625 L 46.625 42.375 L 34.5 30.28125 C 36.679688 27.421875 38 23.878906 38 20 C 38 10.601563 30.398438 3 21 3 Z M 21 7 C 28.199219 7 34 12.800781 34 20 C 34 27.199219 28.199219 33 21 33 C 13.800781 33 8 27.199219 8 20 C 8 12.800781 13.800781 7 21 7 Z"></path>
      </svg>
    </a>
  </div>
</template>

<style>
#buscar-input {
    width: 100%;
    position: relative;
    font-size: 1.5rem;
}
.barra-busqueda {
    width: 63vw;
    height: 10vh;
    padding: 25px 10px;
    display: flex;
    justify-content: space-between;
    background-color: #f5f5f5;
    align-items: center;
    border-radius: 30px;
    box-shadow: 0 30px 25px rgba(0, 0, 0, 0.3);
    border: 1px #062743 solid;
}

.barra-busqueda input {
    outline: none;
    border: none;
    padding: 10px 30px;
    background: transparent;
    font-weight: 500;
}

.search {
    text-decoration: none;
    outline: none;
    cursor: pointer;
    background-color: transparent;
    border: none;
}

.buscar-icono {
    width: fit-content;
    height: 6vh;
    margin-right: 2vh;
    object-fit: contain;
}

.search:hover .buscar-icono {
    fill: #4471C4;
}

.search:active .buscar-icono {
    fill: #F5004F;
}

</style>

