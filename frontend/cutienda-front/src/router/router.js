// src/router.js

import { createRouter, createWebHistory } from 'vue-router'
import PaginaCrearCuenta from '@/components/views/PaginaCrearCuenta.vue' 
import PaginaProductos from '@/components/views/PaginaProductos.vue'
import PaginaUsuario from '@/components/views/PaginaUsuario.vue'
import Login from '@/components/views/Login.vue'

const routes = [
  {
    path: '/',
    name: 'CrearCuenta',
    component: PaginaCrearCuenta
  },
  {
    path: '/productos',
    name: 'Productos',
    component: PaginaProductos
  },
  {
    path: '/usuario',
    name: 'Usuario',
    component: PaginaUsuario
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
  // Puedes añadir más rutas aquí
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
