package supercell.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercell.Models.Domicilio;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sucursalEmpresa")
public class DomicilioController {

     // Agregar servicio.
    @Autowired
    private DomicilioService domicilioService;

    @PostMapping
    public ResponseEntity<Domicilio> crearDomicilio(@RequestBody Domicilio domicilio) {
        Domicilio nuevoDomicilio = domicilioService.guardarDomicilio(domicilio);
        return ResponseEntity.ok(nuevoDomicilio);
    }


    @GetMapping
    public ResponseEntity<List<Domicilio>> listarDomicilios() {
        List<Domicilio> domicilios = domicilioService.listarTodos();
        return ResponseEntity.ok(domicilios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Domicilio> buscarPorId(@PathVariable Long id) {
        Domicilio domicilio = domicilioService.buscarPorId(id);
        return ResponseEntity.ok(domicilio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Domicilio> actualizarDomicilio(
            @PathVariable Long id,
            @RequestBody Domicilio domicilioActualizado) {
        Domicilio domicilio = domicilioService.actualizarDomicilio(id, domicilioActualizado);
        return ResponseEntity.ok(domicilio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDomicilio(@PathVariable Long id) {
        domicilioService.eliminarDomicilio(id);
        return ResponseEntity.noContent().build();
    }
}
