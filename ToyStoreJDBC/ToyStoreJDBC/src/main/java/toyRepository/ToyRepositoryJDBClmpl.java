package toyRepository;

import config.DatabaseConnection;
import model.category;
import model.toy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class ToyRepositoryJDBClmpl implements RepositoryToy<toy> {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }
    private toy createToy(ResultSet resultSet) throws SQLException {
        toy t = new toy();
        t.setId(resultSet.getInt("id"));
        t.setName(resultSet.getString("name"));
        t.setPrice(resultSet.getInt("price"));

        t.setCategory(new category(
                resultSet.getInt("id_category"),
                resultSet.getString("category_name")

        ));
        t.setStock(resultSet.getInt("stock"));
        return t;
    }



    @Override
    public List<toy> list() {
        List<toy>toysList=new ArrayList<>();
        try(Statement statement=getConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(
                    """
                        SELECT t.*, c.type as category_type, c.id as category_id
                        FROM toy AS t 
                        INNER JOIN category AS c ON t.id_category = c.id;
                        """
            ))
        {
            while (resultSet.next()){
                toy t = createToy(resultSet);
                toysList.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toysList;
    }

    @Override
    public toy byId(int id) {
        toy t =null;
        try (PreparedStatement preparedStatement=getConnection()
                .prepareStatement(""" 
                                    SELECT t.*, c.type as category_type, c.id as category_id
                                    FROM toy AS t 
                                    INNER JOIN category AS c ON t.id_category = c.id
                                    WHERE t.id=?
                                    """)
        ) {
            preparedStatement.setLong(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if (resultSet.next()){
                t =createToy(resultSet);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    @Override
    public int getTotalStock() {
        int totalStock = 0;
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT SUM(stock) AS total_stock FROM toy")) {
            if (resultSet.next()) {
                totalStock = resultSet.getInt("total_stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalStock;
    }

    @Override
    public int getTotalValue() {
        int totalValue = 0;
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT SUM(stock * price) AS total_value FROM toy")) {
            if (resultSet.next()) {
                totalValue = resultSet.getInt("total_value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalValue;
    }

    @Override
    public String TypeWithMostToys() {
        String typeWithMostToys = null;
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT c.type, COUNT(*) AS total_toys " +
                             "FROM toy AS t " +
                             "JOIN category AS c ON t.id_category = c.id " +
                             "GROUP BY c.type " +
                             "ORDER BY total_toys DESC " +
                             "LIMIT 1")) {
            if (resultSet.next()) {
                typeWithMostToys = resultSet.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeWithMostToys;
    }

    @Override
    public String TypeWithLeastToys() {
        String typeWithLeastToys = null;
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT c.type, COUNT(*) AS total_toys " +
                             "FROM toy AS t " +
                             "JOIN category AS c ON t.id_category = c.id " +
                             "GROUP BY c.type " +
                             "ORDER BY total_toys ASC " +
                             "LIMIT 1")) {
            if (resultSet.next()) {
                typeWithLeastToys = resultSet.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeWithLeastToys;
    }

    @Override
    public List<toy> ToysWithAnValue(int value) {

        List<toy> toysWithValueGreaterThan = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "SELECT t.*, c.type AS category_type, c.id AS category_id\n" +
                        "FROM toy AS t\n" +
                        "JOIN category AS c ON t.id_category = c.id\n" +
                        "WHERE t.price > ?")) {
            preparedStatement.setInt(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                toysWithValueGreaterThan.add(createToy(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toysWithValueGreaterThan;
    }

    @Override
    public List<toy> orderByStockQuantity() {

        List<toy> toysOrderedByStockQuantity = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT t.*, c.type AS category_name, COUNT(*) AS total_stock " +
                             "FROM toy AS t " +
                             "JOIN category AS c ON t.id_category = c.id " +
                             "GROUP BY c.type " +
                             "ORDER BY total_stock ASC")) {
            while (resultSet.next()) {
                toysOrderedByStockQuantity.add(createToy(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toysOrderedByStockQuantity;
    }

    @Override
    public void updateStock(int toyId, int quantityChange) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("UPDATE toy SET stock = stock + ? WHERE id = ?")) {
            preparedStatement.setInt(1, quantityChange);
            preparedStatement.setInt(2, toyId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void save(toy toy) {
        try (PreparedStatement pst = getConnection()
                .prepareStatement("""
                                          INSERT INTO toy(name,price,id_category,stock) values (?,?,?,?)
                                          """)


                ){
            pst.setString(1, toy.getName());
            pst.setDouble(2,toy.getPrice());
            pst.setInt(3,toy.getCategory().getId());
            pst.setInt(4,toy.getStock());
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {
        try(PreparedStatement preparedStatement = getConnection()
                .prepareStatement("""
                                      DELETE FROM toy where id=?
                                      """)
        ){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<toy> showByTYpe() {
        List<toy> typesList = new ArrayList<>();
        try(Statement statement=getConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(
                    """
                        
                            SELECT id_category, COUNT(*) FROM toy
                        JOIN category on toy.id_category = category.id
                        GROUP BY category.type
                        """
            ))
        {
            while (resultSet.next()){
                toy t = createToy(resultSet);
                typesList.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typesList;
    }


    @Override
    public void update(toy toy) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("UPDATE toy SET name = ?, price = ?, id_category = ?, stock = ? WHERE id = ?")) {
            preparedStatement.setString(1, toy.getName());
            preparedStatement.setDouble(2, toy.getPrice());
            preparedStatement.setInt(3, toy.getCategory().getId());
            preparedStatement.setInt(4, toy.getStock());
            preparedStatement.setInt(5, toy.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar juguete", e);
        }
    }
}
