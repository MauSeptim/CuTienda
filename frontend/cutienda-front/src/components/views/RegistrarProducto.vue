<template>
    <div class="register-product">
        <CutiendaLeonVue />
        <h1>Registrar Producto</h1>
        <form @submit.prevent="registerProduct">
            <div class="form-group">
                <label for="nombre_producto">Nombre del Producto</label>
                <input type="text" id="nombre_producto" v-model="product.nombre_producto" required />
            </div>
            <div class="form-group">
                <label for="id_usuario">ID Usuario</label>
                <input type="number" id="id_usuario" v-model="product.id_usuario" required />
            </div>
            <div class="form-group">
                <label for="descripcion">Descripción</label>
                <textarea id="descripcion" v-model="product.descripcion" required></textarea>
            </div>
            <div class="form-group">
                <label for="tipo">Tipo</label>
                <input type="text" id="tipo" v-model="product.tipo" required />
            </div>
            <div class="form-group">
                <label for="precio">Precio</label>
                <input type="number" step="0.01" id="precio" v-model="product.precio" required />
            </div>
            <div class="form-group">
                <label for="latitud">Latitud</label>
                <input type="number" step="0.000001" id="latitud" v-model="product.latitud" />
            </div>
            <div class="form-group">
                <label for="longitud">Longitud</label>
                <input type="number" step="0.000001" id="longitud" v-model="product.longitud" />
            </div>
            <div class="form-group">
                <label for="foto">Foto</label>
                <input type="file" id="foto" @change="onFileChange" />
            </div>
            <button type="submit">Registrar</button>
        </form>
    </div>
</template>

<script>
import axios from 'axios';
import CutiendaLeonVue from '../icons/CutiendaLeon.vue';

export default {
    name: 'RegistrarProducto',
    components: {
        CutiendaLeonVue,
    },
    data() {
        return {
            product: {
                nombre_producto: '',
                id_usuario: null,
                descripcion: '',
                tipo: '',
                precio: null,
                latitud: null,
                longitud: null,
                foto: null,
            },
        };
    },
    methods: {
        onFileChange(event) {
            this.product.foto = event.target.files[0];
        },
        async registerProduct() {
            const formData = new FormData();
            for (const key in this.product) {
                formData.append(key, this.product[key]);
            }
            try {
                const response = await axios.post('http://localhost:8010/cutienda/api/productos/registro', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });
                alert('Producto registrado con éxito');
            } catch (error) {
                console.error(error);
                alert('Error al registrar el producto');
            }
        },
    },
};
</script>

<style scoped>
.register-product {
    background-color: #002060;
    color: white;
    padding: 20px;
    border-radius: 10px;
    max-width: 600px;
    margin: auto;
}

h1 {
    text-align: center;
}

.form-group {
    margin-bottom: 15px;
}

label {
    display: block;
    margin-bottom: 5px;
}

input,
textarea {
    width: 100%;
    padding: 10px;
    border-radius: 5px;
    border: none;
}

button {
    display: block;
    width: 100%;
    padding: 10px;
    background-color: #004080;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

button:hover {
    background-color: #003060;
}
</style>