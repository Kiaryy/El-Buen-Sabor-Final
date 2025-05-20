package supercell.service;

import supercell.Models.Company;

import java.util.List;

public interface CompanyService {

    public List<Company> getAllCategories();
    public Company updateCategory(Company company);
    public Company addCategory(Company company);
    public Company deleteCategory(Company company);
}
