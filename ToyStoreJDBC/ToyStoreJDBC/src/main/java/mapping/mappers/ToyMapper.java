package mapping.mappers;

import mapping.dtos.ToyDTO;
import model.toy;

public class ToyMapper {
    public static ToyDTO mapFromModel(toy toy) {
        return new ToyDTO(toy.getName(), toy.getPrice(), toy.getCategory(), toy.getStock());
    }

    public static toy mapFromDTO(ToyDTO dto) {
        return toy.builder()
                .name(dto.name())
                .price(dto.price())
                .category(dto.category())
                .stock(dto.stock())
                .build();
    }
}



