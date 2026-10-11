package co.piedraazul.crud.service;

import co.piedraazul.crud.dto.CitaDTO;
import co.piedraazul.crud.exception.NoEncontradoException;
import co.piedraazul.crud.exception.ReglaNegocioException;
import co.piedraazul.crud.model.Cita;
import co.piedraazul.crud.model.Paciente;
import co.piedraazul.crud.repository.CitaRepository;
import co.piedraazul.crud.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;

    public CitaService(CitaRepository citaRepository, PacienteRepository pacienteRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public List<CitaDTO> listar() {
        return citaRepository.findAll().stream().map(this::aDTO).toList();
    }

    // PASO 4: valida las reglas y convierte el DTO en Entity
    @Transactional
    public CitaDTO crear(CitaDTO dto) {
        Cita entidad = aEntidad(new Cita(), dto);
        // PASO 5: el Repository inserta el registro en la base de datos
        return aDTO(citaRepository.save(entidad));
    }

    @Transactional
    public CitaDTO actualizar(Long id, CitaDTO dto) {
        Cita existente = citaRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("La cita no existe."));
        return aDTO(citaRepository.save(aEntidad(existente, dto)));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new NoEncontradoException("La cita no existe.");
        }
        citaRepository.deleteById(id);
    }

    //reglas de negocio 
    private Cita aEntidad(Cita c, CitaDTO dto) {
        Paciente paciente = pacienteRepository.findById(dto.pacienteId())
                .orElseThrow(() -> new ReglaNegocioException("El paciente seleccionado no existe."));
        c.setPaciente(paciente);
        c.setFecha(dto.fecha());
        c.setHora(dto.hora());
        c.setMotivo(dto.motivo());
        c.setEstado(dto.estado() == null || dto.estado().isBlank() ? "PROGRAMADA" : dto.estado());
        return c;
    }

    private CitaDTO aDTO(Cita c) {
        return new CitaDTO(c.getId(), c.getPaciente().getId(), c.getPaciente().getNombreCompleto(),
                c.getFecha(), c.getHora(), c.getMotivo(), c.getEstado());
    }
}
