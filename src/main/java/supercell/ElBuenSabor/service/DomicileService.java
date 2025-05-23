package supercell.ElBuenSabor.service;

import java.util.List;

import supercell.ElBuenSabor.Models.Company;
import supercell.ElBuenSabor.Models.Domicile;

public interface DomicileService {

    public List<Domicile> getAllDomiciles();
    public Domicile updateDomicile(Domicile domicile);
    public Domicile addDomicile(Domicile domicile);
    public Domicile deleteDomicile(Domicile domicile);
}
