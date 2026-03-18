package org.example.exameninterfaces4.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.example.exameninterfaces4.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item,String> {

    List<Item> findByManufacturer(String manufacturer);

    List<Item> findByCategory(String category);

    List<Item> findByCountLessThan(Integer umbral);

    List<Item> findByTitleContainingIgnoreCase(String title);

    Optional<Object> findAllManufacturers();

    long countByManufacturer(String manufacturer);
}
