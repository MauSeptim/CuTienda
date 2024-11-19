<template>
  <div class="register-product">
    <header class="encabezado">
      <CutiendaLeonVue />
      <h1>Registrar Producto</h1>
    </header>
    
    <form @submit.prevent="registerProduct" class="form-container">
      <div class="form-group">
        <label for="nombre_producto">Nombre del Producto</label>
        <input type="text" id="nombre_producto" v-model="product.nombre_producto" placeholder="Ej. Laptop Dell" required />
      </div>
      <div class="form-group">
        <label for="descripcion">Descripción</label>
        <textarea id="descripcion" v-model="product.descripcion" placeholder="Describe el producto" required></textarea>
      </div>
      <div class="form-group">
        <label for="tipo">Tipo</label>
        <input type="text" id="tipo" v-model="product.tipo" placeholder="Ej. Electrónica" required />
      </div>
      <div class="form-group">
        <label for="precio">Precio</label>
        <input type="number" step="0.01" id="precio" v-model="product.precio" placeholder="Ej. 1200.50" required />
      </div>
      <div class="form-group">
        <label for="foto">Foto</label>
        <input type="file" id="foto" @change="onFileChange" />
      </div>
      <div class="actions">
        <button type="button" @click="cancelar" class="cancel-button">Cancelar</button>
        <button
        type="button"
        @click="getLocation"
        class="location-button"
          :class="{ active: ubicacionObtenida }"
          :disabled="ubicacionObtenida"
        >
          {{ ubicacionObtenida ? "Ubicación Permitida" : "Permitir Ubicación" }}
        </button>
        <button type="submit" class="submit-button">Registrar Producto</button>
      </div>
    </form>
  </div>
</template>

<script>
import CutiendaLeonVue from "../icons/CutiendaLeon.vue";
import axios from "axios";
import Swal from "sweetalert2";

export default {
  name: "RegistrarProducto",
  components: {
    CutiendaLeonVue,
  },
  data() {
    return {
      product: {
        nombre_producto: "",
        id_usuario: this.$route.params.id,
        descripcion: "",
        tipo: "",
        precio: "",
        latitud: "",
        longitud: "",
        foto: null,
      },
      ubicacionObtenida: false,
    };
  },
  methods: {
    getLocation() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(this.setPosition, this.showError);
      } else {
        Swal.fire("Error", "Geolocalización no es soportada por este navegador.", "error");
      }
    },
    cancelar() {
      this.$router.go(-1);
    },
    setPosition(position) {
      this.product.latitud = position.coords.latitude;
      this.product.longitud = position.coords.longitude;
      Swal.fire({
        icon: "success",
        title: "Ubicación obtenida con éxito",
        timer: 1000,
        background: "transparent",
        showConfirmButton: false,
        position: "bottom-end",
      });
      this.ubicacionObtenida = true;
    },
    showError(error) {
      let message = "";
      switch (error.code) {
        case error.PERMISSION_DENIED:
          message = "El usuario denegó la solicitud de geolocalización.";
          break;
        case error.POSITION_UNAVAILABLE:
          message = "La información de ubicación no está disponible.";
          break;
        case error.TIMEOUT:
          message = "La solicitud para obtener la ubicación ha caducado.";
          break;
        default:
          message = "Error desconocido.";
      }
      Swal.fire("Error", message, "error");
    },
    onFileChange(event) {
      this.product.foto = event.target.files[0];
    },
    async registerProduct() {
      if (!this.ubicacionObtenida) {
        Swal.fire("Advertencia", "Debes permitir la ubicación antes de registrar el producto.", "warning");
        return;
      }

      const form = new FormData();
      form.append("nombre_producto", this.product.nombre_producto);
      form.append("id_usuario", this.product.id_usuario);
      form.append("descripcion", this.product.descripcion);
      form.append("tipo", this.product.tipo);
      form.append("precio", this.product.precio);
      form.append("latitud", this.product.latitud);
      form.append("longitud", this.product.longitud);
      form.append("foto", this.product.foto);

      try {
        await axios.post("http://localhost:8010/cutienda/api/productos/registro", form, {
          headers: { "Content-Type": "multipart/form-data" },
        });
        Swal.fire("Éxito", "Producto registrado con éxito", "success");
      } catch (error) {
        console.error("Error al registrar el producto:", error);
        Swal.fire("Error", "Error al registrar el producto", "error");
      }
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap");

.register-product {
  background-color: #002060;
  color: #333;
  font-family: "Inter", sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem;
  gap: 1.5rem;
  min-height: 100vh;
}

.encabezado {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.5rem;
  margin-bottom: 1.5rem;
}
.encabezado img {
  width: 7rem;
  height: 7rem;
}

h1 {
  font-size: 1.8rem;
  font-weight: 600;
  color: #f9f9f9;
}

.form-container {
  background: #ffffff;
  border-radius: 10px;
  padding: 1.5rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}

label {
  font-size: 0.9rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: #002060;
}

input,
textarea {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
}

textarea {
  resize: none;
  height: 100px;
}

.actions {
  margin-top: 2.5rem;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

button {
  padding: 0.75rem 1rem;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.location-button {
  background: #007bff;
  color: white;
}

.location-button.active {
  background: #28a745;
}

.submit-button {
  background: #28a745;
  color: white;
}
.cancel-button {
  background: #dc3545;
  color: white;
}

button:hover {
  opacity: 0.9;
}
</style>
