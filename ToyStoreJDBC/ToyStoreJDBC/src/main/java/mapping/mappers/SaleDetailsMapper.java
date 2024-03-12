package mapping.mappers;

import mapping.dtos.SaleDetailsDTO;
import model.sale_details;

public class SaleDetailsMapper {
    public static SaleDetailsDTO mapFromModel(sale_details sale) {
        return new SaleDetailsDTO(sale.getSale(), sale.getToy(), sale.getQuantities(), sale.getPrice());
    }

    public static sale_details mapFromDTO(SaleDetailsDTO sale) {
        return sale_details.builder()
                .sale(sale.sale())
                .toy(sale.toy())
                .quantities(sale.quantities())
                .price(sale.price())
                .build();
    }
}
