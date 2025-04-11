package supercell.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercell.Models.Localidad;

import java.util.List;

@RestController
@RequestMapping("/localidades")
@RequiredArgsConstructor
public class LocalidadController {
    // Agregar servicio
    @Autowired
    private final LocalidadService localidadService;

    @PostMapping
    public ResponseEntity<Localidad> crearLocalidad(@RequestBody Localidad localidad) {
        Localidad nuevaLocalidad = localidadService.crearLocalidad(localidad);
        return ResponseEntity.ok(nuevaLocalidad);
    }

    @GetMapping
    public ResponseEntity<List<Localidad>> obtenerTodasLocalidades() {
        return ResponseEntity.ok(localidadService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Localidad> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(localidadService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Localidad> actualizarLocalidad( @PathVariable Long id, @RequestBody Localidad localidad)
    {
        return ResponseEntity.ok(localidadService.actualizarLocalidad(id, localidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarLocalidad(@PathVariable Long id) {
        String respose = localidadService.eliminarLocalidad(id);

        return new ResponseEntity<>(respose, HttpStatus.ACCEPTED);
    }

    }
