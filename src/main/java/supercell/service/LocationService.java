package supercell.service;

import supercell.Models.Location;

import java.util.List;

public interface LocationService {
    public List<Location> getAllLocation();
    public Location updateLocation(Location location);
    public Location addLocation(Location location);
    public Location deleteLocation(Location location);
}
