package toyRepository;

import model.toy;

import java.util.List;

public interface RepositoryToy<T>{
    List<T> list();
    T byId(int id);
    int getTotalStock();
    int getTotalValue();
    String TypeWithMostToys();
    String TypeWithLeastToys();
    List<toy> ToysWithAnValue(int value);
     List<toy> orderByStockQuantity();

    void updateStock(int toyId, int quantityChange);

    void save(toy toy);

    void delete(int id);

    List<T>showByTYpe();


    void update(toy toy);

}
