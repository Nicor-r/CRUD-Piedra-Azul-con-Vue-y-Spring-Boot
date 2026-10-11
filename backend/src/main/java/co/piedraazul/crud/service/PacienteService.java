package co.piedraazul.crud.service;

import co.piedraazul.crud.dto.PacienteDTO;
import co.piedraazul.crud.exception.NoEncontradoException;
import co.piedraazul.crud.exception.ReglaNegocioException;
import co.piedraazul.crud.model.Paciente;
import co.piedraazul.crud.repository.CitaRepository;
import co.piedraazul.crud.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final CitaRepository citaRepository;

    public PacienteService(PacienteRepository pacienteRepository, CitaRepository citaRepository) {
        this.pacienteRepository = pacienteRepository;
        this.citaRepository = citaRepository;
    }

    public List<PacienteDTO> listar() {
        return pacienteRepository.findAll().stream().map(this::aDTO).toList();
    }

    // PASO 4: valida las reglas y convierte el DTO en Entity
    @Transactional
    public PacienteDTO crear(PacienteDTO dto) {
        validarUnicos(dto, -1L);
        Paciente entidad = aEntidad(new Paciente(), dto);
        // PASO 5: el Repository inserta el registro en la base de datos
        return aDTO(pacienteRepository.save(entidad));
    }

    @Transactional
    public PacienteDTO actualizar(Long id, PacienteDTO dto) {
        Paciente existente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("El paciente no existe."));
        validarUnicos(dto, id);
        return aDTO(pacienteRepository.save(aEntidad(existente, dto)));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new NoEncontradoException("El paciente no existe.");
        }
        if (citaRepository.existsByPacienteId(id)) {
            throw new ReglaNegocioException("No se puede eliminar: el paciente tiene citas registradas.");
        }
        pacienteRepository.deleteById(id);
    }

    // reglas de negocio
    private void validarUnicos(PacienteDTO dto, Long idActual) {
        if (pacienteRepository.existsByEmailAndIdNot(dto.email(), idActual)) {
            throw new ReglaNegocioException("Ya existe un paciente con ese correo.");
        }
        if (pacienteRepository.existsByNumeroIdentificacionAndIdNot(dto.numeroIdentificacion(), idActual)) {
            throw new ReglaNegocioException("Ya existe un paciente con ese número de identificación.");
        }
    }

    private Paciente aEntidad(Paciente p, PacienteDTO dto) {
        p.setNombreCompleto(dto.nombreCompleto());
        p.setEmail(dto.email());
        p.setNumeroIdentificacion(dto.numeroIdentificacion());
        p.setNumeroTelefonico(dto.numeroTelefonico());
        return p;
    }

    private PacienteDTO aDTO(Paciente p) {
        return new PacienteDTO(p.getId(), p.getNombreCompleto(), p.getEmail(),
                p.getNumeroIdentificacion(), p.getNumeroTelefonico());
    }
}
