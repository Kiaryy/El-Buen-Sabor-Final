package supercell.service;

import supercell.Models.Article;

import java.util.List;

public interface ArticleService {

    public List<Article> getAllArticles();
    public Article updateArticle(Article article);
    public Article addArticle(Article article);
    public Article deleteArticle(Article article);
}
