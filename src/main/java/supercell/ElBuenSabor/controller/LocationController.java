package supercell.ElBuenSabor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import supercell.ElBuenSabor.Models.Location;
import supercell.ElBuenSabor.repository.LocationRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;
    
    @GetMapping("/getAll")
    public List<Location> getAllLocations(){
        return locationRepository.findAll();
    }
}
