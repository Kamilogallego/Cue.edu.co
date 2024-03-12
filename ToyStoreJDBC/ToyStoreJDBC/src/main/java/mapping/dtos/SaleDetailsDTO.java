package mapping.dtos;

import lombok.Builder;
import model.sale;
import model.toy;
@Builder
public record SaleDetailsDTO(sale sale, toy toy, int quantities, int price)  {
}
