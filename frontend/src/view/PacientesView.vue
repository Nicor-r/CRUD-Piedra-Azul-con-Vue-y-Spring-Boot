<script setup>
//no tiene lógica propia: todo viene del ViewModel.
import { usePacientesViewModel } from '../viewmodel/usePacientesViewModel'

const { pacientes, form, editandoId, mensajeOk, mensajeError, guardar, editar, cancelar, eliminar } =
  usePacientesViewModel()
</script>

<template>
  <div>
    <div v-if="mensajeOk" class="mensaje ok">{{ mensajeOk }}</div>
    <div v-if="mensajeError" class="mensaje error">{{ mensajeError }}</div>

    <section class="panel">
      <h2>{{ editandoId ? 'Editar paciente' : 'Nuevo paciente' }}</h2>
      <!-- PASO 1: el usuario llena el formulario (v-model) y pulsa Guardar -->
      <form class="grid" @submit.prevent="guardar">
        <div class="campo">
          <label for="nombre">Nombre completo</label>
          <input id="nombre" v-model="form.nombreCompleto" required />
        </div>
        <div class="campo">
          <label for="email">Correo electrónico</label>
          <input id="email" type="email" v-model="form.email" required />
        </div>
        <div class="campo">
          <label for="identificacion">Número de identificación</label>
          <input id="identificacion" v-model="form.numeroIdentificacion" required />
        </div>
        <div class="campo">
          <label for="telefono">Número telefónico</label>
          <input id="telefono" type="tel" v-model="form.numeroTelefonico" required />
        </div>
        <div class="acciones">
          <button class="btn" type="submit">{{ editandoId ? 'Guardar cambios' : 'Guardar paciente' }}</button>
          <button v-if="editandoId" class="btn secundario" type="button" @click="cancelar">Cancelar</button>
        </div>
      </form>
    </section>

    <section class="panel">
      <h2>Pacientes registrados</h2>
      <p v-if="!pacientes.length" class="vacio">Aún no hay pacientes. Crea el primero con el formulario.</p>
      <div v-else class="tabla-scroll">
        <table>
          <thead>
            <tr><th>Nombre</th><th>Correo</th><th>Identificación</th><th>Teléfono</th><th></th></tr>
          </thead>
          <tbody>
            <tr v-for="p in pacientes" :key="p.id">
              <td>{{ p.nombreCompleto }}</td>
              <td>{{ p.email }}</td>
              <td>{{ p.numeroIdentificacion }}</td>
              <td>{{ p.numeroTelefonico }}</td>
              <td class="celda-acciones">
                <button class="btn secundario chico" @click="editar(p)">Editar</button>
                <button class="btn peligro chico" @click="eliminar(p)">Eliminar</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  </div>
</template>
