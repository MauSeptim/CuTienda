// src/router.js

import { createRouter, createWebHistory } from 'vue-router'
import PaginaCrearCuenta from '@/components/views/PaginaCrearCuenta.vue' 
import PaginaProductos from '@/components/views/PaginaProductos.vue'
import PaginaUsuario from '@/components/views/PaginaUsuario.vue'
import Login from '@/components/views/Login.vue'
import Notificacion from '@/components/views/Notificacion.vue'
import RegistrarProducto from '@/components/views/RegistrarProducto.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/productos/:id',
    name: 'Productos',
    component: PaginaProductos
  },
  {
    path: '/usuario/:id',
    name: 'Usuario',
    component: PaginaUsuario
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/crear-cuenta',
    name: 'CrearCuenta',
    component: PaginaCrearCuenta
  },
  {
    path: '/notificaciones/:id',
    name: 'Notificaciones',
    component: Notificacion
  },
  {
    path: '/registrar-producto/:id',
    name : 'RegistrarProducto',
    component: RegistrarProducto
  }
  // Puedes añadir más rutas aquí
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
