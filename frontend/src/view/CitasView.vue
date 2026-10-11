<script setup>
//no tiene lógica propia: todo viene del ViewModel
import { useCitasViewModel } from '../viewmodel/useCitasViewModel'

const { citas, pacientes, form, editandoId, mensajeOk, mensajeError, guardar, editar, cancelar, eliminar } =
  useCitasViewModel()
</script>

<template>
  <div>
    <div v-if="mensajeOk" class="mensaje ok">{{ mensajeOk }}</div>
    <div v-if="mensajeError" class="mensaje error">{{ mensajeError }}</div>

    <section class="panel">
      <h2>{{ editandoId ? 'Editar cita' : 'Nueva cita' }}</h2>
      <p v-if="!pacientes.length" class="vacio">Primero registra un paciente en la pestaña Pacientes.</p>
      <!-- PASO 1: el usuario llena el formulario (v-model) y pulsa Guardar -->
      <form v-else class="grid" @submit.prevent="guardar">
        <div class="campo">
          <label for="paciente">Paciente</label>
          <select id="paciente" v-model="form.pacienteId" required>
            <option value="" disabled>Selecciona un paciente</option>
            <option v-for="p in pacientes" :key="p.id" :value="p.id">{{ p.nombreCompleto }}</option>
          </select>
        </div>
        <div class="campo">
          <label for="fecha">Fecha</label>
          <input id="fecha" type="date" v-model="form.fecha" required />
        </div>
        <div class="campo">
          <label for="hora">Hora</label>
          <input id="hora" type="time" v-model="form.hora" required />
        </div>
        <div class="campo">
          <label for="motivo">Motivo</label>
          <input id="motivo" v-model="form.motivo" required />
        </div>
        <div class="campo">
          <label for="estado">Estado</label>
          <select id="estado" v-model="form.estado">
            <option>PROGRAMADA</option>
            <option>ATENDIDA</option>
            <option>CANCELADA</option>
          </select>
        </div>
        <div class="acciones">
          <button class="btn" type="submit">{{ editandoId ? 'Guardar cambios' : 'Guardar cita' }}</button>
          <button v-if="editandoId" class="btn secundario" type="button" @click="cancelar">Cancelar</button>
        </div>
      </form>
    </section>

    <section class="panel">
      <h2>Citas registradas</h2>
      <p v-if="!citas.length" class="vacio">Aún no hay citas.</p>
      <div v-else class="tabla-scroll">
        <table>
          <thead>
            <tr><th>Paciente</th><th>Fecha</th><th>Hora</th><th>Motivo</th><th>Estado</th><th></th></tr>
          </thead>
          <tbody>
            <tr v-for="c in citas" :key="c.id">
              <td>{{ c.pacienteNombre }}</td>
              <td>{{ c.fecha }}</td>
              <td>{{ c.hora.substring(0, 5) }}</td>
              <td>{{ c.motivo }}</td>
              <td>{{ c.estado }}</td>
              <td class="celda-acciones">
                <button class="btn secundario chico" @click="editar(c)">Editar</button>
                <button class="btn peligro chico" @click="eliminar(c)">Eliminar</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  </div>
</template>
