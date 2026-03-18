package org.example.exameninterfaces4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "store")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    private String mongoId; // Mongo

    @Field("id")
    private Integer id; // id normal

    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
    private Double rate;
    private Integer count;
    private String color;
    private String EAN;
    private String manufacturer;
}
