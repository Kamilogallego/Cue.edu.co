package services;

import mapping.dtos.*;

import java.sql.SQLException;
import java.util.List;

public interface ToyStorelnt {
    void addToy(ToyDTO toyDTO);
    void addEmployees(EmployeesDTO employeesDTO);
    void addCliente(ClienteDTO clientsDTO);
    void addSale(SaleDTO saleDTO);
    void addSaleDetail(SaleDetailsDTO saleDetailsDTO);
    List<ClienteDTO> listCustomers();
    List<SaleDTO> listSales();
    List<EmployeesDTO> listEmployees();
    List<SaleDetailsDTO> listSaleDetails();

    List<ToyDTO> listToys();
    List<ToyDTO> showByTypes();
    ToyDTO search(Integer id) throws SQLException;

    int getTotalStock();

    double getTotalValue();

    String getTypeWithMostToys();

    String getTypeWithLeastToys();



    List<ToyDTO> getToysWithValueGreaterThan(int value);

    List<ToyDTO> orderByStockQuantity();

    void updateStock(int toyId, int quantityChange);


}
