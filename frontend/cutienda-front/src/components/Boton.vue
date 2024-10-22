<script setup>
import { defineProps } from 'vue';
const props = defineProps({
    texto: String
})
</script>

<template>
    <button class="boton-azul">{{ texto }}</button>
</template>

<style>
.boton-azul {
    cursor: pointer;
    border-radius: 5px;
    padding: 15px 29px;
    width: auto;
    height: 4vh;
    font-family: Arial, Helvetica, sans-serif;
    color: #F5F5F5;
    text-align: center;
    border: none;
    background-color: #4471C4;
}

.boton-azul:hover {
    background-color: #3242F9;
    transition: 0.4s ease;
}
.boton-azul:active {
    background-color: #D20062;
    transition: 0.1s ease;
}
</style>