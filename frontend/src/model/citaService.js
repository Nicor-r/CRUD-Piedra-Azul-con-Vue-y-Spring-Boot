//llamadas HTTP de la entidad Cita
import { http } from './http'

export const citaService = {
  listar: () => http.get('/citas').then((r) => r.data),
  crear: (datos) => http.post('/citas', datos).then((r) => r.data),
  actualizar: (id, datos) => http.put(`/citas/${id}`, datos).then((r) => r.data),
  eliminar: (id) => http.delete(`/citas/${id}`),
}
