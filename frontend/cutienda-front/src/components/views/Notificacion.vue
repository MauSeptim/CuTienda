<template>
  <div class="padre">
    <div class="page-wrapper" :class="{x: !hayNotificaciones()}">
      <div class="encabezado" >
        <h1>{{ mensaje }}</h1>
      </div>
  
      <div class="notificaciones" v-if="hayNotificaciones()">
        <div v-for="notificacion in notificacionesFiltradas" :key="notificacion.id" class="notificacion">
          <h3>{{ notificacion.categoria }}</h3>
          <p>{{ notificacion.mensaje }}</p>
          <small>{{ notificacion.fecha }}</small>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import CutiendaLeon from "../icons/CutiendaLeon.vue";
import axios from "axios";

export default {
  name: 'Notificacion',
  props: {
    user_id: {
      type: Number,
      required: true
    }
  },
  components: {
    CutiendaLeon,
  },
  data() {
    return {
      mensaje: 'Notificaciones',
      notificaciones: [],
      intervalId: null,
    };
  },
  computed: {
    notificacionesFiltradas() {
      return this.notificaciones.filter(notificacion => notificacion.id_usuario !== this.usuarioId);
    }
  },
  created() {
    this.usuarioId = this.$route.params.id;
    this.obtenerNotificaciones();
  },
  mounted() {
    // Actualizar las notificaciones cada 20 segundos
    this.intervalId = setInterval(() => {
      this.obtenerNotificaciones();
    }, 20000);
  },
  unmounted() {
    // Limpiar el intervalo cuando el componente se desmonta
    clearInterval(this.intervalId);
  },
  methods: {
    async obtenerNotificaciones() {
      try {
        const response = await axios.get(`http://localhost:8012/api/notificaciones/user/${this.user_id}`);
        this.notificaciones = response.data;
      } catch (error) {
        console.error('Error al obtener las notificaciones:', error);
        Swal.fire('Error', 'Error al obtener las notificaciones', 'error');
      }
    },
    hayNotificaciones() {
     let res = this.notificaciones.length > 0;
     this.mensaje = res ? 'Notificaciones' : 'No hay notificaciones';
     return res;
    }
  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');

.padre {
  display: flex;
  justify-content: center;
  padding: 20px;
  width: 100%;
  height: 90vh;
  background-color: #002060; /* Fondo azul */
  color: #fff; /* Texto blanco */
  font-family: 'Roboto', sans-serif;
}


h2 {
  font-weight: 100;
  font-size: 5em;
}

h1 {
  font-weight: 500;
  font-size: 3em;
}

.page-wrapper {
  width: 70%;
  height: 100%;
}

.encabezado {
  display: flex;
  align-items: center;
  justify-content: left;
  margin-bottom: 20px;
}

.encabezado span {
  font-size: 24px;
  font-weight: bold;
  margin-left: 10px;
}

.notificaciones {
  width: 100%;
  max-width: 600px;
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  color: #333; /* Texto oscuro dentro del contenedor */
}

.notificacion {
  margin-bottom: 15px;
  padding: 15px;
  border-bottom: 1px solid #ccc;
  transition: all 400ms ease;
}

.notificacion h3 {
  margin: 0;
  font-size: 18px;
  color: #007bff;
}

.notificacion p {
  margin: 5px 0;
}

.notificacion small {
  color: #666;
}

.notificacion:last-child {
  border-bottom: none;
}
.x {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>