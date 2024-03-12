package mapping.dtos;

import lombok.Builder;

import java.util.Date;
@Builder
public record ClienteDTO ( String name, String number_ID, Date dateBirth){
}
