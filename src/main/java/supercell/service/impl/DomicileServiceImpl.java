package supercell.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supercell.Models.Domicile;
import supercell.repository.DomicileRepository;
import supercell.service.DomicileService;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DomicileServiceImpl implements DomicileService {

    private final DomicileRepository domicileRepository;

    @Override
    public List<Domicile> getAllDomiciles() {
        return List.of();
    }

    @Override
    public Domicile updateDomicile(Domicile domicile) {
        return null;
    }

    @Override
    public Domicile addDomicile(Domicile domicile) {
        return null;
    }

    @Override
    public Domicile deleteDomicile(Domicile domicile) {
        return null;
    }
}
