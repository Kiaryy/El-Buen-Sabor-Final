package supercell.ElBuenSabor.service;

import java.util.List;

import supercell.ElBuenSabor.Models.Sale;
import supercell.ElBuenSabor.Models.payload.SaleDTO;

public interface SaleService {
    public List<Sale> getAllSales();
    public Sale updateSale(Long ID, SaleDTO saleDTO);
    public Sale addSale(SaleDTO saleDTO);

}
