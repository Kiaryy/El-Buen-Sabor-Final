package supercell.ElBuenSabor.service;

import java.util.List;

import supercell.ElBuenSabor.Models.Location;

public interface LocationService {
    public List<Location> getAllLocation();
    public Location updateLocation(Location location);
    public Location addLocation(Location location);
    public Location deleteLocation(Location location);
}
