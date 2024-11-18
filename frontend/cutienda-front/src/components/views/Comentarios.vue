<template>
    <div class="comentarios-container">
        <div class="product-info">
            <img :src="producto.fotoBase64" alt="Imagen del producto" class="product-image" />
            <h2>{{ producto.nombre }}</h2>
            <p>{{ producto.descripcion }}</p>
            <button @click="openGoogleMaps">Ver en Google Maps</button>
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
                <button @click="guardarComentario">Enviar</button>
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
            producto: {},
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
.comentarios-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
}

.product-info {
    text-align: center;
}

.product-image {
    width: 300px;
    height: auto;
    border-radius: 10px;
}

.comments-section {
    width: 100%;
    max-width: 600px;
    margin-top: 20px;
}

.comment {
    border-bottom: 1px solid #ccc;
    padding: 10px 0;
}

.rating {
    display: flex;
}

.star {
    font-size: 20px;
    cursor: pointer;
}

.star.filled {
    color: gold;
}

.add-comment {
    margin-top: 20px;
}

textarea {
    width: 100%;
    height: 100px;
    margin-bottom: 10px;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
}
</style>