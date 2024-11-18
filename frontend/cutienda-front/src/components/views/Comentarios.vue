<template>
    <div class="comentarios-container">
        <div class="product-info">
            <img :src="producto.fotoBase64" alt="Imagen del producto" class="product-image" />
            <h2>{{ producto.nombre }}</h2>
            <p>{{ producto.descripcion }}</p>
            <button @click="openGoogleMaps" class="maps-button"> 
                Ver en Google Maps
            </button>
        </div>
        <div class="comments-section">
            <h3>Comentarios</h3>
            <div v-for="comentario in comentarios" :key="comentario.id" class="comment">
                <p>{{ comentario.contenido }}</p>
                <div class="rating">
                    <span v-for="star in 5" :key="star" class="star" :class="{ filled: star <= comentario.calificacion }">★</span>
                </div>
                <p class="comment-date">{{ comentario.fechaCreacion }}</p>
            </div>
            <div v-if="!isProductOwner" class="add-comment">
                <textarea v-model="nuevoComentario.contenido" placeholder="Escribe tu comentario"></textarea>
                <div class="rating">
                    <span v-for="star in 5" :key="star" class="star" :class="{ filled: star <= nuevoComentario.calificacion }" @click="setRating(star)">★</span>
                </div>
                <button @click="guardarComentario" class="submit-button">Enviar</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'Comentarios',
    data() {
        return {
            producto: {
                fotoBase64: '',
                nombre: '',
                descripcion: ''
            },
            comentarios: [],
            nuevoComentario: {
                contenido: '',
                calificacion: 0
            },
            idUsuario: null,
            idVendedor: null,
            idProducto: null,
            isProductOwner: false
        };
    },
    methods: {
        fetchProducto() {
            axios.get(`http://localhost:8010/cutienda/api/productos/${this.idProducto}`)
                .then(response => {
                    this.producto = response.data;
                    console.log(this.producto);
                });
        },
        fetchComentarios() {
            axios.get(`http://localhost:8013/cutienda2/comentarios/${this.idVendedor}${this.idProducto}`)
                .then(response => {
                    this.comentarios = response.data;
                });
        },
        guardarComentario() {
            const comentario = {
                vendedor: `${this.idVendedor}${this.idProducto}`,
                usuario: this.idUsuario, // Aquí deberías obtener el ID del usuario autenticado
                contenido: this.nuevoComentario.contenido,
                calificacion: this.nuevoComentario.calificacion,
                fechaCreacion: new Date().toISOString()
            };
            axios.post('http://localhost:8013/cutienda2/comentarios/guardar', comentario)
                .then(() => {
                    this.fetchComentarios();
                    this.nuevoComentario.contenido = '';
                    this.nuevoComentario.calificacion = 0;
                });
        },
        setRating(star) {
            this.nuevoComentario.calificacion = star;
        },
        openGoogleMaps() {
            const { latitud, longitud } = this.producto;
            window.open(`https://www.google.com/maps?q=${latitud},${longitud}`, '_blank');
        }
    },
    created() {
        // Aquí deberías verificar si el usuario autenticado es el propietario del producto
        this.idUsuario = this.$route.params.user_id;
        this.idVendedor = this.$route.params.id_vendedor;
        this.idProducto = this.$route.params.id_producto;
        this.isProductOwner = this.idUsuario === this.idVendedor;
        this.fetchProducto();
        this.fetchComentarios();
    }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

body {
    font-family: 'Roboto', sans-serif;
    background-color: #002060;
    color: #fff;
}

.comentarios-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
}

.product-info {
    display: flex;
    flex-flow: column;
    justify-content: center;
    align-items: center;
    gap: 30px;
    margin-bottom: 20px;
    color: white;
}

.product-image {
    width: 100%;
    max-width: 300px;
    border-radius: 8px;
    box-shadow: rgba(240, 46, 170, 0.4) 0px 5px, rgba(240, 46, 170, 0.3) 0px 10px, rgba(240, 46, 170, 0.2) 0px 15px, rgba(240, 46, 170, 0.1) 0px 20px, rgba(240, 46, 170, 0.05) 0px 25px;
}

.maps-button {
    background-color: #007bff;
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
    display: flex;
    align-items: center;
    gap: 10px;
    transition: background-color 0.3s, box-shadow 0.3s;
}

.maps-button i {
    font-size: 18px;
}

.maps-button:hover {
    background-color: #0056b3;
    box-shadow: 0 6px 10px rgba(0, 0, 0, 0.15);
}

.comments-section {
    color: white;
    width: 100%;
    max-width: 600px;
}

.comments-section h3 {
    margin-bottom: 20px;
    font-size: 24px;
    font-weight: bold;
}

.comment {
    background-color: #1a1a1a;
    padding: 15px;
    border-radius: 8px;
    margin-bottom: 15px;
}

.comment p {
    margin: 0 0 10px;
}

.rating {
    display: flex;
    gap: 5px;
}

.star {
    font-size: 20px;
    color: #ccc;
}

.star.filled {
    color: #ffc107;
}

.comment-date {
    font-size: 12px;
    color: #aaa;
}

.add-comment {
    margin-top: 20px;
}

.add-comment textarea {
    width: 100%;
    padding: 10px;
    border-radius: 4px;
    border: 1px solid #ccc;
    background-color: #f9f9f9;
    color: #333;
    margin-bottom: 10px;
}

.submit-button {
    background-color: #28a745;
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
    transition: background-color 0.3s, box-shadow 0.3s;
}

.submit-button:hover {
    background-color: #218838;
    box-shadow: 0 6px 10px rgba(0, 0, 0, 0.15);
}
</style>