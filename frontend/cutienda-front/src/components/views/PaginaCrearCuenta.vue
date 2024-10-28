<template>
  <div class="padre">
    <div class="encabezado">
      <CutiendaLeon />
      <span>Registro</span>
    </div>

    <div class="container">
      <div class="rol" id="rol">
        <label>
          <input type="radio" value="admin" v-model="formData.role" /> ADMINISTRADOR
        </label>
        <label>
          <input type="radio" value="client" v-model="formData.role" /> CLIENTE
        </label>
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

export default {
  name: 'PaginaCrearCuenta',
  components: {
    CutiendaLeon,
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
        foto:'',
        role: '',
        confirmPassword: '',
      },
      errors: {},
    };
  },
  methods: {
    validateField(field) {
      // Limpia los errores anteriores
      this.errors[field] = '';

      // Validaciones específicas por campo
      if (field === 'nombre' || field === 'apellidos') {
        if (!this.formData[field].trim()) this.errors[field] = 'Este campo es obligatorio';
      } else if (field === 'correoElectronico') {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(this.formData.email)) {
          this.errors.correoElectronico = 'Correo no válido';
        }
      } else if (field === 'telefono') {
        const phoneRegex = /^[0-9]{10}$/;
        if (!phoneRegex.test(this.formData.telefono)) {
          this.errors.telefono = 'Número de teléfono no válido';
        }
      } else if (field === 'contrasena' || field === 'confirmacionContraseña') {
        if (this.formData.password !== this.formData.confirmPassword) {
          this.errors.confirmacionContraseña = 'Las contraseñas no coinciden';
        }
      }
    },
    submitForm() {
      // Validación final antes del envío
      Object.keys(this.formData).forEach((field) => this.validateField(field));
      if (Object.values(this.errors).some((error) => error)) return;

      if (!['admin', 'client'].includes(this.formData.role)) {
        Swal.fire({
          icon: 'error',
          title: 'Falta de informacion',
          text: 'Confirme si es admin o cliente'
        });
        return;
      }

      console.log(this.formData);
      // Crear un nuevo FormData
      const formData = new FormData();
      formData.append('nombre', this.formData.nombre);
      formData.append('apellidos', this.formData.apellidos);
      formData.append('email', this.formData.email);
      formData.append('telefono', this.formData.telefono);
      formData.append('password', this.formData.password);
      formData.append('confirmPassword', this.formData.confirmPassword);
      formData.append('role', this.formData.role);
      formData.append('foto', this.formData.foto); // Aquí es donde deberías agregar el archivo


      axios
        .post('http://localhost:8011/cutienda/registro', formData,{
          headers: {
          'Content-Type':'multipart/form-data'
          },
          withCredentials: false,
        }
        )
        .then((response) => {
          this.registroExitoso();
        })
        .catch((e) => {
          Swal.fire({
            icon: 'error',
            title: 'Error al registrar',
            text: 'Error de conexion, por favor intente mas tarde',
          });
        });
    },
    registroExitoso() {
      Swal.fire({
        title: 'Registro exitoso',
        icon: 'success',
      }).then((result) => {
        if (result.isConfirmed) {
          console.log('REGISTRO EVALUADO');
        }
      });
    },
    confirmDelete() {
      Swal.fire({
        title: "¿Estás seguro de que quieres eliminar tu cuenta?",
        text: "Esta acción no se puede deshacer.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#dc3545",
        confirmButtonText: "Eliminar",
        cancelButtonText: "Cancelar",
      }).then((result) => {
        if (result.isConfirmed) {
          console.log("Cuenta eliminada");
          // axios.post('/cutienda/perfil/eliminar')
        }
      });
    },
    previewImage(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.profileImageUrl = e.target.result;
          this.formData.foto = file;
        };
        reader.readAsDataURL(file);
      }
    },
  },
};
</script>

<style scoped>
* {
  color: white;
  font-family: Arial, sans-serif;
  font-size: 0.95em;
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
  height: 7.9rem;
  background-color: #002160;
  padding: 40px 0;
  border-bottom-left-radius: 12px;
  border-bottom-right-radius: 12px;
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
  background: #002160;
  padding: 40px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-flow: column;
}

.encabezado img{
  width: 5rem;
  height: 5rem;
  border: 0.5rem solid #4472c4;
}
.encabezado span {
  font-size: 1.5em;
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
  justify-content: space-between;
  flex-flow: row-reverse;
  align-items: center;
  padding: 1em;
  gap: 20px;
  padding: 0em 0em 0em 0em;
}
.profile-section img {
  width: 4.5rem;
  height: 5rem;
}
.bottom-buttons {
  display: flex;
  justify-content: center;
  margin-top: 10%;
}

.button {
  width: auto;
  padding: 10px; /* Ajustar el padding para mejorar la apariencia */
  border: none; /* Sin borde */
  border-radius: 5px; /* Bordes redondeados */
  background-color: #0056b3; /* Color de fondo */
  color: white; /* Color del texto */
  cursor: pointer; /* Cambiar el cursor al pasar sobre el botón */
  transition: background-color 0.3s ease; /* Transición suave para el fondo */
  font-size: 0.8rem;
}

.button:hover {
  background-color: #004494; /* Color más oscuro al pasar el mouse */
}
.error {
  color: red;
  font-size: 0.9em;
}

@media (max-width: 920px) {
  .container {
    width: 100%;
    padding: 5px;
  }
  .inputs {
    padding: 1.5rem 1.5rem 0.5rem;
  }
}
@media (min-width: 1700px) {
  * {
    font-size: 1.8rem;
  }
  .encabezado {
    font-size: 1rem;
  }
  .button {
    font-size: 2rem;
  }
}
</style>
