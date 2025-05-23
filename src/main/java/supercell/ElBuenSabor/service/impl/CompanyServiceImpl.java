package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import supercell.ElBuenSabor.Models.Company;
import supercell.ElBuenSabor.repository.CompanyRepository;
import supercell.ElBuenSabor.service.CompanyService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public List<Company> getAllCategories() {
        return List.of();
    }

    @Override
    public Company updateCategory(Company company) {
        return null;
    }

    @Override
    public Company addCategory(Company company) {
        return null;
    }

    @Override
    public Company deleteCategory(Company company) {
        return null;
    }
}
