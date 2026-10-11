//estado y acciones de la pantalla de Citas
import { ref, onMounted } from 'vue'
import { citaService } from '../model/citaService'
import { pacienteService } from '../model/pacienteService'
import { mensajeDeError } from '../model/http'

const formularioVacio = () => ({
  pacienteId: '',
  fecha: '',
  hora: '',
  motivo: '',
  estado: 'PROGRAMADA',
})

export function useCitasViewModel() {
  const citas = ref([])
  const pacientes = ref([]) // para el selector del formulario
  const form = ref(formularioVacio())
  const editandoId = ref(null)
  const mensajeOk = ref('')
  const mensajeError = ref('')

  async function cargar() {
    try {
      ;[citas.value, pacientes.value] = await Promise.all([
        citaService.listar(),
        pacienteService.listar(),
      ])
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
        const actualizada = await citaService.actualizar(editandoId.value, form.value)
        const i = citas.value.findIndex((c) => c.id === actualizada.id)
        if (i !== -1) citas.value[i] = actualizada
        mensajeOk.value = 'Cita actualizada'
      } else {
        const creada = await citaService.crear(form.value)
        // PASO 6: llega 201 + JSON; al agregarla, la lista se actualiza por reactividad
        citas.value.push(creada)
        mensajeOk.value = 'Cita creada'
      }
      cancelar()
    } catch (e) {
      mensajeError.value = mensajeDeError(e)
    }
  }

  function editar(cita) {
    editandoId.value = cita.id
    form.value = {
      pacienteId: cita.pacienteId,
      fecha: cita.fecha,
      hora: cita.hora.substring(0, 5), // "09:30:00" -> "09:30"
      motivo: cita.motivo,
      estado: cita.estado,
    }
    mensajeOk.value = ''
    mensajeError.value = ''
  }

  function cancelar() {
    editandoId.value = null
    form.value = formularioVacio()
  }

  async function eliminar(cita) {
    if (!confirm('¿Eliminar esta cita?')) return
    mensajeOk.value = ''
    mensajeError.value = ''
    try {
      await citaService.eliminar(cita.id)
      citas.value = citas.value.filter((c) => c.id !== cita.id)
      mensajeOk.value = 'Cita eliminada'
    } catch (e) {
      mensajeError.value = mensajeDeError(e)
    }
  }

  onMounted(cargar)

  return { citas, pacientes, form, editandoId, mensajeOk, mensajeError, guardar, editar, cancelar, eliminar }
}
