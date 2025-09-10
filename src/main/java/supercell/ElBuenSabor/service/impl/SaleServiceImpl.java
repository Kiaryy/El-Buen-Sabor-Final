package supercell.ElBuenSabor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import supercell.ElBuenSabor.Models.Article;
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
            // .startDate(saleDTO.startDate())
            // .endDate(saleDTO.endDate())
            // .startTime(saleDTO.startTime())
            // .endTime(saleDTO.endTime())
            .saleDescription(saleDTO.saleDescription())
            .saleType(saleDTO.saleType())
            .saleDiscount(saleDTO.saleDiscount())
            .isActive(saleDTO.isActive())
            .build();

        InventoryImage inventoryImage = InventoryImage.builder()
            .imageData(saleDTO.inventoryImage().imageData())
            .build();

        sale.setInventoryImage(inventoryImage);
        
        Double price = 0.0D;

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

            if (detail.getArticle() != null && detail.getArticle().getBuyingPrice() != null) {
                price += detail.getArticle().getBuyingPrice() * detail.getQuantity() * (100-saleDTO.saleDiscount())/100;
            } else if (detail.getManufacturedArticle() != null && detail.getManufacturedArticle().getPrice() != null) {
                price += detail.getManufacturedArticle().getPrice() * detail.getQuantity() * (100-saleDTO.saleDiscount())/100;
            }


            details.add(detail);
        }

        sale.setSaleDetails(details);
        sale.setSalePrice(price);
        return saleRepository.save(sale);
    }

    
    @Override
    public Sale updateSale(Long ID, SaleDTO saleDTO) {
        return saleRepository.findById(ID).map(existingSale -> {
            boolean discountUpdated = false;

            // if (saleDTO.denomination() != null) existingSale.setDenomination(saleDTO.denomination());
            // if (saleDTO.startDate() != null) existingSale.setStartDate(saleDTO.startDate());
            // if (saleDTO.endDate() != null) existingSale.setEndDate(saleDTO.endDate());
            // if (saleDTO.startTime() != null) existingSale.setStartTime(saleDTO.startTime());
            // if (saleDTO.endTime() != null) existingSale.setEndTime(saleDTO.endTime());

            if (saleDTO.saleDiscount() != null) {
                existingSale.setSaleDiscount(saleDTO.saleDiscount());
                discountUpdated = true;
            }

            if (saleDTO.saleDescription() != null) existingSale.setSaleDescription(saleDTO.saleDescription());
            if (saleDTO.saleType() != null) existingSale.setSaleType(saleDTO.saleType());
            if (saleDTO.isActive() || !saleDTO.isActive()) existingSale.setActive(saleDTO.isActive());

            if (saleDTO.inventoryImage() != null) {
                InventoryImage inventoryImage = InventoryImage.builder()
                    .imageData(saleDTO.inventoryImage().imageData())
                    .build();
                existingSale.setInventoryImage(inventoryImage);
            }

            if (saleDTO.saleDetails() != null) {
                updatePrices(existingSale, saleDTO);
            } else if (discountUpdated) {
                recalculatePricesWithExistingDetails(existingSale);
            }

            return saleRepository.save(existingSale);
        }).orElseThrow(() -> new EntityNotFoundException("Sale not found with ID: " + ID));
    }
   
    private void recalculatePricesWithExistingDetails(Sale sale) {
        double price = 0.0;
    
        for (SaleDetail detail : sale.getSaleDetails()) {
            if (detail.getArticle() != null && detail.getArticle().getBuyingPrice() != null) {
                price += detail.getArticle().getBuyingPrice() * detail.getQuantity() * (100-sale.getSaleDiscount())/100;
            } else if (detail.getManufacturedArticle() != null && detail.getManufacturedArticle().getPrice() != null) {
                price += detail.getManufacturedArticle().getPrice() * detail.getQuantity() * (100-sale.getSaleDiscount())/100;
            }
        }
    
        sale.setSalePrice(price);
    }
    
    @Transactional
    public void updateSalePricesUsingArticle(Article updatedArticle) {
        List<Sale> allSales = saleRepository.findAll();
    
        for (Sale sale : allSales) {
            boolean modified = false;
            double totalPrice = 0.0;
    
            for (SaleDetail detail : sale.getSaleDetails()) {
                if (detail.getArticle() != null && detail.getArticle().getIDArticle().equals(updatedArticle.getIDArticle())) {
                    modified = true;
                }
    
                if (detail.getArticle() != null && detail.getArticle().getBuyingPrice() != null) {
                    totalPrice += detail.getArticle().getBuyingPrice() * detail.getQuantity() * (100-sale.getSaleDiscount())/100;
                } else if (detail.getManufacturedArticle() != null && detail.getManufacturedArticle().getPrice() != null) {
                    totalPrice += detail.getManufacturedArticle().getPrice() * detail.getQuantity() * (100-sale.getSaleDiscount())/100;
                }
            }
    
            if (modified) {
                sale.setSalePrice(totalPrice);
                saleRepository.save(sale);
            }
        }
    }
    
    @Transactional
    public void updatePrices(Sale existingSale,SaleDTO saleDTO){
        Double price = 0.0D;

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

            if (detail.getArticle() != null && detail.getArticle().getBuyingPrice() != null) {
                price += detail.getArticle().getBuyingPrice() * detail.getQuantity() * (100-saleDTO.saleDiscount())/100;
            } else if (detail.getManufacturedArticle() != null && detail.getManufacturedArticle().getPrice() != null) {
                price += detail.getManufacturedArticle().getPrice() * detail.getQuantity() * (100-saleDTO.saleDiscount())/100;
            }

            details.add(detail);
        }

        existingSale.getSaleDetails().clear();
        existingSale.getSaleDetails().addAll(details);
        existingSale.setSalePrice(price);
    }
    @Transactional
    public void updateSalePricesUsingManufacturedArticle(ManufacturedArticle updatedMA) {
        List<Sale> allSales = saleRepository.findAll();
    
        for (Sale sale : allSales) {
            boolean modified = false;
            double totalPrice = 0.0;
    
            for (SaleDetail detail : sale.getSaleDetails()) {
                if (detail.getManufacturedArticle() != null &&
                    detail.getManufacturedArticle().getIDManufacturedArticle().equals(updatedMA.getIDManufacturedArticle())) {
                    modified = true;
                }
    
                if (detail.getArticle() != null && detail.getArticle().getBuyingPrice() != null) {
                    totalPrice += detail.getArticle().getBuyingPrice() * detail.getQuantity() * (100-sale.getSaleDiscount())/100;
                } else if (detail.getManufacturedArticle() != null && detail.getManufacturedArticle().getPrice() != null) {
                    totalPrice += detail.getManufacturedArticle().getPrice() * detail.getQuantity() * (100-sale.getSaleDiscount())/100;
                }
            }
    
            if (modified) {
                sale.setSalePrice(totalPrice);
                saleRepository.save(sale);
            }
        }
    }
    

}
