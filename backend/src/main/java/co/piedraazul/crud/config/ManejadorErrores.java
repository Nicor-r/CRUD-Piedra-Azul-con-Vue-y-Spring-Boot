package co.piedraazul.crud.config;

import co.piedraazul.crud.exception.NoEncontradoException;
import co.piedraazul.crud.exception.ReglaNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Convierte errores en mensajes de texto simples para el frontend
@RestControllerAdvice
public class ManejadorErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getDefaultMessage())
                .reduce((a, b) -> a + " | " + b)
                .orElse("Datos inválidos");
        return ResponseEntity.badRequest().body(mensaje);
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<String> reglaNegocio(ReglaNegocioException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(NoEncontradoException.class)
    public ResponseEntity<String> noEncontrado(NoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
