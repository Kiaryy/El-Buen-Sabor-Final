package supercell.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import supercell.payload.SucursalDTO;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sucursalEmpresa/sucursales")
public class SucursalEmpresaController {

    // Agregar servicio
    private final SucursalEmpresaService sucursalService;

    @PostMapping
    public ResponseEntity<SucursalDTO> crearSucursal(
           @RequestBody SucursalDTO sucursalDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sucursalService.crearSucursal(sucursalDTO));
    }

    @GetMapping
    public ResponseEntity<Page<SucursalDTO>> listarSucursales() {
        return ResponseEntity.ok(sucursalService.listarSucursales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalDTO> obtenerSucursal(
            @PathVariable Long id) {
        return ResponseEntity.ok(sucursalService.obtenerSucursalConDetalle(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> actualizarSucursal(
            @PathVariable Long id,
            @Valid @RequestBody SucursalDTO sucursalDTO) {
        return ResponseEntity.ok(sucursalService.actualizarSucursal(id, sucursalDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }
}
