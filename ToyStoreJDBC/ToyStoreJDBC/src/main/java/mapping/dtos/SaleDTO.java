package mapping.dtos;

import lombok.Builder;
import model.cliente;
import model.employees;
@Builder
public record SaleDTO( cliente cliente, employees employees)  {
}
