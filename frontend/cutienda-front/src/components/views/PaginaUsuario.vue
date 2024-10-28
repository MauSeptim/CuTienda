<template>
  <div class="padre">
    <div class="encabezado">
        <CutiendaLeon/>
        <span>Cutienda</span>
    </div>

    <div class="container">
      <!-- AQUI EMPIEZA LO MIO MAU -->
       <div class="rol" id="rol">
        <label>
            <input type="radio" value="admin" v-model="role" /> ADMINISTRADOR
          </label>
          <label>
            <input type="radio" value="client" v-model="role" /> CLIENTE
          </label>
       </div>

       <div class="inputs" id="inputs">
        <form @submit.prevent="submitForm">
          <div class="form-group">
            <label for="nombre">Nombre</label>
            <input class="input-field" type="text" id="nombre" v-model="formData.nombre" required />
          </div>

          <div class="form-group">
            <label for="apellidos">Apellidos</label>
            <input class="input-field"  type="text" id="apellidos" v-model="formData.apellidos" required />
          </div>

          <div class="form-group">
            <label for="correoElectronico">Correo electrónico</label>
            <input class="input-field"  type="email" id="correoElectronico" v-model="formData.correoElectronico" required />
          </div>

          <div class="form-group">
            <label for="telefono">Teléfono</label>
            <input  class="input-field" type="tel" id="telefono" v-model="formData.telefono" required />
          </div>

          <div class="form-group">
            <label for="contrasena">Contraseña</label>
            <input class="input-field" type="password" id="contrasena" v-model="formData.contrasena" required />
          </div>

          <div class="form-group">
            <label for="confirmacionContraseña">Corroborar contraseña</label>
            <input class="input-field" type="password" id="confirmacionContraseña" v-model="formData.confirmacionContraseña" required />
          </div>

          <div class="passwordshow">
              <input type="checkbox" @click="togglePassword"/>
              <label for="input-field">SHOW</label>
          </div>

          <div class="profile-section">
            <img :src="profileImageUrl" class="profile-image" id="profileImage" />
            <input class="img-chooser" type="file" id="profile-image" @change="previewImage" accept="image/*" />
          </div>

          <div class="bottom-buttons">
            <button type="submit" @click="submitForm" class="button">Guardar Cambios</button>
            <button type="button" @click="confirmDelete" class="button">Eliminar Cuenta</button>
          </div>
          <router-link to="/login" class="home">Inicio</router-link>
        </form>
       </div>
       </div>
       </div>
      <!-- AQUI TERMINA LO QUE HICE -->
</template>

<script>
import Swal from "sweetalert2";
import CutiendaLeon from "../icons/CutiendaLeon.vue";
import axios from "axios";

export default {
  components:{
    CutiendaLeon,
  },
  data() {
    return {
      profileImageUrl: '/path/to/default/image.png',
      formData: {
        nombre: '',
        apellidos: '',
        correoElectronico: '',
        telefono: '',
        contraseña: '',
        tipo: '',
        fotoUrl: '',
      },
      confirmacionContraseña: '',
    };
  },
  methods: {
    submitForm() {
      // Aquí enviarás los datos con Axios o Fetch.
      console.log(this.formData);
      // Implementa la lógica de envío
      axios.post('', this.formData);
      // axios.post('/cutienda/perfil/actualizar', this.formData)
    },
    registroExitoso() {
      Swal.fire({
        title: 'Registro exitoso',
        text: '',
        icon: 'success',
        showCancelButton: false,
      }).then(result => {
        if (result.isConfirmed) {
          console.log('REGISTRO EVALUADO');
        }
      })
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
* {
  color: white;
  font-family: Arial, sans-serif;
  background-color: #002160;
  padding: 0;
}

.padre {
  display: flex;
  justify-content: center;
}

.encabezado {
  position: fixed;
  top: 0;
  width: 90%;
  height: 9rem;
  background-color: #002160;
  padding: 40px 0;
  text-align: center;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 15px;
}

.container {
  margin-top: 150px;
  width: 62%;
  border: 1px white solid;
  background: #002160;
  padding: 40px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-flow: column;
}

.encabezado img{
  border: 6px #4471c4 solid;
  width: 5em;
  height: 5em;
}
.encabezado span {
  font-size: 2em;
  color: #f9f9f9;
}

.rol {
  width: 100%;
  height: auto;
  display: flex;
  gap: 15px;
  justify-content: center;
  align-items: center;
}

.inputs {
  display: flex;
  flex-flow: column;
  width: 100%;
  height: auto;
  border: 1px solid aqua;
  justify-content: center;
  padding: 3em 5em 0em 5em;
}
.form-group {
  /* display: grid;
  grid-template-columns: 1fr 1fr; */
  display: flex;
  flex-flow: column;
  padding-bottom: 30px;
}

.input-field, .file-input {
  background-color: #f0f8ff; /* Color de fondo claro */
  border: 1px solid #ccc; /* Borde más visible */
  border-radius: 5px; /* Bordes redondeados */
  color: #333; /* Color del texto */
  padding: 12px; /* Espaciado interior */
  margin: 10px 0; /* Espaciado entre inputs */
  width: 100%; /* 100% de ancho */
  transition: border-color 0.3s ease; /* Transición suave para el borde */
}
.passwordshow {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: row-reverse;
  gap: 10px;
}
/* El resto de tu CSS */
.profile-section {
  display: flex;
  width: 100%;
  height: auto;
  border: 1px white solid;
  justify-content: space-between;
  flex-flow: row-reverse;
  align-items: center;
  padding: 1em;
  gap: 20px;
  padding: 0em 0em 0em 0em;
}
.profile-section img {
  width: 4.5em;
  height: 5em;
}
.bottom-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 10%;
}

.button {
  width: 48%;
  padding: 10px; /* Ajustar el padding para mejorar la apariencia */
  border: none; /* Sin borde */
  border-radius: 5px; /* Bordes redondeados */
  background-color: #0056b3; /* Color de fondo */
  color: white; /* Color del texto */
  cursor: pointer; /* Cambiar el cursor al pasar sobre el botón */
  transition: background-color 0.3s ease; /* Transición suave para el fondo */
}

.button:hover {
  background-color: #004494; /* Color más oscuro al pasar el mouse */
}
</style>
