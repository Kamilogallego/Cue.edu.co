package services;

import config.DatabaseConnection;
import mapping.dtos.*;
import mapping.mappers.*;
import model.*;
import repository.Repository;
import repository.customerimpl.CustomerRepositoryJDBCimpl;
import repository.employeimpl.EmployeRepositoryJDBCimpl;
import repository.sale_detailsimpl.SaleDetailsRepositoryJDBCimpl;
import repository.saleimpl.SaleRepositoryJDBCimpl;
import toyRepository.RepositoryToy;
import toyRepository.ToyRepositoryJDBClmpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class ToyStorelmpl implements ToyStorelnt {
    Connection conn = DatabaseConnection.getInstance();
    private RepositoryToy<toy> toyRepository;
    private Repository<employees> employeesRepository;
    private Repository<cliente> clientsRepository;
    private static Repository<sale> saleRepository;
    private static Repository<sale_details> saleDetailsRepository;

    public ToyStorelmpl() throws SQLException {
        this.toyRepository = new ToyRepositoryJDBClmpl();
        this.employeesRepository = new EmployeRepositoryJDBCimpl();
        this.clientsRepository = new CustomerRepositoryJDBCimpl();
        saleDetailsRepository = new SaleDetailsRepositoryJDBCimpl();
        saleRepository = new SaleRepositoryJDBCimpl();
    }


    @Override
    public void addToy(ToyDTO toyDTO) {
        toyRepository.save(ToyMapper.mapFromDTO(toyDTO));
    }

    @Override
    public void addEmployees(EmployeesDTO employeesDTO) {
        employeesRepository.save(EmployeesMapper.mapFromDTO(employeesDTO));
    }

    @Override
    public void addCliente(ClienteDTO clientsDTO) {
        clientsRepository.save(ClienteMapper.mapFromDTO(clientsDTO));

    }

    @Override
    public void addSale(SaleDTO saleDTO) {
        saleRepository.save(SaleMapper.mapFromDTO(saleDTO));
    }

    @Override
    public void addSaleDetail(SaleDetailsDTO saleDetailsDTO) {
        saleDetailsRepository.save(SaleDetailsMapper.mapFromDTO(saleDetailsDTO));
    }

    @Override
    public List<ClienteDTO> listCustomers() {
        return clientsRepository.list()
                .stream()
                .map(ClienteMapper::mapFromModel)
                .toList();
    }

    @Override
    public List<SaleDTO> listSales() {
        return saleRepository.list()
                .stream()
                .map(SaleMapper::mapFromModel)
                .toList();
    }

    @Override
    public List<EmployeesDTO> listEmployees() {
        return employeesRepository.list()
                .stream()
                .map(EmployeesMapper::mapFromModel)
                .toList();
    }

    @Override
    public List<SaleDetailsDTO> listSaleDetails() {
        return saleDetailsRepository.list()
                .stream()
                .map(SaleDetailsMapper::mapFromModel)
                .toList();
    }

    @Override
    public List<ToyDTO> listToys() {
        return toyRepository.list()
                .stream()
                .map(ToyMapper::mapFromModel)
                .toList();


    }

    @Override
    public List<ToyDTO> showByTypes() {
        return toyRepository.showByTYpe()
                .stream()
                .map(ToyMapper::mapFromModel)
                .toList();
    }


    @Override
    public ToyDTO search(Integer id) throws SQLException {
        return ToyMapper.mapFromModel(toyRepository.byId(id));
    }

    @Override
    public int getTotalStock() {
        return toyRepository.getTotalStock();
    }

    @Override
    public double getTotalValue() {
        return toyRepository.getTotalValue();
    }

    @Override
    public String getTypeWithMostToys() {
        return toyRepository.TypeWithMostToys();
    }

    @Override
    public String getTypeWithLeastToys() {
        return toyRepository.TypeWithLeastToys();
    }


    @Override
    public List<ToyDTO> getToysWithValueGreaterThan(int value) {
        List<toy> toys = toyRepository.ToysWithAnValue(value);
        return toys.stream()
                .map(ToyMapper::mapFromModel)
                .toList();
    }

    @Override
    public List<ToyDTO> orderByStockQuantity() {
        List<toy> toys = toyRepository.orderByStockQuantity();
        return toys.stream()
                .map(ToyMapper::mapFromModel)
                .toList();
    }

    @Override
    public void updateStock(int toyId, int quantityChange) {
        toyRepository.updateStock(toyId, quantityChange);
    }


}






