<template>
  <div class="padre">
    <header class="encabezado">
      <h1 class="titulo">Perfil de Usuario</h1>
    </header>

    <main class="container">
      <section class="profile-section">
        <img :src="usuario.fotoUrlBase64" alt="Imagen de Perfil" class="profile-image" />
        <h2 class="nombre">{{ usuario.nombre }} {{ usuario.apellidos }}</h2>
        <p class="text-muted">{{ usuario.correoElectronico }}</p>
      </section>

      <section class="actions-section">
        <button @click="editarPerfil" class="action-button">Editar Perfil</button>
        <button @click="verNotificaciones" class="action-button">Notificaciones</button>
        <button @click="verProductos" class="action-button">Productos</button>
        <button v-if="usuario.tipo === 'admin'" @click="registrarProducto" class="action-button">Registrar Producto</button>
      </section>
    </main>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "PaginaUsuario",
  data() {
    return {
      usuario: {
        nombre: "",
        apellidos: "",
        correoElectronico: "",
        telefono: "",
        tipo: "",
      },
    };
  },
  methods: {
    editarPerfil() {
      this.$router.push({ name: "ActualizarPerfil", params: { id: this.usuario.id } });
    },
    verNotificaciones() {
      this.$router.push({ name: "Notificaciones", params: { id: this.usuario.id } });
    },
    registrarProducto() {
      this.$router.push({ name: "RegistrarProducto", params: { id: this.usuario.id } });
    },
    verProductos() {
      this.$router.push({ name: "Productos", params: { id: this.usuario.id } });
    },
  },
  mounted() {
    const id = this.$route.params.id;
    axios
      .get(`http://localhost:8011/api/cutienda/${id}`)
      .then((response) => {
        this.usuario = response.data;
      })
      .catch((error) => {
        console.error("Error al obtener el usuario:", error);
      });
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap");

body {
  font-family: "Roboto" ,"Inter", sans-serif;
  background-color: #f4f4f9;
  margin: 0;
  padding: 0;
  color: #333;
}

.encabezado {
  background-color: #0044cc;
  color: white;
  padding: 1.5rem 0;
  text-align: center;
}

.titulo {
  font-size: 1.8rem;
  font-weight: bold;
  margin: 0;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem;
  gap: 2rem;
}

.profile-section {
  text-align: center;
  max-width: 300px;
  color: #f9f9f9;
}

.profile-image {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 1rem;
  border: 2px solid #ddd;
}

.nombre {
  font-size: 1.5rem;
  font-weight: bold;
  margin: 0.5rem 0;
}

.text-muted {
  font-size: 0.9rem;
}

.actions-section {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  max-width: 300px;
}

.action-button {
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  font-weight: 600;
  color: white;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.action-button:hover {
  background-color: #0056b3;
}

.action-button:active {
  background-color: #004494;
}
</style>
