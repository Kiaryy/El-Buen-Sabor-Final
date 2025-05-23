package supercell.ElBuenSabor.service;

import java.util.List;

import supercell.ElBuenSabor.Models.ManufacturedArticle;

public interface ManufacturedArticleService {
    public List<ManufacturedArticle> getAllManufacturedArticle();
    public ManufacturedArticle updateManufacturedArticle(ManufacturedArticle manufacturedArt);
    public ManufacturedArticle addManufacturedArticle(ManufacturedArticle manufacturedArt);
    public ManufacturedArticle deleteManufacturedArticle(ManufacturedArticle manufacturedArt);
}
