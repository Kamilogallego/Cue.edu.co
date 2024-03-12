package mapping.dtos;

import lombok.Builder;

import java.util.Date;
@Builder
public record EmployeesDTO ( String user, String password, Date start_date_employment){
}
