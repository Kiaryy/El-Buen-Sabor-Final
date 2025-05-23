package supercell.ElBuenSabor.service;

import java.util.List;

import supercell.ElBuenSabor.Models.MeasuringUnit;
import supercell.ElBuenSabor.Models.payload.MeasuringUnitDTO;

public interface MeasuringUnitService {
    public List<MeasuringUnit> getAllMeasuringUnits();
    public MeasuringUnit updateMeasuringUnit(Long ID, MeasuringUnitDTO measuringUnitDTO);
    public MeasuringUnit addMeasuringUnit(MeasuringUnitDTO measuringUnitDTO);
    public boolean deleteMeasuringUnit(Long ID);
}
