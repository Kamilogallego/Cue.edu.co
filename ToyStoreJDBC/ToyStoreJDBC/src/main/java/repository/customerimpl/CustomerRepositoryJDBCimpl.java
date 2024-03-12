package repository.customerimpl;

import config.DatabaseConnection;
import model.cliente;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryJDBCimpl implements Repository<cliente> {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }
    @Override
    public List<cliente> list() {
        List<cliente> customers = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM cliente")) {
            while (resultSet.next()) {
                cliente cliente = createCustomer(resultSet);
                customers.add(cliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar clientes", e);
        }
        return customers;
    }

    @Override
    public cliente byID(int id) {
        cliente customer = null;
        try {
            String sql = "SELECT * FROM cliente WHERE id = ?";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = createCustomer(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cliente por ID", e);
        }
        return customer;
    }

    @Override
    public void save(cliente customer) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "INSERT INTO cliente(name, IDnumber, date_birth) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getNumber_ID());
            preparedStatement.setDate(3, new java.sql.Date(customer.getDateBirth().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar cliente", e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "DELETE FROM cliente WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar cliente", e);
        }
    }

    @Override
        public void update(cliente customer) {
            try (PreparedStatement preparedStatement = getConnection()
                    .prepareStatement("UPDATE cliente SET name = ?, IDnumber = ?, date_birth = ? WHERE id = ?")) {
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getNumber_ID());
                preparedStatement.setDate(3, new java.sql.Date(customer.getDateBirth().getTime()));
                preparedStatement.setInt(4, customer.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error al actualizar cliente", e);
            }
        }

    private cliente createCustomer(ResultSet resultSet) throws SQLException {
        cliente customer = new cliente();
        customer.setId(resultSet.getInt("id"));
        customer.setName(resultSet.getString("name"));
        customer.setNumber_ID(resultSet.getString("IDnumber"));
        customer.setDateBirth(resultSet.getDate("date_birth"));
        return customer;
    }
}
