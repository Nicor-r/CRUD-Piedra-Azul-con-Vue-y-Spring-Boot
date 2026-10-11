package co.piedraazul.crud.controller;

import co.piedraazul.crud.dto.CitaDTO;
import co.piedraazul.crud.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CitaDTO> listar() {
        return service.listar();
    }

    // PASO 3: @PostMapping recibe el JSON convertido en DTO
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)   // PASO 6: responde 201 + JSON
    public CitaDTO crear(@Valid @RequestBody CitaDTO dto) {
        return service.crear(dto);
    }

    @PutMapping("/{id}")
    public CitaDTO actualizar(@PathVariable Long id, @Valid @RequestBody CitaDTO dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
