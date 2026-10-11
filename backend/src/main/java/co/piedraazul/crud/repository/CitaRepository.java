package co.piedraazul.crud.repository;

import co.piedraazul.crud.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    boolean existsByPacienteId(Long pacienteId);
}
