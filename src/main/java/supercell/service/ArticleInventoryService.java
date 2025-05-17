package supercell.service;

import supercell.Models.Article;
import supercell.Models.ArticleInventory;

import java.util.List;

public interface ArticleInventoryService {

    public List<ArticleInventory> getAllArticleInventory();
    public ArticleInventory updateArticleInventory(ArticleInventory article);
    public ArticleInventory addArticleInventory(ArticleInventory article);
    public ArticleInventory deleteArticleInventory(ArticleInventory article);

}
