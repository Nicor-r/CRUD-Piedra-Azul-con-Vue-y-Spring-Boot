package co.piedraazul.crud.controller;

import co.piedraazul.crud.dto.PacienteDTO;
import co.piedraazul.crud.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<PacienteDTO> listar() {
        return service.listar();
    }

    // PASO 3: @PostMapping recibe el JSON convertido en DTO
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)   // PASO 6: responde 201 + JSON
    public PacienteDTO crear(@Valid @RequestBody PacienteDTO dto) {
        return service.crear(dto);
    }

    @PutMapping("/{id}")
    public PacienteDTO actualizar(@PathVariable Long id, @Valid @RequestBody PacienteDTO dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
