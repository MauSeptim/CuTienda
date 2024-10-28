<template>
  <div>
    <!-- Encabezado -->
    <header class="encabezado">
        <CutiendaLeon />
        <Boton class="botones-superiores" :texto="'Buscar productos'" @click="prod"/>
    </header>

    <!-- Contenedor Principal -->
    <div class="container">
      <!-- Sección de Perfil -->
      <section class="profile-section">
        <img :src="usuarioFoto" alt="Imagen de Perfil" class="profile-image" />
        <p class="profile-name">{{ usuario.nombre }} {{ usuario.apellidos }}</p>
      </section>

      <!-- Tabs -->
      <nav class="tabs">
        <ul>
          <li  @click="cambiarTab('perfil')" :class="{ active: tabActiva === 'perfil' }">Perfil</li>
          <li @click="cambiarTab('notificaciones')" :class="{ active: tabActiva === 'notificaciones' }">Notificaciones</li>
          <li @click="cambiarTab('configuracion')" :class="{ active: tabActiva === 'configuracion' }">Configuración</li>
        </ul>
      </nav>

      <!-- Contenido de las Tabs -->
      <section class="tab-content">
        <div v-if="tabActiva === 'perfil'" class="perfil-content">
          <h2>Información del Perfil</h2>
          <p><strong>Correo Electrónico:</strong> {{ usuario.correoElectronico }}</p>
          <p><strong>Teléfono:</strong> {{ usuario.telefono }}</p>
          <button @click="activarEdicion" class="edit-button">Editar</button>
        </div>

        <div v-else-if="tabActiva === 'notificaciones'" class="notificaciones-content">
          <h2>Notificaciones</h2>
          <p>Aquí puedes ver tus notificaciones.</p>
        </div>

        <div v-else-if="tabActiva === 'configuracion'" class="configuracion-content">
          <h2>Configuración</h2>
          <p>Aquí puedes ajustar la configuración de tu cuenta.</p>
        </div>

        <!-- Formulario de Edición (visible solo en modo edición) -->
        <section v-if="modoEdicion" class="form-section">
          <form @submit.prevent="guardarCambios">
            <div v-for="(campo, index) in camposFormulario" :key="index" class="form-group">
              <label :for="campo.id">{{ campo.label }}</label>
              <input :type="campo.type" :id="campo.id" v-model="usuario[campo.modelo]" :required="campo.required" />
            </div>

            <div class="buttons">
              <button type="submit" class="save">Guardar Cambios</button>
              <button type="button" @click="cancelarEdicion" class="cancel">Cancelar</button>
            </div>
          </form>
        </section>
      </section>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import CutiendaLeon from "../icons/CutiendaLeon.vue";
import Boton from "../Boton.vue";

export default {
    name: 'PaginaUsuario',
    components: {
        CutiendaLeon,
        Boton,
    },
    data() {
    return {

        usuario: {
            nombre: "Juan",
            apellidos: "Pérez",
            correoElectronico: "juan.perez@example.com",
            telefono: "555-1234",
            contrasena: "",
        },
      modoEdicion: false,
      tabActiva: "perfil",
      camposFormulario: [
        { id: "nombre", label: "Nombre", type: "text", modelo: "nombre", required: true },
        { id: "apellidos", label: "Apellidos", type: "text", modelo: "apellidos", required: true },
        { id: "correoElectronico", label: "Correo Electrónico", type: "email", modelo: "correoElectronico", required: true },
        { id: "telefono", label: "Teléfono", type: "tel", modelo: "telefono", required: true },
        { id: "contrasena", label: "Contraseña", type: "password", modelo: "contrasena", required: true },
      ],
      usuarioFoto: "/path/to/default/image.png",
    };
  },
  methods: {
    prod() {
        this.$router.push('Productos');
    },
    cambiarTab(tab) {
      this.tabActiva = tab;
    },
    activarEdicion() {
      this.modoEdicion = true;
    },
    cancelarEdicion() {
      this.modoEdicion = false;
    },
    guardarCambios() {
      this.modoEdicion = false;
      Swal.fire("Guardado con éxito", "Tus cambios han sido guardados", "success");
    },
  },
};
</script>

<style scoped>
* {
    color: #f9f9f9;
}
body {
  color: white;
  font-family: Arial, sans-serif;
  background-color: #001f5f;
  margin: 0;
  padding: 0;
}
.encabezado {
  background-color: #001f5f;
  height: 5rem;
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  padding: 20px 0;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
}
.encabezado img {
  width: 5rem;
  height: 5rem;
  border: 0.5rem solid #4472c4;
  transform: translateY(-30px);
}
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: start;
  gap: 20px;
}
.profile-section {
  background-color: #002160;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  text-align: center;
}
.profile-image {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid #ffffff;
}
.profile-name {
  margin: 10px 0;
  font-size: 18px;
  font-weight: bold;
  color: #f0f0f0;
}
.tabs {
  margin: 20px 0;
}
.tabs ul {
  display: flex;
  list-style-type: none;
  padding: 0;
  gap: 5px;
}
.buttons, .tabs li {
  cursor: pointer;
  padding: 10px 15px;
  border-radius: 5px;
  background-color: #003366;
  transition: background-color 0.3s;
}
.tabs li:hover {
  background-color: #004080;
}
.tabs li.active {
  background-color: #4e73df;
}
.tab-content {
  width: 100%;
  max-width: 600px;
}
.perfil-content,
.notificaciones-content,
.configuracion-content {
  background-color: #002160;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}
.perfil-content h2, p{
    padding: 15px 0;
}
.edit-button {
  background-color: #4e73df;
  color: white;
  padding: 8px 15px;
  border: none;
  border-radius: 5px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s;
}
.edit-button:hover {
  background-color: #3a61c9;
}
.form-section {
  margin-top: 20px;
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
.buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}
.buttons button {
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s, box-shadow 0.3s;
}
.buttons .cancel {
  background-color: #6c757d;
}
.buttons button:hover {
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
}
.botones-superiores {
  position: absolute;
  top: 0;      /* Alínea al borde superior */
  right: 0;    /* Alínea al borde derecho */
  display: flex;
  gap: 10px;   /* Espacio entre los botones */
  margin: 20px; /* Margen para separarlos un poco del borde */

}
</style>
