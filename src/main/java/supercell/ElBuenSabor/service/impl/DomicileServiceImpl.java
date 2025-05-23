package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import supercell.ElBuenSabor.Models.Domicile;
import supercell.ElBuenSabor.repository.DomicileRepository;
import supercell.ElBuenSabor.service.DomicileService;

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
