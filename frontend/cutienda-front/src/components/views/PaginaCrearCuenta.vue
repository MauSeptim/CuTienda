<template>
  <div class="padre">
    <div class="encabezado">
      <CutiendaLeon />
      <span>Registro</span>
    </div>

    <div class="container">
      <div class="roles" id="roles">
        <CustomRadio
          class="radio"
          v-model="formData.role"
          name="roles"
          :options="[
            { id: 'admin', value: 'admin', label: 'ADMIN' },
            { id: 'client', value: 'client', label: 'CLIENTE' }
          ]"
        />
      </div>

      <div class="inputs" id="inputs">
        <form @submit.prevent="submitForm">
          <div class="form-group">
            <label for="nombre">Nombre</label>
            <input
              class="input-field"
              type="text"
              id="nombre"
              v-model="formData.nombre"
              @blur="validateField('nombre')"
              required
            />
            <span v-if="errors.nombre" class="error">{{ errors.nombre }}</span>
          </div>

          <div class="form-group">
            <label for="apellidos">Apellidos</label>
            <input
              class="input-field"
              type="text"
              id="apellidos"
              v-model="formData.apellidos"
              @blur="validateField('apellidos')"
              required
            />
            <span v-if="errors.apellidos" class="error">{{ errors.apellidos }}</span>
          </div>

          <div class="form-group">
            <label for="correoElectronico">Correo electrónico</label>
            <input
              class="input-field"
              type="email"
              id="correoElectronico"
              v-model="formData.email"
              @blur="validateField('correoElectronico')"
              required
            />
            <span v-if="errors.email" class="error">{{ errors.correoElectronico }}</span>
          </div>

          <div class="form-group">
            <label for="telefono">Teléfono</label>
            <input
              class="input-field"
              type="tel"
              id="telefono"
              v-model="formData.telefono"
              @blur="validateField('telefono')"
              required
            />
            <span v-if="errors.telefono" class="error">{{ errors.telefono }}</span>
          </div>

          <div class="form-group">
            <label for="contrasena">Contraseña</label>
            <input
              class="input-field"
              type="password"
              id="contrasena"
              v-model="formData.password"
              @blur="validateField('contrasena')"
              required
            />
            <span v-if="errors.contrasena" class="error">{{ errors.contrasena }}</span>
          </div>

          <div class="form-group">
            <label for="confirmacionContraseña">Corroborar contraseña</label>
            <input
              class="input-field"
              type="password"
              id="confirmacionContraseña"
              v-model="formData.confirmPassword"
              @blur="validateField('confirmacionContraseña')"
              required
            />
            <span v-if="errors.confirmacionContraseña" class="error">{{ errors.confirmacionContraseña }}</span>
          </div>

          <div class="passwordshow">
            <input type="checkbox" @click="togglePassword" />
            <label for="input-field">SHOW</label>
          </div>

          <div class="profile-section">
            <img :src="profileImageUrl" class="profile-image" id="profileImage" />
            <input
              class="img-chooser"
              type="file"
              id="profile-image"
              @change="previewImage"
              accept="image/*"
              style="display: none;"
            />
            <label for="profile-image" class="button">Escoger foto</label>
          </div>

          <div class="bottom-buttons">
            <button type="submit" class="button">CREAR CUENTA</button>
            <button class="button" @click="login">INICIAR SESION</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import CutiendaLeon from "../icons/CutiendaLeon.vue";
import axios from "axios";
import CustomRadio from '../CustomRadio.vue'; // Asegúrate de importar CustomRadio

export default {
  name: 'PaginaCrearCuenta',
  components: {
    CutiendaLeon,
    CustomRadio,
  },
  data() {
    return {
      profileImageUrl: '/path/to/default/image.png',
      formData: {
        nombre: '',
        apellidos: '',
        email: '',
        telefono: '',
        password: '',
        foto: '',
        role: '',
        confirmPassword: '',
      },
      errors: {},
    };
  },
  methods: {
    login() {
      this.$router.push('Login');
    },
    validateField(field) {
      // Limpia los errores anteriores
      this.errors[field] = '';

      // Validaciones específicas por campo
      if (field === 'nombre' || field === 'apellidos') {
        if (!this.formData[field].trim()) this.errors[field] = 'Este campo es requerido';
      }
      // Agrega validaciones adicionales según sea necesario
    },
    togglePassword() {
      // Implementar la lógica para mostrar/ocultar la contraseña
      const passwordFields = document.querySelectorAll('input[type=password]');
      passwordFields.forEach(field => {
        field.type = field.type === 'password' ? 'text' : 'password';
      });
    },
    previewImage(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = e => {
          this.profileImageUrl = e.target.result;
          this.formData.foto = file;
        };
        reader.readAsDataURL(file);
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


      axios.post('http://localhost:8011/api/cutienda/registro', form, {
        headers: {"Content-Type":"multipart/form-data"}
      })
      .then(response => {
        localStorage.setItem("user", {
          id: response.data.id,
          nombre: this.formData.nombre,
          apellidos: this.formData.apellidos,
          password: this.formData.password,
          telefono: this.formData.telefono,
          email: this.formData.email,
          tipo: this.formData.role,
          foto: this.formData.foto,
          confirmPassword: this.formData.confirmPassword,
        });
        Swal.fire('Éxito', 'Cuenta creada exitosamente', 'success');
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
    }
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap");

* {
  color: #f9f9f9;
  font-family: "Roboto", sans-serif;
}
.padre {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.container {
  display: flex;
  flex-flow: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  width: 80%;
}
.input-field {
  background-color: #f9f9f9;
  border: none;
  border-radius: 30px;
  outline: none;
  color: black;
  padding: 1rem;
  margin: 20px 0;
  width: 100%;
  box-shadow: rgba(0, 0, 0, 0.45) 0px 25px 20px -20px;
}
.bottom-buttons {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-top: 5%;
}
.error {
  color: red;
}
.profile-section {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.profile-image {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 10px;
}
.button {
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  cursor: pointer;
}
.button:hover {
  background-color: #0056b3;
}
.encabezado {
  position: absolute;
  display: flex;
  flex-flow: column;
  align-items: center;
  gap: 10px;
}
.encabezado img {
  width: 6em;
  height: 6em;
  border: 5px solid #4471c4;
}
.roles {
  margin-top: 150px;
  margin-bottom: 30px;
}
.inputs {
  width: 67%;
  padding: 2em;
}
.radio {
  height: 5em;
  width: 30em;
  font-size: 1em;
}
input {
  font-size: 1.1rem;
}
@media (max-width: 950px) {
  .inputs {
    width: 100%;
    padding: 2px;
  }
  .radio {
    width: 25em;
    height: auto;
    font-size: 0.9em;
  }
}

</style>
