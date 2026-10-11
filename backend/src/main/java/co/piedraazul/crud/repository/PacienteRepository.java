package co.piedraazul.crud.repository;

import co.piedraazul.crud.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByNumeroIdentificacionAndIdNot(String numeroIdentificacion, Long id);
}
