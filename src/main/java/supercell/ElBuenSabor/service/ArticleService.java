package supercell.ElBuenSabor.service;

import java.util.List;

import supercell.ElBuenSabor.Models.Article;
import supercell.ElBuenSabor.Models.payload.ArticleDTO;

public interface ArticleService {

    public List<Article> getAllArticles();
    public Article updateArticle(Long ID, ArticleDTO articleDTO);
    public Article addArticle(ArticleDTO articleDTO);
}
