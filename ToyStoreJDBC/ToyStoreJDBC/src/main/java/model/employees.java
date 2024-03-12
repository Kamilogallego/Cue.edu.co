package model;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class employees {
    private int id;
    private String user;
    private String password;
    private Date start_date_employment;


}
