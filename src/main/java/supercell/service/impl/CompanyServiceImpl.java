package supercell.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supercell.Models.Company;
import supercell.repository.CompanyRepository;
import supercell.service.CompanyService;

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
