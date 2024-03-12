package mapping.mappers;

import mapping.dtos.EmployeesDTO;
import model.employees;

import java.sql.Date;

public class EmployeesMapper {
    public static EmployeesDTO mapFromModel(employees employees){
        return new EmployeesDTO(employees.getUser(),employees.getPassword(),employees.getStart_date_employment());

    }
    public static employees mapFromDTO(EmployeesDTO dto){
        return employees.builder()
                .user(dto.user())
                .password(dto.password())
                .start_date_employment((Date) dto.start_date_employment())
                .build();
    }

}
