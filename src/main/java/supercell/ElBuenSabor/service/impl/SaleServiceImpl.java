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
import supercell.ElBuenSabor.Models.payload.SaleDTO;
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
        .saleType(saleDTO.saleType())
        .build();

        InventoryImage inventoryImage = InventoryImage.builder()
            .imageData(saleDTO.inventoryImage().imageData())
            .build();
        
        sale.setInventoryImage(inventoryImage);

        List<ManufacturedArticle> mads = new ArrayList<>();
        for (Long id : saleDTO.manufacturedArticle()) {
            ManufacturedArticle mad = manufacturedArticleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Articulo Manufacturado no encontrado con ID: " + id));
            
            mads.add(mad);
        }

        sale.setManufacturedArticle(mads);

        return saleRepository.save(sale);
    }
    
    @Override
    public Sale updateSale(Long ID, SaleDTO saleDTO) {
        return saleRepository.findById(ID).map(existingSale ->{
            if (saleDTO.denomination() != null) {
                existingSale.setDenomination(saleDTO.denomination());
            }
            if (saleDTO.startDate() != null) {
                existingSale.setStartDate(saleDTO.startDate());
            }
            if (saleDTO.endDate() != null) {
                existingSale.setEndDate(saleDTO.endDate());
            }
            if (saleDTO.startTime() != null) {
                existingSale.setStartTime(saleDTO.startTime());
            }
            if (saleDTO.endTime() != null) {
                existingSale.setEndTime(saleDTO.endTime());
            }
            if (saleDTO.saleDescription() != null) {
                existingSale.setSaleDescription(saleDTO.saleDescription());
            }
            if (saleDTO.salePrice() != null) {
                existingSale.setSalePrice(saleDTO.salePrice());
            }
            if (saleDTO.saleType() != null) {
                existingSale.setSaleType(saleDTO.saleType());
            }
            if (saleDTO.inventoryImage() != null) {
                InventoryImage inventoryImage = InventoryImage.builder()
                    .imageData(saleDTO.inventoryImage().imageData())
                    .build();

                existingSale.setInventoryImage(inventoryImage);
            }
            if (saleDTO.manufacturedArticle() != null && !saleDTO.manufacturedArticle().isEmpty()) {
                List<ManufacturedArticle> mads = new ArrayList<>();
                for (Long id : saleDTO.manufacturedArticle()) {
                    ManufacturedArticle mad = manufacturedArticleRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Articulo Manufacturado no encontrado con ID: " + id));
                    
                    mads.add(mad);
                }
                existingSale.getManufacturedArticle().clear();
                existingSale.getManufacturedArticle().addAll(mads);
                
            }
        
        return saleRepository.save(existingSale);
        }).orElseThrow(() -> new EntityNotFoundException("No se encontro una promocion con el ID: " + ID));
    }
}
