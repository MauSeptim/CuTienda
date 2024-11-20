<script setup>
import axios from 'axios';
import Swal from 'sweetalert2';
import { useRouter } from 'vue-router';
import { defineProps, ref, onMounted } from 'vue';

const props = defineProps({
    producto: {
        type: Object,
        required: true
    },
    id_user: {
        type: Number,
        required: true
    }
});
var producto = ref(props.producto);
var id_user = ref(props.id_user);
var nombreUsuario = ref('');
var router = useRouter();
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
};

const verComentarios = () => {
    router.push({ name: 'Comentarios', params: {
        id_producto: producto.value.id_producto,
        id_vendedor: producto.value.id_usuario,
        user_id: id_user.value 
    }});
};

// Cargar la foto al montar el componente
onMounted(fetchFoto);
</script>

<template>
  <div class="container">
    <div class="table" @click="verComentarios">
      <div class="table-header">
        <div class="header__item"><a id="name" class="filter__link" href="#">PRODUCTO</a></div>
        <div class="header__item"><a id="wins" class="filter__link filter__link--number" href="#">NOMBRE</a></div>
        <div class="header__item"><a id="draws" class="filter__link filter__link--number" href="#">PRECIO</a></div>
        <div class="header__item"><a id="losses" class="filter__link filter__link--number" href="#">VENDEDOR</a></div>
        <div class="header__item"><a id="total" class="filter__link filter__link--number" href="#">DESCRIPCIÓN</a></div>
      </div>
      <div class="table-content">
        <div class="table-row">
          <div class="table-data"><img class="foto" :src="producto.fotoBase64" alt="Imagen del producto"></div>
          <div class="table-data">{{ producto.nombreProducto }}</div>
          <div class="table-data">{{ producto.precio}}</div>
          <div class="table-data">{{ nombreUsuario }}</div>
          <div class="table-data">{{ producto.descripcion }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

:root {
  --base-spacing-unit: 24px;
  --alf-spacing-unit: calc(var(--base-spacing-unit) / 2); /* Usando CSS custom property */
  --color-alpha: #1772FF;
  --color-form-highlight: #001a52;
}

.foto {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 8px;
}

*, *:before, *:after {
  box-sizing: border-box;
}

body {
  padding: var(--base-spacing-unit); /* Usando variable */
  font-family: 'Source Sans Pro', sans-serif;
  margin: 0;
}

.container {
  width: 100%;
  padding: 20px;
}

.table {
  width: 100%;
  border-collapse: collapse;
  background-color: #1a1a1a;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
}

.table:hover {
  transform: translateY(-10px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
}

.table-header {
  display: flex;
  justify-content: space-around;
  text-align: center;
  background-color: var(--color-form-highlight);
  padding: 10px 0;
}

.header__item {
  flex: 1;
  text-transform: uppercase;
  font-weight: bold;
  color: white;
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
}

.table-content {
  display: flex;
  flex-direction: column;
}

.table-row {
  display: flex;
  justify-content: space-around;
  align-items: center;
  text-align: center;
  padding: 10px 0;
  border-bottom: 1px solid #ccc;
}

.table-data {
  flex: 1;
  font-size: 1.2rem;
  color: white;
  word-wrap: break-word; /* Asegura que las palabras no salgan del div */
}

@media (max-width: 768px) {
  .table-header, .table-row {
    flex-direction: column;
    align-items: flex-start;
  }

  .header__item, .table-data {
    text-align: left;
    padding: 10px 0;
  }

  .foto {
    width: 100%;
    height: auto;
  }
}

@media (max-width: 480px) {
  .table-header, .table-row {
    flex-direction: column;
    align-items: flex-start;
  }

  .header__item, .table-data {
    text-align: left;
    padding: 10px 0;
  }

  .table-row {
    grid-template-columns: 1fr 1fr;
    gap: 10px;
  }

  .table-data {
    text-align: left;
  }

  .foto {
    grid-column: 1 / -1;
    width: 100%;
    height: auto;
  }
}
</style>