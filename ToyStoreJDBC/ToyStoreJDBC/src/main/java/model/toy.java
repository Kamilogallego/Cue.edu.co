package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class toy {
    private int id;
    private String name;
    private int price;
    private category category;
    private int stock;



}