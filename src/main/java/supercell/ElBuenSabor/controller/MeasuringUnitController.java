package supercell.ElBuenSabor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import supercell.ElBuenSabor.Models.MeasuringUnit;
import supercell.ElBuenSabor.Models.payload.MeasuringUnitDTO;
import supercell.ElBuenSabor.service.impl.MeasuringUnitServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/measuringUnit")
public class MeasuringUnitController {
    @Autowired 
    private MeasuringUnitServiceImpl measuringUnitServiceImpl;
    

    @GetMapping("/getAll")
    public List<MeasuringUnit> getAll(){
        return measuringUnitServiceImpl.getAllMeasuringUnits();
    }

    @PostMapping("/add")
    public ResponseEntity<MeasuringUnit> addMeasuringUnit(@RequestBody MeasuringUnitDTO measuringUnitDTO){
        return ResponseEntity.status(HttpStatus.OK).body(measuringUnitServiceImpl.addMeasuringUnit(measuringUnitDTO));
    }

    @PatchMapping("/update/{ID}")
    public ResponseEntity<MeasuringUnit> updateMeasuringUnit(@PathVariable Long ID, @RequestBody MeasuringUnitDTO measuringUnitDTO){
        return ResponseEntity.status(HttpStatus.OK).body(measuringUnitServiceImpl.updateMeasuringUnit(ID, measuringUnitDTO));
    }
}
