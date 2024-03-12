package repository.employeimpl;

import config.DatabaseConnection;
import model.employees;
import model.sale;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeRepositoryJDBCimpl implements Repository<employees> {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }
    private employees createEmployee(ResultSet resultSet) throws SQLException{
        employees e = new employees();
        e.setId(resultSet.getInt("id"));
        e.setUser(resultSet.getString("user"));
        e.setPassword(resultSet.getString("password"));
        e.setStart_date_employment(resultSet.getDate("employment_start_date"));
        return e;

    }
    @Override
    public List<employees> list() {
        List<employees>employeesList=new ArrayList<>();
        try(Statement statement=getConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(
                    """
                        
                            SELECT e.*  
                        FROM employees AS e
                        
                       
                        
                        """
            ))
        {
            while (resultSet.next()){
                employees e = createEmployee(resultSet);
                employeesList.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeesList;
    }

    @Override
    public employees byID(int id) {
        employees employee = null;
        try {
            String sql = "SELECT e.* FROM employees AS e WHERE e.id = ?";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = createEmployee(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleado por ID", e);
        }
        return employee;
    }

    @Override
    public void save(employees employees) {
        try (PreparedStatement pst = getConnection()
                .prepareStatement("""
                                          INSERT INTO employees(user,password,employment_start_date) values (?,?,?)
                                          """)


        ){
            pst.setString(1,employees.getUser());
            pst.setString(2,employees.getPassword());
            pst.setDate(3,employees.getStart_date_employment());
            pst.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {
        try(PreparedStatement preparedStatement = getConnection()
              .prepareStatement("""
                                       DELETE FROM employees where id=?
                                       """)
        ){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(employees employees) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("UPDATE employees SET user = ?, password = ?, employment_start_date = ? WHERE id = ?")) {
            preparedStatement.setString(1, employees.getUser());
            preparedStatement.setString(2, employees.getPassword());
            preparedStatement.setDate(3, employees.getStart_date_employment());
            preparedStatement.setInt(4, employees.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar empleado", e);
        }
    }
}
