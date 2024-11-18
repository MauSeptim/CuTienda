<script setup>
import {ref} from 'vue'
import CutiendaLeon from '../icons/CutiendaLeon.vue';
import BarraBusqueda from '../BarraBusqueda.vue';
import boton from '../Boton.vue';
import axios from 'axios';
import CuadroProducto from '../CuadroProducto.vue';
import { useRouter } from 'vue-router';
import { onMounted } from 'vue';

let productos = ref([]);
let email = ref('');
const router = useRouter()

onMounted(() => {
  email.value = router.currentRoute.value.params.email;
});

// Función para buscar productos en la API
const buscarProductos = async (query) => {
  try {
    const url = query 
      ? `http://localhost:8010/cutienda/api/productos/buscar?nombre=${query}` 
      : 'http://localhost:8010/cutienda/api/productos';  // Si no hay query, obtienes todos los productos

    const response = await axios.get(url);

    productos.value = response.data;  // Actualiza la lista de productos
  } catch (error) {
    alert('No se pudo obtener todos los productos');
    console.error(error);
  }
};

const aPerfil = () => {
    router.push({name: 'Usuario', params: {email: email.value}});
};

const notificacion = () => {
    let form = new FormData();
    form.append('email', email.value);

    axios.post('http://localhost:8011/api/cutienda/correo', form
        .then(response => {
          // Manejar la respuesta exitosa aquí
          router.push({name: 'Notificaciones', params: {id: response.data.id}});
          console.log(response.data);
        })
        .catch(error => {
          // Manejar el error aquí
          console.error(error);

          if (error.response) {
            console.error(error.response.data);
            Swal.fire('Error', error.response.data.error || 'Error durante la autenticación', 'error');
          }
        }));
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
                      :key="producto.id" 
                      :producto="producto"
                      class="iter-cuadro"/>
    </div>  
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
  justify-content: center;
}
.iter-cuadro {
  margin-top: 60px;
}

.navbar-imagen {
  display: flex;
  flex-flow: column ;
  position: relative;
  align-items: center;
}
.navbar-imagen img {
  width: 12rem;
  height: 12rem;
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

</style>
