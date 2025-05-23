package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import supercell.ElBuenSabor.Models.Location;
import supercell.ElBuenSabor.repository.LocationRepository;
import supercell.ElBuenSabor.service.LocationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceIImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public List<Location> getAllLocation() {
        return List.of();
    }

    @Override
    public Location updateLocation(Location location) {
        return null;
    }

    @Override
    public Location addLocation(Location location) {
        return null;
    }

    @Override
    public Location deleteLocation(Location location) {
        return null;
    }
}
