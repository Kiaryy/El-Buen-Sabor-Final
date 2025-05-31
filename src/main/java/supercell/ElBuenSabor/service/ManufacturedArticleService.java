package supercell.ElBuenSabor.service;

import java.util.List;

import supercell.ElBuenSabor.Models.ManufacturedArticle;
import supercell.ElBuenSabor.Models.payload.ManufacturedArticleDTO;

public interface ManufacturedArticleService {
    public List<ManufacturedArticle> getAllManufacturedArticle();
    public ManufacturedArticle updateManufacturedArticle(Long ID, ManufacturedArticleDTO manufacturedArticleDTO);
    public ManufacturedArticle addManufacturedArticle(ManufacturedArticleDTO manufacturedArticleDTO);
}
