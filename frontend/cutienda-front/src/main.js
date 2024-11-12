// src/main.js
import { createApp } from 'vue';
import App from './App.vue';
import router from './router/router';

const app = createApp(App);
app.use(router); // Usa el router en tu aplicación
app.mount('#app');
