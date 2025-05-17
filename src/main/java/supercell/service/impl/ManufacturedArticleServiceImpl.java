package supercell.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supercell.Models.ManufacturedArticle;
import supercell.repository.ManufacturedArticleRepository;
import supercell.service.ManufacturedArticleService;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ManufacturedArticleServiceImpl implements ManufacturedArticleService {

    private final ManufacturedArticleRepository manufacturedArticleRepository;

    @Override
    public List<ManufacturedArticle> getAllManufacturedArticle() {
        return List.of();
    }

    @Override
    public ManufacturedArticle updateManufacturedArticle(ManufacturedArticle manufacturedArt) {
        return null;
    }

    @Override
    public ManufacturedArticle addManufacturedArticle(ManufacturedArticle manufacturedArt) {
        return null;
    }

    @Override
    public ManufacturedArticle deleteManufacturedArticle(ManufacturedArticle manufacturedArt) {
        return null;
    }
}
