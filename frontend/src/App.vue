<script setup>
import { ref } from 'vue'
import PacientesView from './view/PacientesView.vue'
import CitasView from './view/CitasView.vue'

const vista = ref('pacientes')
</script>

<template>
  <div class="contenedor">
    <header class="marca">
      <div class="logo">PA</div>
      <div>
        <h1>Piedra Azul</h1>
        <p>Gestión de pacientes y citas</p>
      </div>
    </header>

    <nav class="tabs">
      <button :class="{ activa: vista === 'pacientes' }" @click="vista = 'pacientes'">Pacientes</button>
      <button :class="{ activa: vista === 'citas' }" @click="vista = 'citas'">Citas</button>
    </nav>

    <!-- v-if/v-else recarga los datos cada vez que se cambia de pestaña -->
    <PacientesView v-if="vista === 'pacientes'" />
    <CitasView v-else />
  </div>
</template>
