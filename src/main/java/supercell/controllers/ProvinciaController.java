package supercell.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercell.payload.ProvinciaDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/provincias")
public class ProvinciaController {
    // Agregar service
    private final ProvinciaService provinciaService;

    @PostMapping
    public ResponseEntity<ProvinciaDTO> crearProvincia(@RequestBody ProvinciaDTO provinciaDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(provinciaService.crearProvincia(provinciaDTO));
    }

    @GetMapping
    public ResponseEntity<Page<ProvinciaDTO>> listarProvincias()
    {
        return ResponseEntity.ok(provinciaService.listarProvincias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvinciaDTO> obtenerProvincia(@PathVariable Long id)
    {
        return ResponseEntity.ok(provinciaService.obtenerProvinciaConLocalidades(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProvinciaDTO> actualizarProvincia(
            @PathVariable Long id,
           @RequestBody ProvinciaDTO provinciaDTO) {
        return ResponseEntity.ok(provinciaService.actualizarProvincia(id, provinciaDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProvincia(
            @PathVariable Long id)
    {
        provinciaService.eliminarProvincia(id, force);
        return ResponseEntity.noContent().build();
    }
}
