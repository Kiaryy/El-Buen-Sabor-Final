package supercell.ElBuenSabor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import supercell.ElBuenSabor.Models.InventoryImage;
import supercell.ElBuenSabor.Models.ManufacturedArticle;
import supercell.ElBuenSabor.Models.Sale;
import supercell.ElBuenSabor.Models.SaleDetail;
import supercell.ElBuenSabor.Models.payload.SaleDTO;
import supercell.ElBuenSabor.Models.payload.SaleDetailDTO;
import supercell.ElBuenSabor.repository.ArticleRepository;
import supercell.ElBuenSabor.repository.ManufacturedArticleRepository;
import supercell.ElBuenSabor.repository.SaleRepository;
import supercell.ElBuenSabor.service.SaleService;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    @Autowired
    private final SaleRepository saleRepository;
    @Autowired
    private final ManufacturedArticleRepository manufacturedArticleRepository;
    @Autowired
    private final ArticleRepository articleRepository;


    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }
  
    @Override
    public Sale addSale(SaleDTO saleDTO) {
        Sale sale = Sale.builder()
            .denomination(saleDTO.denomination())
            .startDate(saleDTO.startDate())
            .endDate(saleDTO.endDate())
            .startTime(saleDTO.startTime())
            .endTime(saleDTO.endTime())
            .saleDescription(saleDTO.saleDescription())
            .salePrice(saleDTO.salePrice())
            .saleType(saleDTO.saleType())
            .isActive(saleDTO.isActive())
            .build();

        InventoryImage inventoryImage = InventoryImage.builder()
            .imageData(saleDTO.inventoryImage().imageData())
            .build();

        sale.setInventoryImage(inventoryImage);

        List<SaleDetail> details = new ArrayList<>();
        for (SaleDetailDTO dto : saleDTO.saleDetails()) {
            SaleDetail detail = new SaleDetail();
            detail.setQuantity(dto.quantity());
            detail.setSale(sale);

            switch (dto.type().toUpperCase()) {
                case "ARTICLE" -> detail.setArticle(articleRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Article not found with ID: " + dto.id())));
                case "MANUFACTURED" -> detail.setManufacturedArticle(manufacturedArticleRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("ManufacturedArticle not found with ID: " + dto.id())));
                default -> throw new IllegalArgumentException("Invalid type: " + dto.type());
            }

            details.add(detail);
        }

        sale.setSaleDetails(details);

        return saleRepository.save(sale);
    }

    
    @Override
    public Sale updateSale(Long ID, SaleDTO saleDTO) {
        return saleRepository.findById(ID).map(existingSale -> {
            if (saleDTO.denomination() != null) existingSale.setDenomination(saleDTO.denomination());
            if (saleDTO.startDate() != null) existingSale.setStartDate(saleDTO.startDate());
            if (saleDTO.endDate() != null) existingSale.setEndDate(saleDTO.endDate());
            if (saleDTO.startTime() != null) existingSale.setStartTime(saleDTO.startTime());
            if (saleDTO.endTime() != null) existingSale.setEndTime(saleDTO.endTime());
            if (saleDTO.saleDescription() != null) existingSale.setSaleDescription(saleDTO.saleDescription());
            if (saleDTO.salePrice() != null) existingSale.setSalePrice(saleDTO.salePrice());
            if (saleDTO.saleType() != null) existingSale.setSaleType(saleDTO.saleType());
            if (saleDTO.isActive() || !saleDTO.isActive()) existingSale.setActive(saleDTO.isActive());;
            if (saleDTO.inventoryImage() != null) {
                InventoryImage inventoryImage = InventoryImage.builder()
                    .imageData(saleDTO.inventoryImage().imageData())
                    .build();
                existingSale.setInventoryImage(inventoryImage);
            }

            if (saleDTO.saleDetails() != null) {
                List<SaleDetail> details = new ArrayList<>();
                for (SaleDetailDTO dto : saleDTO.saleDetails()) {
                    SaleDetail detail = new SaleDetail();
                    detail.setQuantity(dto.quantity());
                    detail.setSale(existingSale);

                    switch (dto.type().toUpperCase()) {
                        case "ARTICLE" -> detail.setArticle(articleRepository.findById(dto.id())
                            .orElseThrow(() -> new EntityNotFoundException("Article not found with ID: " + dto.id())));
                        case "MANUFACTURED" -> detail.setManufacturedArticle(manufacturedArticleRepository.findById(dto.id())
                            .orElseThrow(() -> new EntityNotFoundException("ManufacturedArticle not found with ID: " + dto.id())));
                        default -> throw new IllegalArgumentException("Invalid type: " + dto.type());
                    }

                    details.add(detail);
                }

                existingSale.getSaleDetails().clear();
                existingSale.getSaleDetails().addAll(details);
            }

            return saleRepository.save(existingSale);
        }).orElseThrow(() -> new EntityNotFoundException("Sale not found with ID: " + ID));
    }

}
