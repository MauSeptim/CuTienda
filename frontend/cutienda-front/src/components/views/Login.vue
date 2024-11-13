<template>
  <div class="padre">
    <div class="encabezado">
      <CutiendaLeon />
      <span>Iniciar Sesión</span>
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
            <label for="email">Correo Electrónico</label>
            <input
              class="input-field"
              type="email"
              id="email"
              v-model="formData.email"
              required
            />
          </div>
          <div class="form-group">
            <label for="password">Contraseña</label>
            <input
              class="input-field"
              type="password"
              id="password"
              v-model="formData.password"
              required
            />
          </div>
          <div class="bottom-buttons">
            <button type="submit" class="button">INICIAR SESIÓN</button>
            <button @click="crearCuenta" class="button">CREAR CUENTA</button>
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
  name: 'Login',
  components: {
    CutiendaLeon,
    CustomRadio,
  },
  data() {
    return {
      formData: {
        email: '',
        password: '',
        role: ''
      }
    };
  },
  methods: {
    crearCuenta() {
      this.$router.push('/');
    },
    submitForm() {
      const form = new FormData();
      form.append('email', this.formData.email);
      form.append('password', this.formData.password); 
      form.append('role', this.formData.role);

      axios.post('http://localhost:8011/api/cutienda/login', form)
        .then(response => {
          // Manejar la respuesta exitosa aquí
          console.log(response.data);
          Swal.fire('Éxito', 'Inicio de sesión exitoso', 'success');
        })
        .catch(error => {
          // Manejar el error aquí
          console.error(error.response.data);
          Swal.fire('Error', error.response.data.error || 'Error durante la autenticación', 'error');
        });
    }
  }
};
</script>

<style scoped>
.padre {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #002060; /* Fondo azul */
  color: #fff; /* Texto blanco */
}

.encabezado img {
    width: 9rem;
    height: 9rem;
}
.encabezado {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 40px;
  flex-direction: column;
  margin-bottom: 20px;
}

.encabezado span {
  font-size: 24px;
  font-weight: bold;
  margin-left: 10px;
}

.container {
    display: flex;
    flex-direction: column;
  width: 100%;
  max-width: 600px;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  color: #333; /* Texto oscuro dentro del contenedor */
  gap: 10px;
}

.roles {
  margin-bottom: 20px;
}

.inputs {
    padding: 20px; 
    width: 100%;
}

.form-group {
  margin-bottom: 15px;
  padding: 8px;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.form-group input {
    border-radius: 20px;
    height: 3.0rem;
    font-size: 1.2rem;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #f9f9f9; /* Texto blanco */
}

.input-field {
  width: 100%;
  padding: 10px;
  border-radius: 4px;
  box-sizing: border-box;
}

.bottom-buttons {
  display: flex;
  justify-content: center;
  margin-top: 35px;
  gap: 30px;
}

.button {
  padding: 10px 20px;
  background-color: #28a745; /* Verde */
  color: #fff;
  border: none;
  border-radius: 9px;
  cursor: pointer;
  font-size: 16px;
}

.button:hover {
  background-color: #218838;
}
</style>