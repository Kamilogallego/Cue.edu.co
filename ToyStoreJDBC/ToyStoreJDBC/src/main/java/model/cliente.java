package model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class cliente {
    private int id;
    private String name;
    private String number_ID;
    private Date dateBirth;


}
