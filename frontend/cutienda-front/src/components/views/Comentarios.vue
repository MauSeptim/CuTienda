<template>
    <div class="comentarios-container">
        <div class="product-info">
            <img :src="producto.fotoBase64" alt="Imagen del producto" class="product-image" />
            <h2>{{ producto.nombre }}</h2>
            <p>{{ producto.descripcion }}</p>
            <button @click="openGoogleMaps" class="maps-button">
                <i class="fas fa-map-marker-alt"></i> Ver en Google Maps
            </button>
        </div>
        <div class="comments-section">
            <h3>Comentarios</h3>
            <div v-for="comentario in comentarios" :key="comentario.id" class="comment">
                <p>{{ comentario.contenido }}</p>
                <div class="rating">
                    <span v-for="star in 5" :key="star" class="star" :class="{ filled: star <= comentario.calificacion }">★</span>
                </div>
                <p class="comment-date">{{ formatFecha(comentario.fechaCreacion) }}</p>
            </div>
            <div v-if="!isProductOwner && !yaComento" class="add-comment">
                <textarea v-model="nuevoComentario.contenido" placeholder="Escribe tu comentario">
                </textarea>
                <div class="rating">
                    <span v-for="star in 5" :key="star" class="star" :class="{ filled: star <= nuevoComentario.calificacion }" @click="setRating(star)">★</span>
                </div>
                <button @click="guardarComentario" class="submit-button">Enviar</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
import { format } from 'date-fns';
import { es } from 'date-fns/locale';

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
            idProducto: null,
            idUsuario: null, // Aquí deberías obtener el ID del usuario autenticado
            isProductOwner: false,
            yaComento: false
        };
    },
    computed: {
        promedioEstrellas() {
            if (this.comentarios.length === 0) return 0;
            const totalEstrellas = this.comentarios.reduce((sum, comentario) => sum + comentario.calificacion, 0);
            return totalEstrellas / this.comentarios.length;
        }
    },
    methods: {
        openGoogleMaps() {
            const { latitud, longitud } = this.producto;
            window.open(`https://www.google.com/maps?q=${latitud},${longitud}`, '_blank');
        },
        setRating(star) {
            this.nuevoComentario.calificacion = star;
        },
        formatFecha(fecha) {
            return format(new Date(fecha), "dd 'de' MMMM 'del' yyyy 'a las' hh:mm a", { locale: es });
        },
        fetchProducto() {
            axios.get(`http://localhost:8010/cutienda/api/productos/${this.idProducto}`)
                .then(response => {
                    this.producto = response.data;
                    console.log(this.producto);
                });
        },
        fetchComentarios() {
            const id = +`${this.idVendedor}${this.idProducto}`;
            axios.get(`http://localhost:8013/cutienda2/comentarios/${id}`)
                .then(response => {
                    this.comentarios = response.data;
                    console.log(this.comentarios);
                    console.log(this.idUsuario);
                    this.yaComento = this.comentarios.some(({usuario}) => usuario == this.idUsuario);
                });
        },
        guardarComentario() {
            if (this.nuevoComentario.calificacion === 0) {
                Swal.fire('Error', 'Por favor, elige al menos una estrella para calificar.', 'error');
                return;
            }

            const comentario = {
                vendedor: +`${this.idVendedor}${this.idProducto}`,
                usuario: this.idUsuario,
                contenido: this.nuevoComentario.contenido,
                calificacion: this.nuevoComentario.calificacion,
                fechaCreacion: new Date().toISOString()
            };

            axios.post('http://localhost:8013/cutienda2/comentarios/guardar', comentario)
                .then(response => {
                    this.comentarios.push(response.data);
                    this.nuevoComentario.contenido = '';
                    this.nuevoComentario.calificacion = 0;
                    this.yaComento = true;
                    this.$router.go(0);
                })
                .catch(error => {
                    console.error('Error al guardar el comentario:', error);
                    Swal.fire('Error', 'Error al guardar el comentario', 'error');
                });
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
    gap: 3.5rem;
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
    font-size: 1rem;
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
    font-size: 2.2em;
    font-weight: bold;
}

.comment {
    background-color: #1a1a1a;
    padding: 15px;
    width: 100%;
    border-radius: 4px;
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
    font-size: 1em;
    color: #ccc;
}

.star.filled {
    color: #ffc107;
}

.comment-date {
    font-size: 0.8em;
    color: #aaa;
}

.add-comment {
    margin-top: 20px;
}

.add-comment textarea {
    width: 100%;
    padding: 15px;
    border-radius: 4px;
    border: 1px solid #ccc;
    background-color: #f9f9f9;
    color: #333;
    font-size: 1rem;
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