<template>
    <div class="padre">
      <div class="page-wrapper">
        <div class="encabezado">
          <h1>Actualizar Perfil</h1>
        </div>
  
        <form @submit.prevent="actualizarPerfil" class="formulario">
          <div class="campo">
            <label for="nombre">Nombre</label>
            <input v-model="perfil.nombre" type="text" id="nombre" placeholder="Ingresa tu nombre" />
          </div>
  
          <div class="campo">
            <label for="apellidos">Apellidos</label>
            <input v-model="perfil.apellidos" type="text" id="apellidos" placeholder="Ingresa tus apellidos" />
          </div>
  
          <div class="campo">
            <label for="correo">Correo Electrónico</label>
            <input v-model="perfil.correoElectronico" type="email" id="correo" placeholder="Ingresa tu correo" />
          </div>
  
          <div class="campo">
            <label for="telefono">Teléfono</label>
            <input v-model="perfil.telefono" type="text" id="telefono" placeholder="Ingresa tu teléfono" />
          </div>
  
          <div class="campo">
            <label for="nuevaContraseña">Nueva Contraseña</label>
            <input v-model="perfil.newPassword" type="password" id="nuevaContraseña" placeholder="Nueva contraseña" />
          </div>
  
          <div class="campo">
            <label for="confirmacionContraseña">Confirmar Contraseña</label>
            <input v-model="perfil.passwordConfirm" type="password" id="confirmacionContraseña" placeholder="Confirma contraseña" />
          </div>
  
          <div class="campo">
            <label for="foto">Foto de Perfil</label>
            <input @change="seleccionarArchivo" type="file" id="foto" />
          </div>
  
          <div class="botones">
            <button type="submit" class="btn-guardar">Guardar Cambios</button>
            <button type="button" @click="irseAtras" class="btn-cancelar">Cancelar</button>
          </div>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  import Swal from "sweetalert2";
  
  export default {
    name: "ActualizarPerfil",
    data() {
      return {
        perfil: {
          nombre: "",
          apellidos: "",
          correoElectronico: "",
          telefono: "",
          newPassword: "",
          passwordConfirm: "",
        },
        foto: null,
        id: this.$route.params.id,
      };
    },
    methods: {
      async actualizarPerfil() {
        if (this.perfil.newPassword !== null && this.perfil.newPassword !== this.perfil.passwordConfirm) {
            Swal.fire("Error", "Las contraseñas no coinciden", "error");
            return;
        }
        const formData = new FormData();
        Object.keys(this.perfil).forEach((key) => {
          if (this.perfil[key]) {
            formData.append(key, this.perfil[key]);
          }
        });
        if (this.foto) {
          formData.append("foto", this.foto);
        }
  
        try {
          const response = await axios.post(`http://localhost:8011/api/cutienda/perfil/actualizar/${this.id}`, formData, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          });
          Swal.fire("Éxito", response.data.message, "success");
          this.irseAtras();
        } catch (error) {
          const mensaje = error.response?.data?.error || "Error al actualizar el perfil";
          Swal.fire("Error", mensaje, "error");
        }
      },
      irseAtras() {
        this.$router.go(-1);
      },
      seleccionarArchivo(event) {
        this.foto = event.target.files[0];
      },
    },
  };
  </script>
  
  <style scoped>
  @import url("https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap");
  
  .padre {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 4rem;
    background-color: #002060;
    font-family: "Roboto", sans-serif;
  }
  
  .page-wrapper {
    width: 100%;
    max-width: 500px;
    background: #ffffff;
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    padding: 20px;
  }
  
  .encabezado h1 {
    font-size: 1.8rem;
    color: #002060;
    text-align: center;
    margin-bottom: 20px;
  }
  
  .formulario .campo {
    margin-bottom: 15px;
  }
  
  .formulario label {
    display: block;
    font-weight: 500;
    margin-bottom: 5px;
    color: #333;
  }
  
  .formulario input {
    width: 95%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 14px;
  }
  
  .botones {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
  }
  
  .btn-guardar {
    background-color: #007bff;
    color: #fff;
    padding: 10px 15px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .btn-cancelar {
    background-color: #e0e0e0;
    color: #333;
    padding: 10px 15px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .btn-guardar:hover {
    background-color: #0056b3;
  }
  
  .btn-cancelar:hover {
    background-color: #d6d6d6;
  }
  </style>
  