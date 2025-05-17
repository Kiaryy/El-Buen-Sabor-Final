package supercell.service;

import supercell.Models.ManufacturedArticle;
import supercell.Models.Subsidiary;

import java.util.List;

public interface SubsidiaryService {
    public List<Subsidiary> getAllSubsidiary();
    public Subsidiary updateSubsidiary(Subsidiary subsidiary);
    public Subsidiary addSubsidiary(Subsidiary subsidiary);
    public Subsidiary deleteSubsidiary(Subsidiary subsidiary);
}
