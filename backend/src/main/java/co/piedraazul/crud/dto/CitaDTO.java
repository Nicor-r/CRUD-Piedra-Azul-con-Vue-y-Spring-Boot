package co.piedraazul.crud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

// pacienteNombre solo se usa en las respuestas (para mostrarlo en la tabla)
public record CitaDTO(
        Long id,
        @NotNull(message = "Debes seleccionar un paciente") Long pacienteId,
        String pacienteNombre,
        @NotNull(message = "La fecha es obligatoria") LocalDate fecha,
        @NotNull(message = "La hora es obligatoria") LocalTime hora,
        @NotBlank(message = "El motivo es obligatorio") String motivo,
        String estado
) {}
