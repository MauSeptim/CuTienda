<template>
  <div class="register-product">
    <CutiendaLeonVue />
    <h1>Registrar Producto</h1>
    <form @submit.prevent="registerProduct">
      <div class="form-group">
        <label for="nombre_producto">Nombre del Producto</label>
        <input type="text" id="nombre_producto" v-model="product.nombre_producto" required />
      </div>
      <div class="form-group">
        <label for="descripcion">Descripción</label>
        <textarea id="descripcion" v-model="product.descripcion" required></textarea>
      </div>
      <div class="form-group">
        <label for="tipo">Tipo</label>
        <input type="text" id="tipo" v-model="product.tipo" required />
      </div>
      <div class="form-group">
        <label for="precio">Precio</label>
        <input type="number" step="0.01" id="precio" v-model="product.precio" required />
      </div>
      <div class="form-group">
        <label for="foto">Foto</label>
        <input type="file" id="foto" @change="onFileChange" />
      </div>
      <div class="botones">
        <button type="button" @click="getLocation" :class="{x:ubicacionObtenida}">Permitir Ubicación</button>
        <button type="submit">Registrar Producto</button>
      </div>
    </form>
  </div>
</template>

<script>
import CutiendaLeonVue from "../icons/CutiendaLeon.vue";
import axios from "axios";
import Swal from "sweetalert2";

export default {
  name: 'RegistrarProducto',
  components: {
    CutiendaLeonVue,
  },
  data() {
    return {
      product: {
        nombre_producto: '',
        id_usuario: '',
        descripcion: '',
        tipo: '',
        precio: '',
        latitud: '',
        longitud: '',
        foto: null
      },
      ubicacionObtenida: false
    };
  },
  methods: {
    getLocation() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(this.setPosition, this.showError);
      } else {
        Swal.fire('Error', 'Geolocalización no es soportada por este navegador.', 'error');
      }
    },
    setPosition(position) {
      this.product.latitud = position.coords.latitude;
      this.product.longitud = position.coords.longitude;
      console.log(this.product.latitud, this.product.longitud);
      Swal.fire({
        icon: 'success',
        title: 'Ubicación obtenida con éxito',
        color: '#28a745',
        timer: 1000,
        background: "transparent",
        showConfirmButton: false,
        position: 'bottom-end',
      });
      this.ubicacionObtenida = true;
    },
    showError(error) {
      let message = '';
      switch(error.code) {
        case error.PERMISSION_DENIED:
          message = 'El usuario denegó la solicitud de geolocalización.';
          break;
        case error.POSITION_UNAVAILABLE:
          message = 'La información de ubicación no está disponible.';
          break;
        case error.TIMEOUT:
          message = 'La solicitud para obtener la ubicación del usuario ha caducado.';
          break;
        case error.UNKNOWN_ERROR:
          message = 'Se ha producido un error desconocido.';
          break;
      }
      Swal.fire('Error', message, 'error');
    },
    onFileChange(event) {
      this.product.foto = event.target.files[0];
    },
    async registerProduct() {
      const form = new FormData();
      form.append('nombre_producto', this.product.nombre_producto);
      form.append('id_usuario', this.product.id_usuario);
      form.append('descripcion', this.product.descripcion);
      form.append('tipo', this.product.tipo);
      form.append('precio', this.product.precio);
      form.append('latitud', this.product.latitud);
      form.append('longitud', this.product.longitud);
      form.append('foto', this.product.foto);

      try {
        const response = await axios.post('http://localhost:8011/api/cutienda/productos', form, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        Swal.fire('Éxito', 'Producto registrado con éxito', 'success');
      } catch (error) {
        console.error('Error al registrar el producto:', error);
        Swal.fire('Error', 'Error al registrar el producto', 'error');
      }
    }
  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

.register-product {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 18px;
  gap: 30px;
  background-color: #002060; /* Fondo azul */
  font-family: 'Roboto', sans-serif;
  color: #fff; /* Texto blanco */
}

.register-product img {
    width: 7rem;
    height: 7rem;
}

.encabezado {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.encabezado span {
  font-size: 24px;
  font-weight: bold;
  margin-left: 10px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input, textarea {
  width: 100%;
  padding: 10px;
  border-radius: 4px;
  box-sizing: border-box;
  font-size: 1rem;
}

button {
  padding: 10px 20px;
  background-color: #28a745; /* Verde */
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  margin-top: 10px;
}

button:hover {
  background-color: #218838;
}
.botones {
  display: flex;
  flex-flow: column;
  gap: 10px;
}
.x {
  display: none;
}
</style>