<template>
  <div class="padre">
    <div class="encabezado">
      <CutiendaLeon />
    </div>

    <div class="container">
      <div class="profile-section">
        <img :src="usuario.fotoUrl" alt="Imagen de Perfil" class="profile-image" id="fotoUrl">
        <h2>{{ usuario.nombre }} {{ usuario.apellidos }}</h2>
        <p>{{ usuario.correoElectronico }}</p>
        <p>{{ usuario.telefono }}</p>
        <label for="foto" class="upload-label">Cambiar Imagen</label>
        <input type="file" id="foto" @change="onFileChange" accept="image/png, image/jpeg" class="upload-input">
      </div>

      <div class="actions-section">
        <button @click="editarPerfil" class="action-button">Editar Perfil</button>
        <button @click="verNotificaciones" class="action-button">Ver Notificaciones</button>
        <button v-if="usuario.tipo === 'admin'" @click="registrarProducto" class="action-button">Registrar Producto</button>
      </div>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import CutiendaLeon from "../icons/CutiendaLeon.vue";
import axios from "axios";

export default {
  name: 'PaginaUsuario',
  components: {
    CutiendaLeon,
  },
  data() {
    return {
      usuario: {
        nombre: '',
        apellidos: '',
        correoElectronico: '',
        telefono: '',
        tipo: ''
      },
    };
  },
  methods: {
    editarPerfil() {
      this.$router.push({ name: 'EditarPerfil', params: { id: this.usuario.id } });
    },
    verNotificaciones() {
      this.$router.push({ name: 'Notificaciones', params: { id: this.usuario.id } });
    },
    registrarProducto() {
      this.$router.push({ name: 'RegistrarProducto' , params: { id: this.usuario.id } });
    },
    foto() {
      axios.get(`http://localhost:8011/api/cutienda/${this.usuario.id}/foto`)
      .then(response => {
        this.usuario.fotoUrl = response.data;
        return response.data;
      })
      .catch(error => {
        console.error(error);
      });
    }

  },
  mounted() {
    const form = new FormData();
    form.append('email', this.$route.params.email);
    axios.post("http://localhost:8011/api/cutienda/correo", form)
    .then(response => {
      this.usuario = response.data;
      this.foto();
        console.log("Usuario:", this.usuario);
      })
      .catch(error => {
        console.error("Error al obtener el usuario:", error);
      });
  },
};
</script>

<style scoped>
body {
  color: white;
  font-family: Arial, sans-serif;
  background-color: #002160;
  margin: 5%;
  padding: 0;
}

.encabezado {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background-color: #002160;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.container {
  margin-top: 20px;
  width: 100%;
  max-width: 900px;
  background: #002160;
  padding: 40px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 50px;
  border-radius: 8px;
}

.profile-section {
  width: 40%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.profile-section img {
  width: 200px;
  height: 200px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 20px;
}

.upload-label {
  background-color: #007bff;
  color: #fff;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.upload-label:hover {
  background-color: #0056b3;
}

.upload-input {
  display: none;
}

.actions-section {
  width: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.action-button {
  padding: 15px 30px;
  background-color: #28a745;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  font-weight: bold;
  transition: background-color 0.3s, box-shadow 0.3s;
}

.action-button:hover {
  background-color: #218838;
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.15);
}
.profile-image img {
  width: 3rem;
  height: 3rem;
}
</style>
