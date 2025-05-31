package supercell.ElBuenSabor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import supercell.ElBuenSabor.Models.MeasuringUnit;
import supercell.ElBuenSabor.Models.payload.MeasuringUnitDTO;
import supercell.ElBuenSabor.repository.MeasuringUnitRepository;
import supercell.ElBuenSabor.service.MeasuringUnitService;

@Service
@RequiredArgsConstructor
public class MeasuringUnitServiceImpl implements MeasuringUnitService{
    @Autowired
    private final MeasuringUnitRepository measuringUnitRepository;

    @Override
    public List<MeasuringUnit> getAllMeasuringUnits(){
        return measuringUnitRepository.findAll();
    }

    @Override 
    public MeasuringUnit addMeasuringUnit(MeasuringUnitDTO measuringUnitDTO){
        MeasuringUnit measuringUnit = MeasuringUnit.builder()
            .unit(measuringUnitDTO.unit())
            .build();
        return measuringUnitRepository.save(measuringUnit);
    }

    @Override
    public MeasuringUnit updateMeasuringUnit(Long ID, MeasuringUnitDTO measuringUnitDTO){
        return measuringUnitRepository.findById(ID).map(existingMeasuringUnit ->{
            if (measuringUnitDTO.unit() != null) {
                existingMeasuringUnit.setUnit(measuringUnitDTO.unit());
            }
            return measuringUnitRepository.save(existingMeasuringUnit);
        }).orElseThrow(() -> new EntityNotFoundException("No se encontro una unidad de medida con el ID: " + ID));
    }
}
