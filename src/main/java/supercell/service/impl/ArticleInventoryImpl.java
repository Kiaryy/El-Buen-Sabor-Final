package supercell.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supercell.Models.ArticleInventory;
import supercell.repository.ArticleInventoryRepository;
import supercell.service.ArticleInventoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleInventoryImpl implements ArticleInventoryService {

    private final ArticleInventoryRepository repository;

    @Override
    public List<ArticleInventory> getAllArticleInventory() {
        return List.of();
    }

    @Override
    public ArticleInventory updateArticleInventory(ArticleInventory article) {
        return null;
    }

    @Override
    public ArticleInventory addArticleInventory(ArticleInventory article) {
        return null;
    }

    @Override
    public ArticleInventory deleteArticleInventory(ArticleInventory article) {
        return null;
    }
}
