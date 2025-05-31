package supercell.ElBuenSabor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import supercell.ElBuenSabor.Models.Provider;
import supercell.ElBuenSabor.Models.payload.ProviderDTO;
import supercell.ElBuenSabor.service.impl.ProviderServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    private ProviderServiceImpl providerServiceImpl;

    @GetMapping("/getAll")
    public List<Provider> getAll(){
        return providerServiceImpl.getAllProviders();
    }

    @PostMapping("/add")
    public ResponseEntity<Provider> addProvider(@RequestBody ProviderDTO providerDTO){
        return ResponseEntity.status(HttpStatus.OK).body(providerServiceImpl.addProvider(providerDTO));
    }

    @PatchMapping("/update/{ID}")
    public ResponseEntity<Provider> updateProvider(@PathVariable Long ID, @RequestBody ProviderDTO providerDTO){
        return ResponseEntity.status(HttpStatus.OK).body(providerServiceImpl.updateProvider(ID, providerDTO));
    }
}
