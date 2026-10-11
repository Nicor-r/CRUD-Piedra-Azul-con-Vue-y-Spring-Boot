package co.piedraazul.crud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Objeto que viaja entre Vue y Spring (el JSON se convierte en este DTO)
public record PacienteDTO(
        Long id,
        @NotBlank(message = "El nombre completo es obligatorio") String nombreCompleto,
        @NotBlank(message = "El correo es obligatorio") @Email(message = "El correo no es válido") String email,
        @NotBlank(message = "El número de identificación es obligatorio") String numeroIdentificacion,
        @NotBlank(message = "El número telefónico es obligatorio") String numeroTelefonico
) {}
