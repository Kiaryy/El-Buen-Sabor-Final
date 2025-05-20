package supercell.service;

import supercell.Models.Company;
import supercell.Models.Domicile;

import java.util.List;

public interface DomicileService {

    public List<Domicile> getAllDomiciles();
    public Domicile updateDomicile(Domicile domicile);
    public Domicile addDomicile(Domicile domicile);
    public Domicile deleteDomicile(Domicile domicile);
}
