<template>
  <div>
    <div class="encabezado">
        <CutiendaLeon class="logo2"/>
    </div>

    <div class="container">
      <div class="profile-section">
        <img :src="profileImageUrl" alt="Imagen de Perfil" class="profile-image" id="profileImage" />
        <label for="profile-image">Cambiar Imagen</label>
        <input type="file" id="profile-image" @change="previewImage" accept="image/*" />
      </div>

      <div class="form-section">
        <div class="tabs">
          <label>
            <input type="radio" value="admin" v-model="role" /> ADMINISTRADOR
          </label>
          <label>
            <input type="radio" value="client" v-model="role" /> CLIENTE
          </label>
        </div>

        <form @submit.prevent="submitForm">
          <div class="form-group">
            <label for="nombre">Nombre</label>
            <input type="text" id="nombre" v-model="formData.nombre" required />
          </div>

          <div class="form-group">
            <label for="apellidos">Apellidos</label>
            <input type="text" id="apellidos" v-model="formData.apellidos" required />
          </div>

          <div class="form-group">
            <label for="correoElectronico">Correo electrónico</label>
            <input type="email" id="correoElectronico" v-model="formData.correoElectronico" required />
          </div>

          <div class="form-group">
            <label for="telefono">Teléfono</label>
            <input type="tel" id="telefono" v-model="formData.telefono" required />
          </div>

          <div class="form-group">
            <label for="contrasena">Contraseña</label>
            <input type="password" id="contrasena" v-model="formData.contrasena" required />
          </div>

          <div class="form-group">
            <label for="confirmacionContraseña">Corroborar contraseña</label>
            <input type="password" id="confirmacionContraseña" v-model="formData.confirmacionContraseña" required />
          </div>

          <label>
            <input type="checkbox" @click="togglePassword" /> SHOW
          </label>

          <div class="buttons">
            <button type="submit" class="save">Guardar Cambios</button>
            <router-link to="/login" class="home">Inicio</router-link>
            <button type="button" @click="confirmDelete" class="delete">Eliminar Cuenta</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import CutiendaLeon from "../icons/CutiendaLeon.vue";

export default {
  data() {
    return {
      profileImageUrl: '/path/to/default/image.png',
      formData: {
        nombre: '',
        apellidos: '',
        correoElectronico: '',
        telefono: '',
        contrasena: '',
        confirmacionContraseña: ''
      },
      role: 'client' // O 'admin', dependiendo del usuario
    };
  },
  methods: {
    submitForm() {
      // Aquí enviarás los datos con Axios o Fetch.
      console.log(this.formData);
      // Implementa la lógica de envío
      // axios.post('/cutienda/perfil/actualizar', this.formData)
    },
    confirmDelete() {
      Swal.fire({
        title: "¿Estás seguro de que quieres eliminar tu cuenta?",
        text: "Esta acción no se puede deshacer.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#dc3545",
        confirmButtonText: "Eliminar",
        cancelButtonText: "Cancelar"
      }).then((result) => {
        if (result.isConfirmed) {
          // Aquí puedes enviar la solicitud para eliminar la cuenta
          console.log("Cuenta eliminada");
          // axios.post('/cutienda/perfil/eliminar')
        }
      });
    },
    togglePassword() {
      const passwordInput = document.getElementById("contrasena");
      const checkPasswordInput = document.getElementById("confirmacionContraseña");
      if (passwordInput.type === "password") {
        passwordInput.type = "text";
        checkPasswordInput.type = "text";
      } else {
        passwordInput.type = "password";
        checkPasswordInput.type = "password";
      }
    },
    previewImage(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.profileImageUrl = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    }
  },
  mounted() {
    const successMessage = ''; // Mensaje de éxito aquí
    const errorMessage = ''; // Mensaje de error aquí

    if (successMessage) {
      Swal.fire({
        position: "top-end",
        icon: "success",
        title: "¡Guardado con éxito!",
        text: successMessage,
        timer: 3000,
        showConfirmButton: false
      });
    }

    if (errorMessage) {
      Swal.fire({
        position: "top-end",
        icon: "error",
        title: "Error de actualización",
        text: errorMessage,
        timer: 3000,
        showConfirmButton: false
      });
    }
  }
};
</script>

<style scoped>
/* Aquí puedes pegar tu CSS original para adaptarlo a Vue */
body {
  color: white;
  font-family: Arial, sans-serif;
  background-color: #002160;
  margin: 5%;
  padding: 0;
}

.encabezado {
  position: fixed;
  top: 0;
  width: 90%;
  background-color: #002160;
  padding: 40px 0;
  text-align: center;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.encabezado img {
  width: 250px;
  height: auto;
}

.container {
  margin-top: 200px;
  width: 90%;
  background: #002160;
  padding: 40px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 50px;
}

.logo2 {
  width: 5vw;
  height: 5vh;
  padding: 10px;
  margin-bottom: -100px; /* Espacio adicional debajo del logo */
}

/* El resto de tu CSS */
</style>
