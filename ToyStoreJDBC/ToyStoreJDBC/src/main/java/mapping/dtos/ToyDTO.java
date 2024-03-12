package mapping.dtos;

import lombok.*;
import model.category;
@Builder


public record ToyDTO( String name, int price, category category, int stock) {

}

