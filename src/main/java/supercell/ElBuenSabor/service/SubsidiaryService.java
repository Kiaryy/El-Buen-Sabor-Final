package supercell.ElBuenSabor.service;

import java.util.List;

import supercell.ElBuenSabor.Models.ManufacturedArticle;
import supercell.ElBuenSabor.Models.Subsidiary;

public interface SubsidiaryService {
    public List<Subsidiary> getAllSubsidiary();
    public Subsidiary updateSubsidiary(Subsidiary subsidiary);
    public Subsidiary addSubsidiary(Subsidiary subsidiary);
    public Subsidiary deleteSubsidiary(Subsidiary subsidiary);
}
