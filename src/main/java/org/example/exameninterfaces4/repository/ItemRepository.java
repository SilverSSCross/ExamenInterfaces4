package org.example.exameninterfaces4.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.example.exameninterfaces4.model.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemRepository extends MongoRepository<Item,String> {

    List<Item> findByManufacturer(String manufacturer);

    List<Item> findByCategory(String category);

    List<Item> findByCountLessThan(Integer umbral);

    List<Item> findByTitleContainingIgnoreCase(String title);

    long countByManufacturer(String manufacturer);
}
