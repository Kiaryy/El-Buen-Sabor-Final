package supercell.service;

import supercell.Models.ManufacturedArticle;

import java.util.List;

public interface ManufacturedArticleService {
    public List<ManufacturedArticle> getAllManufacturedArticle();
    public ManufacturedArticle updateManufacturedArticle(ManufacturedArticle manufacturedArt);
    public ManufacturedArticle addManufacturedArticle(ManufacturedArticle manufacturedArt);
    public ManufacturedArticle deleteManufacturedArticle(ManufacturedArticle manufacturedArt);
}
