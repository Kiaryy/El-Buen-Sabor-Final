package supercell.ElBuenSabor.service;
import java.util.List;

import supercell.ElBuenSabor.Models.Provider;
import supercell.ElBuenSabor.Models.payload.ProviderDTO;

public interface ProviderService {
    public List<Provider> getAllProviders();
    public Provider updateProvider(Long ID, ProviderDTO providerDTO);
    public Provider addProvider(ProviderDTO providerDTO);
}
