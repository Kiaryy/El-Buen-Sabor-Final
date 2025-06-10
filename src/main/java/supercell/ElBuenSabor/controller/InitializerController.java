package supercell.ElBuenSabor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import supercell.ElBuenSabor.service.InitializerService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/initialize")
public class InitializerController {
    @Autowired
    private InitializerService initializerService;

    @GetMapping("/all")
    public String initializeAll(){
        initializerService.initializeCategory();
        initializerService.initializeMeasuringUnit();
        initializerService.initializeArticle();        
        initializerService.initializeProvider();
        initializerService.initializeManufacturedArticle();
        initializerService.initializeClient();
        return "Tables Initialized";
    }

}
