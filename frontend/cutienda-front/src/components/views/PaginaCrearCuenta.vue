<template>
  <div class="registro">
    <header class="encabezado">
      <CutiendaLeon />
      <h1>Registro</h1>
    </header>

    <div class="form-container">
      <div class="roles">
        <CustomRadio
          v-model="formData.role"
          name="roles"
          :options="[
            { id: 'admin', value: 'admin', label: 'ADMIN' },
            { id: 'client', value: 'client', label: 'CLIENTE' }
          ]"
        />
      </div>

      <form @submit.prevent="submitForm" class="form">
        <div class="form-group" v-for="(label, key) in formFields" :key="key">
          <label :for="key">{{ label }}</label>
          <input
            class="input-field"
            :type="key.includes('password') ? 'password' : 'text'"
            :id="key"
            v-model="formData[key]"
            @blur="validateField(key)"
            :required="true"
          />
          <span v-if="errors[key]" class="error">{{ errors[key] }}</span>
        </div>

        <div class="password-show">
          <input type="checkbox" @click="togglePassword" />
          <label>Mostrar contraseñas</label>
        </div>

        <div class="profile-section">
          <img :src="profileImageUrl" class="profile-image" />
          <input
            type="file"
            id="profile-image"
            class="img-chooser"
            @change="previewImage"
            accept="image/*"
          />
          <label for="profile-image" class="upload-button">Escoger foto</label>
        </div>

        <div class="actions">
          <button type="submit" class="button primary">Crear Cuenta</button>
          <button @click="login" type="button" class="button secondary">
            Iniciar Sesión
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import CutiendaLeon from "../icons/CutiendaLeon.vue";
import CustomRadio from "../CustomRadio.vue";
import axios from "axios";
import Swal from "sweetalert2";

export default {
  name: "PaginaCrearCuenta",
  components: { CutiendaLeon, CustomRadio },
  data() {
    return {
      profileImageUrl: "/path/to/default/image.png",
      formData: {
        nombre: "",
        apellidos: "",
        email: "",
        telefono: "",
        password: "",
        confirmPassword: "",
        role: "",
      },
      errors: {},
      formFields: {
        nombre: "Nombre",
        apellidos: "Apellidos",
        email: "Correo Electrónico",
        telefono: "Teléfono",
        password: "Contraseña",
        confirmPassword: "Confirmar Contraseña",
      },
    };
  },
  methods: {
    validateField(field) {
      this.errors[field] = this.formData[field].trim() ? "" : "Este campo es obligatorio.";
    },
    togglePassword() {
      const fields = document.querySelectorAll('input[type="password"]');
      fields.forEach((field) => (field.type = field.type === "password" ? "text" : "password"));
    },
    previewImage(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => (this.profileImageUrl = e.target.result);
        reader.readAsDataURL(file);
        this.formData.foto = file;
      }
    },
    submitForm() {
      // Implementar la lógica para enviar el formulario

      //const { nombre, apellidos, email, telefono, password, role, confirmPassword } = this.formData;
      console.log(this.formData);

      // Validar contraseñas
      if (this.formData.password !== this.formData.confirmPassword) {
        Swal.fire('Error', 'Las contraseñas no coinciden', 'error');
        return;
      }

      const form = new FormData();
      form.append("nombre", this.formData.nombre);
      form.append("apellidos", this.formData.apellidos); 
      form.append("email", this.formData.email); 
      form.append("telefono", this.formData.telefono); 
      form.append("role",this.formData.role); 
      form.append("password",this.formData.password); 
      form.append("confirmPassword", this.formData.confirmPassword); 
      form.append("foto", this.formData.foto);


      const rol = this.formData.role === 'admin' ? 'vendedor' : 'usuario';
      axios.post('http://localhost:8011/api/cutienda/registro', form, {
        headers: {"Content-Type":"multipart/form-data"}
      })
      .then(response => {
        Swal.fire('Éxito', 'Cuenta creada exitosamente', 'success');
        axios.post('http://localhost:8012/api/notificaciones', {
          idUsuario: response.data.message.id,
          mensaje: `${this.formData.nombre} ${this.formData.apellidos} se ha registrado en Cutienda como ${rol}`,
          categoria: `🎉🎉¡Un nuevo integrante se une a la familia de Cutienda!🎉🎉`,
          fecha: new Date().toISOString(),
          leido: false
        })
        this.$router.push({name: 'Usuario', params: {id: response.data.message.id}});
      })
      .catch(error => {
        console.error(error);
        if (error.response) {
          Swal.fire('Error', error.response.data.error, 'error');
          return;
        }
        else {
          Swal.fire('Error de conexión', 'Revisa tu conexion a internet', 'error');
        }
      });
    },
    login() {
      this.$router.push("Login");
    },
  },
};
</script>

<style scoped>
/* General styles */
body {
  font-family: "Roboto", sans-serif;
  margin: 0;
  background-color: #f5f6fa;
  color: #333;
}

/* Layout */
.registro {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem;
}

.encabezado {
  text-align: center;
  margin-bottom: 2rem;
  display: flex;
  flex-flow: column;
  gap: 1em;
}

.encabezado h1 {
  color: #f9f9f9;
}
.encabezado img {
  width: 7rem;
  height: 7rem;
}

h1 {
  font-size: 2rem;
  font-weight: bold;
  color: #4471c4;
}

/* Form styles */
.form-container {
  background: #fff;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  width: 100%;
  max-width: 500px;
  padding: 2rem;
}

.roles {
  margin-bottom: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 1.5rem;
}

label {
  font-weight: 500;
  margin-bottom: 0.5rem;
}

.input-field {
  padding: 0.8rem;
  border-radius: 8px;
  border: 1px solid #ddd;
  font-size: 1rem;
  color: #333;
}

.input-field:focus {
  outline: none;
  border-color: #4471c4;
  box-shadow: 0 0 0 3px rgba(68, 113, 196, 0.2);
}

.error {
  color: red;
  font-size: 0.875rem;
}

/* Profile image */
.profile-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 2rem;
}

.profile-image {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 1rem;
}

.upload-button {
  background-color: #007bff;
  color: #fff;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
}

.upload-button:hover {
  background-color: #0056b3;
}

/* Buttons */
.actions {
  display: flex;
  justify-content: space-between;
  margin-top: 1rem;
}

.button {
  padding: 0.8rem 1.5rem;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  cursor: pointer;
}

.button.primary {
  background-color: #4471c4;
  color: white;
}

.button.primary:hover {
  background-color: #3458a1;
}

.button.secondary {
  background-color: #ddd;
  color: #333;
}

.button.secondary:hover {
  background-color: #bbb;
}

/* Responsiveness */
@media (max-width: 600px) {
  .form-container {
    padding: 1rem;
  }

  .actions {
    flex-direction: column;
    gap: 1rem;
  }
}
</style>

