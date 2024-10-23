<template>
  <div class="container">
    <CutiendaLeon class="logo2" />
    <h3 >Crear cuenta</h3>
    <div class="radio-group">
      <label>
        <input type="radio" name="tipo" value="admin" v-model="userType" /> ADMINISTRADOR
      </label>
      <label>
        <input type="radio" name="tipo" value="client" v-model="userType" /> CLIENTE
      </label>
    </div>
    <input type="text" v-model="nombre" placeholder="Nombre" class="input-field" />
    <input type="text" v-model="apellidos" placeholder="Apellidos" class="input-field" />
    <input type="email" v-model="correoElectronico" placeholder="Correo electrónico" class="input-field" />
    <input type="tel" v-model="telefono" placeholder="Teléfono" class="input-field" />
    <input type="file" @change="onFileChange" accept="image/*" class="file-input" />
    <input type="password" v-model="contraseña" placeholder="Contraseña" class="input-field" id="password" />
    <input type="password" v-model="confirmPassword" placeholder="Corroborar contraseña" class="input-field" id="confirm-password" />
    <label>
      <input type="checkbox" @click="togglePassword" /> SHOW
    </label>
    <div class="bottom-buttons">
      <button type="button" @click="goToLogin" class="button">INICIO</button>
      <button type="button" @click.prevent="submitForm" class="button">CREAR CUENTA</button>
    </div>
  </div>
</template>

<script>
import Swal from 'sweetalert2';
import CutiendaLeon from '../icons/CutiendaLeon.vue';

export default {
  components: {
    CutiendaLeon
  },
  data() {
    return {
      userType: '',
      nombre: '',
      apellidos: '',
      correoElectronico: '',
      telefono: '',
      foto: null,
      contraseña: '',
      confirmPassword: '',
    };
  },
  methods: {
    onFileChange(event) {
      const file = event.target.files[0];
      if (!file) {
        alert('Por favor, selecciona un archivo.');
        return;
      }
      const fileType = file.type;
      if (!fileType.startsWith('image/')) {
        alert('Formato no válido. Por favor, sube una imagen.');
      } else {
        this.foto = file;
      }
    },
    togglePassword() {
      const passwordField = document.getElementById('password');
      const confirmPasswordField = document.getElementById('confirm-password');
      const type = passwordField.type === 'password' ? 'text' : 'password';
      passwordField.type = type;
      confirmPasswordField.type = type;
    },
    goToLogin() {
      this.$router.push('/cutienda/login');
    },
    submitForm() {
      if (this.contraseña !== this.confirmPassword) {
        this.showAlert('Error de registro', 'Las contraseñas no coinciden', 'error');
        return;
      }

      // Aquí puedes agregar el código para enviar el formulario
      this.showAlert('Registro exitoso', 'Cuenta creada correctamente', 'success');
    },
    showAlert(title, text, icon) {
      Swal.fire({
        title: title,
        text: text,
        icon: icon,
        confirmButtonText: 'Aceptar'
      });
    }
  }
};
</script>

<style scoped>
.container {
  margin-top: 15%;
  flex-direction: column;  
  text-align: center;
  width: 90%;
  max-width: 400px;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 10px 10px 100px rgba(0, 0, 0, 0.1);
  color: #f0f8ff;
}

.logo2 {
  width: 100%;
  padding: 10px;
  margin-bottom: 20px; /* Espacio adicional debajo del logo */
}

.radio-group {
  display: flex;
  justify-content: center; /* Centra los botones de radio */
  margin: 10px 0; /* Espaciado vertical */
}

.radio-group label {
  margin: 0 15px; /* Espaciado entre los labels */
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

.input-field:focus {
  border-color: #0056b3; /* Color del borde en foco */
  outline: none; /* Sin borde de enfoque */
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
