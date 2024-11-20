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
            <button type="submit" class="button">Iniciar Sesión</button>
            <button @click="crearCuenta" class="button alt">Crear Cuenta</button>
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
      this.$router.push('/crear-cuenta');
    },
    async submitForm() {
      const form = new FormData();
      form.append('email', this.formData.email);
      form.append('password', this.formData.password);
      form.append('role', this.formData.role);
      
      try {
        const response = await axios.post('http://localhost:8011/api/cutienda/login', form);
        Swal.fire('Éxito', 'Inicio de sesión exitoso', 'success');
        this.$router.push({ name: 'Usuario', params: { id: response.data.usuario.id } });
      } catch (error) {
        Swal.fire('Error', error.response?.data?.error || 'Error durante la autenticación', 'error');
      }
    }
  }
};
</script>

<style scoped>
html, body {
  height: 100%;
  margin: 0;
  padding: 0;
}

.padre {
  display: flex;
  flex-direction: column;
  align-items: center;
  color: #fff;
  height: 100vh;
  background: linear-gradient(135deg, #002060, #004d99); /* Gradiente azul */
  padding: 20px;
}

.encabezado img {
  gap: 1259px;
  margin-top: 40px;
  width: 7rem;
  height: 7rem;
}

.encabezado {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  gap: 15px;
  margin-bottom: 30px;
}

.encabezado span {
  font-size: 28px;
  font-weight: bold;
  text-align: center;
}

.container {
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 500px;
  padding: 25px;
  border-radius: 16px;
  background-color: #ffffff;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
  color: #333;
}

.roles {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
}

.inputs {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  color: #333;
}

.input-field {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 16px;
  box-sizing: border-box;
  transition: all 0.3s ease-in-out;
}

.input-field:focus {
  border-color: #007bff;
  box-shadow: 0 0 6px rgba(0, 123, 255, 0.25);
  outline: none;
}

.bottom-buttons {
  display: flex;
  justify-content: space-between;
  gap: 15px;
}

.button {
  padding: 12px 18px;
  background-color: #28a745; /* Verde */
  color: #fff;
  border: none;
  border-radius: 12px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease-in-out, transform 0.2s;
}

.button.alt {
  background-color: #007bff; /* Azul */
}

.button:hover {
  transform: translateY(-2px);
}

.button:active {
  transform: translateY(0);
}

@media (max-width: 480px) {
  .container {
    padding: 15px;
  }

  .form-group {
    margin-bottom: 15px;
  }

  .bottom-buttons {
    flex-direction: column;
    gap: 10px;
  }

  .button {
    width: 100%;
  }
}
@media (min-width: 1920px) {
  .padre {
    padding: 40px;
    background: linear-gradient(135deg, #001840, #002f70); /* Gradiente más oscuro */
  }

  .container {
    max-width: 800px;
  }

  .form-group input {
    font-size: 1.5rem;
    height: 3.5rem;
  }

  .button {
    font-size: 18px;
    padding: 12px 24px;
  }
}

</style>
