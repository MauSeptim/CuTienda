<template>
  <div>
    <!-- Encabezado -->
    <header class="encabezado">
        <CutiendaLeon class="logo2"/>
    </header>

    <!-- Contenedor Principal -->
    <div class="container">
      <!-- Sección de Perfil -->
      <section class="profile-section">
        <img :src="usuarioFoto" alt="Imagen de Perfil" class="profile-image" />
        <label for="profile-image" class="profile-label">Cambiar Imagen</label>
        <input type="file" id="profile-image" @change="previewImage" accept="image/*" />
      </section>

      <!-- Formulario -->
      <section class="form-section">
        <div class="tabs">
          <label>
            <input type="radio" value="admin" v-model="usuario.tipo" /> ADMINISTRADOR
          </label>
          <label>
            <input type="radio" value="client" v-model="usuario.tipo" /> CLIENTE
          </label>
        </div>

        <form @submit.prevent="guardarCambios">
          <div v-for="(campo, index) in camposFormulario" :key="index" class="form-group">
            <label :for="campo.id">{{ campo.label }}</label>
            <input :type="campo.type" :id="campo.id" v-model="usuario[campo.modelo]" :required="campo.required" />
          </div>

          <label class="show-password">
            <input type="checkbox" @click="togglePassword" /> Mostrar Contraseña
          </label>

          <div class="buttons">
            <button type="submit" class="save">Guardar Cambios</button>
            <router-link to="/login" class="home">Inicio</router-link>
            <button type="button" @click="confirmDelete" class="delete">Eliminar Cuenta</button>
          </div>
        </form>
      </section>
    </div>
  </div>
</template>
<script>
import Swal from "sweetalert2";
import CutiendaLeon from "../icons/CutiendaLeon.vue";

export default {
  data() {
    return {
      usuario: {
        nombre: "",
        apellidos: "",
        correoElectronico: "",
        telefono: "",
        contrasena: "",
        confirmacionContraseña: "",
        tipo: "client",
      },
      mostrarPassword: false,
      camposFormulario: [
        { id: "nombre", label: "Nombre", type: "text", modelo: "nombre", required: true },
        { id: "apellidos", label: "Apellidos", type: "text", modelo: "apellidos", required: true },
        { id: "correoElectronico", label: "Correo Electrónico", type: "email", modelo: "correoElectronico", required: true },
        { id: "telefono", label: "Teléfono", type: "tel", modelo: "telefono", required: true },
        { id: "contrasena", label: "Contraseña", type: "password", modelo: "contrasena", required: true },
        { id: "confirmacionContraseña", label: "Confirmar Contraseña", type: "password", modelo: "confirmacionContraseña", required: true },
      ],
      usuarioFoto: "/path/to/default/image.png",
    };
  },
  methods: {
    guardarCambios() {
      Swal.fire("Guardado con éxito", "Tus cambios han sido guardados", "success");
    },
    confirmDelete() {
      Swal.fire({
        title: "¿Estás seguro?",
        text: "No podrás revertir esto",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Sí, eliminar cuenta",
        cancelButtonText: "Cancelar",
      }).then((result) => {
        if (result.isConfirmed) {
          // Acción de eliminación de cuenta
          Swal.fire("Eliminado", "Tu cuenta ha sido eliminada", "success").then(() => {
            this.$router.push("/login");
          });
        }
      });
    },
    togglePassword() {
      this.mostrarPassword = !this.mostrarPassword;
      const tipo = this.mostrarPassword ? "text" : "password";
      this.camposFormulario.forEach((campo) => {
        if (campo.modelo === "contrasena" || campo.modelo === "confirmacionContraseña") {
          campo.type = tipo;
        }
      });
    },
    previewImage(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.usuarioFoto = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    },
  },
};
</script>
<style scoped>
body {
  color: white;
  font-family: Arial, sans-serif;
  background-color: #001f5f;
  margin: 0;
  padding: 0;
}
.encabezado {
  background-color: #001f5f;
  padding: 20px 0;
  text-align: center;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
}
.logo2 {
  width: 150px;
  height: auto;
}
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}
.profile-section,
.form-section {
  background-color: #002160;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}
.profile-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}
.profile-image {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid #ffffff;
}
.profile-label {
  margin-top: 10px;
  font-weight: bold;
  cursor: pointer;
  color: #f0f0f0;
}
.form-section .tabs {
  display: flex;
  justify-content: center;
  margin-bottom: 15px;
  gap: 10px;
}
.form-group {
  margin-bottom: 15px;
}
.form-group label {
  display: block;
  margin-bottom: 5px;
}
.form-group input {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: none;
  outline: none;
  box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.2);
}
.show-password {
  display: block;
  margin-top: 10px;
  font-size: 14px;
  color: #ccc;
}
.buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}
.buttons button,
.buttons .home {
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-weight: bold;
  transition: background-color 0.3s, box-shadow 0.3s;
}
.buttons .delete {
  background-color: #dc3545;
}
.buttons .home {
  background-color: #6c757d;
}
.buttons button:hover,
.buttons .home:hover {
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
}
@media (min-width: 768px) {
  .container {
    flex-direction: row;
    gap: 40px;
  }
  .profile-section,
  .form-section {
    width: 45%;
  }
}
</style>
