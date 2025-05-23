package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import supercell.ElBuenSabor.Models.Subsidiary;
import supercell.ElBuenSabor.repository.SubsidiaryRepository;
import supercell.ElBuenSabor.service.SubsidiaryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubsidiaryServiceImpl implements SubsidiaryService {

    private final SubsidiaryRepository subsidiaryRepository;

    @Override
    public List<Subsidiary> getAllSubsidiary() {
        return List.of();
    }

    @Override
    public Subsidiary updateSubsidiary(Subsidiary subsidiary) {
        return null;
    }

    @Override
    public Subsidiary addSubsidiary(Subsidiary subsidiary) {
        return null;
    }

    @Override
    public Subsidiary deleteSubsidiary(Subsidiary subsidiary) {
        return null;
    }
}
