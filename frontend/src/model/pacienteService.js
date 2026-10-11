//llamadas HTTP de la entidad Paciente
import { http } from './http'

export const pacienteService = {
  listar: () => http.get('/pacientes').then((r) => r.data),
  crear: (datos) => http.post('/pacientes', datos).then((r) => r.data),
  actualizar: (id, datos) => http.put(`/pacientes/${id}`, datos).then((r) => r.data),
  eliminar: (id) => http.delete(`/pacientes/${id}`),
}
