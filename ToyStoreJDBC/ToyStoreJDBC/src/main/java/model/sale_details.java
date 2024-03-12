package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class sale_details {
    private int id;
    private sale sale;
    private toy toy;
    private int quantities;
    private int price;
}
