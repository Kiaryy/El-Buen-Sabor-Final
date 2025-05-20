package supercell.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supercell.Models.Subsidiary;
import supercell.repository.SubsidiaryRepository;
import supercell.service.SubsidiaryService;

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
