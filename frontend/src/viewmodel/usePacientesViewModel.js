// estado y acciones de la pantalla de Pacientes
// La View solo se enlaza a lo que este archivo expone; aquí no hay HTML
import { ref, onMounted } from 'vue'
import { pacienteService } from '../model/pacienteService'
import { mensajeDeError } from '../model/http'

const formularioVacio = () => ({
  nombreCompleto: '',
  email: '',
  numeroIdentificacion: '',
  numeroTelefonico: '',
})

export function usePacientesViewModel() {
  // Estado reactivo: cuando cambia, la View se actualiza sola
  const pacientes = ref([])
  const form = ref(formularioVacio())
  const editandoId = ref(null)
  const mensajeOk = ref('')
  const mensajeError = ref('')

  async function cargar() {
    try {
      pacientes.value = await pacienteService.listar()
    } catch (e) {
      mensajeError.value = mensajeDeError(e)
    }
  }

  // PASO 2: guardar() envía el POST (o PUT si se está editando) con Axios
  async function guardar() {
    mensajeOk.value = ''
    mensajeError.value = ''
    try {
      if (editandoId.value) {
        const actualizado = await pacienteService.actualizar(editandoId.value, form.value)
        const i = pacientes.value.findIndex((p) => p.id === actualizado.id)
        if (i !== -1) pacientes.value[i] = actualizado
        mensajeOk.value = 'Paciente actualizado'
      } else {
        const creado = await pacienteService.crear(form.value)
        // PASO 6: llega 201 + JSON; al agregarlo, la lista se actualiza por reactividad.
        pacientes.value.push(creado)
        mensajeOk.value = 'Paciente creado'
      }
      cancelar()
    } catch (e) {
      mensajeError.value = mensajeDeError(e)
    }
  }

  function editar(paciente) {
    editandoId.value = paciente.id
    form.value = { ...paciente }
    mensajeOk.value = ''
    mensajeError.value = ''
  }

  function cancelar() {
    editandoId.value = null
    form.value = formularioVacio()
  }

  async function eliminar(paciente) {
    if (!confirm(`¿Eliminar a ${paciente.nombreCompleto}?`)) return
    mensajeOk.value = ''
    mensajeError.value = ''
    try {
      await pacienteService.eliminar(paciente.id)
      pacientes.value = pacientes.value.filter((p) => p.id !== paciente.id)
      mensajeOk.value = 'Paciente eliminado'
    } catch (e) {
      mensajeError.value = mensajeDeError(e)
    }
  }

  onMounted(cargar)

  return { pacientes, form, editandoId, mensajeOk, mensajeError, guardar, editar, cancelar, eliminar }
}
