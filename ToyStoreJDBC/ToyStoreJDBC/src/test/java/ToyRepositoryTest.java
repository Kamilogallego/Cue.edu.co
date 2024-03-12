

import model.category;
import model.toy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import toyRepository.RepositoryToy;
import toyRepository.ToyRepositoryJDBClmpl;


import java.util.List;

public class ToyRepositoryTest {

    @Test
    public void testList() {
        RepositoryToy<toy> toyRepository = new ToyRepositoryJDBClmpl();
        List<toy> toys = toyRepository.list();
        Assertions.assertEquals(10, toys.size());
    }

    @Test
    public void testById() {
        RepositoryToy<toy> toyRepository = new ToyRepositoryJDBClmpl();
        toy toy = toyRepository.byId(1);
        Assertions.assertEquals("Barbie Doll", toy.getName());
    }

    @Test
    public void testTotalStock() {
        RepositoryToy<toy> toyRepository = new ToyRepositoryJDBClmpl();
        int totalStock = toyRepository.getTotalStock();
        Assertions.assertEquals(100, totalStock);
    }

    @Test
    public void testTotalValue() {
        RepositoryToy<toy> toyRepository = new ToyRepositoryJDBClmpl();
        int totalValue = toyRepository.getTotalValue();
        Assertions.assertEquals(5000, totalValue);
    }

    @Test
    public void testTypeWithMostToys() {
        RepositoryToy<toy> toyRepository = new ToyRepositoryJDBClmpl();
        String typeWithMostToys = toyRepository.TypeWithMostToys();
        Assertions.assertEquals("Stuffed Animal", typeWithMostToys);
    }

    @Test
    public void testTypeWithLeastToys() {
        RepositoryToy<toy> toyRepository = new ToyRepositoryJDBClmpl();
        String typeWithLeastToys = toyRepository.TypeWithLeastToys();
        Assertions.assertEquals("Action Figures", typeWithLeastToys);
    }

    @Test
    public void testToysWithAnValue() {
        RepositoryToy<toy> toyRepository = new ToyRepositoryJDBClmpl();
        List<toy> toys = toyRepository.ToysWithAnValue(200);
        Assertions.assertEquals(2, toys.size());
    }

    @Test
    public void testOrderByStockQuantity() {
        RepositoryToy<toy> toyRepository = new ToyRepositoryJDBClmpl();
        List<toy> toys = toyRepository.orderByStockQuantity();
        Assertions.assertEquals("Stuffed Animal", toys.get(0).getName());
    }

    @Test
    public void testUpdateStock() {
        RepositoryToy<toy> toyRepository = new ToyRepositoryJDBClmpl();
        toyRepository.updateStock(1, 10);
        toy toy = toyRepository.byId(1);
        Assertions.assertEquals(110, toy.getStock());
    }

    @Test
    public void testSave() {
        RepositoryToy<toy> toyRepository = new ToyRepositoryJDBClmpl();
        toy toy = new toy(100, new category(1, "Test Category"), 10);
        toyRepository.save(toy);
        toy savedToy = toyRepository.byId(6);
        Assertions.assertEquals("Test Toy", savedToy.getName());
    }

    @Test
    public void testDelete() {
        RepositoryToy<toy> toyRepository = new ToyRepositoryJDBClmpl();
        toyRepository.delete(1);
        toy toy = toyRepository.byId(1);
        Assertions.assertNull(toy);
    }

    @Test
    public void testShowByTYpe() {
        RepositoryToy<toy> toyRepository = new ToyRepositoryJDBClmpl();
        List<toy> toys = toyRepository.showByTYpe();
        Assertions.assertEquals(3, toys.size());
    }

    @Test
    public void testUpdate() {
        RepositoryToy<toy> toyRepository = new ToyRepositoryJDBClmpl();
        toy toy = toyRepository.byId(1);
        toy.setName("Updated Name");
        toy.setPrice(200);
        toy.setCategory(new category(2, "Updated Category"));
        toy.setStock(10);
        toyRepository.update(toy);
        toy updatedToy = toyRepository.byId(1);
        Assertions.assertEquals("Updated Name", updatedToy.getName());
    }

}


