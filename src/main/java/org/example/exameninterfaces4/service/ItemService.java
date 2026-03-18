package org.example.exameninterfaces4.service;

import org.example.exameninterfaces4.model.Item;
import org.example.exameninterfaces4.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item>findAll(){
        return itemRepository.findAll();
    }
    public Optional<Item> findById(String id) {
        return itemRepository.findById(id);
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public void deleteById(String id) {
        itemRepository.deleteById(id);
    }

    public long countTotalItems() {
        return itemRepository.count();
    }

    public List<Item> findByManufacturer(String manufacturer) {
        return itemRepository.findByManufacturer(manufacturer);
    }

    public List<Item> findByCategory(String category) {
        return itemRepository.findByCategory(category);
    }

    public List<Item> findByStockBajo(Integer umbral) {
        return itemRepository.findByCountLessThan(umbral);
    }

    public List<Item> searchByTitle(String title) {
        return itemRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<String> getAllManufacturers() {
        return itemRepository.findAll().stream()
                .map(Item::getManufacturer)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public long countByManufacturer(String manufacturer) {
        return itemRepository.countByManufacturer(manufacturer);
    }


    public Item updateItem(String id, Item itemActualizado) {
        return itemRepository.findById(id).map(item -> {
            item.setTitle(itemActualizado.getTitle());
            item.setPrice(itemActualizado.getPrice());
            item.setDescription(itemActualizado.getDescription());
            item.setCategory(itemActualizado.getCategory());
            item.setImage(itemActualizado.getImage());
            item.setRate(itemActualizado.getRate());
            item.setCount(itemActualizado.getCount());
            item.setColor(itemActualizado.getColor());
            item.setEAN(itemActualizado.getEAN());
            item.setManufacturer(itemActualizado.getManufacturer());
            return itemRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item no encontrado"));
    }
}
