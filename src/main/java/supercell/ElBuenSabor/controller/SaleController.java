package supercell.ElBuenSabor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.net.HttpStatus;

import supercell.ElBuenSabor.Models.Sale;
import supercell.ElBuenSabor.Models.payload.SaleDTO;
import supercell.ElBuenSabor.service.impl.SaleServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/sale")
public class SaleController {
    @Autowired
    private SaleServiceImpl saleServiceImpl;
   
    @GetMapping("/getAll")
    public List<Sale> getAll(){
        return saleServiceImpl.getAllSales();
    }

    @PostMapping("/add")
    public ResponseEntity<Sale> addSale(@RequestBody SaleDTO saleDTO){
        return ResponseEntity.status(HttpStatus.OK).body(saleServiceImpl.addSale(saleDTO));
    }

    @PatchMapping("/update/{ID}")
    public ResponseEntity<Sale> updateSale(@PathVariable Long ID, @RequestBody SaleDTO saleDTO){
        return ResponseEntity.status(HttpStatus.OK).body(saleServiceImpl.updateSale(ID, saleDTO));
    }
}
