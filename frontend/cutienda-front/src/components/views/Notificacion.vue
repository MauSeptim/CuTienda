<template>
  <div class="padre">
    <div class="page-wrapper" :class="{ x: !hayNotificaciones() }">
      <div class="encabezado">
        <h1>{{ mensaje }}</h1>
      </div>

      <div class="notificaciones" v-if="hayNotificaciones()">
        <div v-for="notificacion in notificacionesFiltradas" :key="notificacion.id" class="notificacion">
          <h3>{{ notificacion.categoria }}</h3>
          <p>{{ notificacion.mensaje }}</p>
          <small>{{ formatFecha(notificacion.fecha) }}</small>
        </div>
      </div>

      <div v-else class="sin-notificaciones">
        <p>No tienes notificaciones en este momento.</p>
      </div>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import { format } from "date-fns";
import { es } from "date-fns/locale";
import axios from "axios";

export default {
  name: "Notificacion",
  data() {
    return {
      mensaje: "Notificaciones",
      notificaciones: [],
      id: this.$route.params.id,
      intervalId: null,
    };
  },
  computed: {
    notificacionesFiltradas() {
      return this.notificaciones.filter((notificacion) => notificacion.id_usuario !== this.id);
    },
  },
  created() {
    this.id = this.$route.params.id;
    this.obtenerNotificaciones();
  },
  mounted() {
    this.intervalId = setInterval(() => {
      this.obtenerNotificaciones();
    }, 20000);
  },
  unmounted() {
    clearInterval(this.intervalId);
  },
  methods: {
    async obtenerNotificaciones() {
      try {
        const response = await axios.get(`http://localhost:8012/api/notificaciones/user/${this.id}`);
        this.notificaciones = response.data;
      } catch (error) {
        console.error("Error al obtener las notificaciones:", error);
        Swal.fire("Error", "Error al obtener las notificaciones", "error");
      }
    },
    hayNotificaciones() {
      const res = this.notificaciones.length > 0;
      this.mensaje = res ? "Notificaciones" : "No hay notificaciones";
      return res;
    },
    formatFecha(fecha) {
      return format(new Date(fecha), "PPP, p", { locale: es });
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap");

.padre {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 4rem;
  background-color: #002060;
  font-family: "Roboto", "Inter", sans-serif;
}

.page-wrapper {
  display: flex;
  flex-direction: column;
  gap: 3rem;
  width: 100%;
  max-width: 600px;
}

.encabezado h1 {
  font-size: 2rem;
  color: #f9f9f9;
  text-align: center;
}

.notificaciones {
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
}

.notificacion {
  margin-bottom: 15px;
  padding: 15px;
  border-bottom: 1px solid #e0e0e0;
  transition: all 0.3s ease;
}

.notificacion:hover {
  background-color: #f9f9f9;
}

.notificacion h3 {
  font-size: 1.2rem;
  color: #007bff;
  margin-bottom: 5px;
}

.notificacion p {
  margin: 0 0 10px;
  color: #333;
}

.notificacion small {
  font-size: 0.9rem;
  color: #666;
}

.sin-notificaciones {
  text-align: center;
  color: #666;
  font-size: 1.2rem;
}
</style>
