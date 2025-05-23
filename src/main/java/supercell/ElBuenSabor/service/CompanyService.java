package supercell.ElBuenSabor.service;

import java.util.List;

import supercell.ElBuenSabor.Models.Company;

public interface CompanyService {

    public List<Company> getAllCategories();
    public Company updateCategory(Company company);
    public Company addCategory(Company company);
    public Company deleteCategory(Company company);
}
