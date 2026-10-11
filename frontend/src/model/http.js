//configuración de Axios y manejo de errores del servidor
import axios from 'axios'

export const http = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' },
})

// Convierte cualquier error de Axios en un texto que se pueda mostrar
export function mensajeDeError(error) {
  if (error.response) {
    const data = error.response.data
    return typeof data === 'string' && data ? data : `Error ${error.response.status}`
  }
  return 'No se pudo conectar con el servidor. ¿Está corriendo Spring Boot?'
}
