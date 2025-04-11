package supercell.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercell.Models.Pais;
import supercell.payload.PaisDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pais")
public class PaisController {
    // Agregar servicio
    private final PaisService paisService;

    @PostMapping
    public ResponseEntity<PaisDTO> crearPais(@RequestBody Pais paisDTO) {
        return new ResponseEntity<>(paisService.crearPais(paisDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PaisDTO>> listarPaises() {
        return ResponseEntity.ok(paisService.listarPaises(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaisDTO> obtenerPaisPorId(@PathVariable Long id) {
        return ResponseEntity.ok(paisService.obtenerPaisPorId(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PaisDTO> actualizarPais(
            @PathVariable Long id,
            @RequestBody PaisDTO paisDTO) {
        return ResponseEntity.ok(paisService.actualizarPais(id, paisDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPais(@PathVariable Long id) {
        paisService.eliminarPais(id);
        return ResponseEntity.noContent().build();
    }
}