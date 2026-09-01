package com.zestindia.productapi.service;

import com.zestindia.productapi.entity.Item;
import com.zestindia.productapi.entity.Product;
import com.zestindia.productapi.exception.ResourceNotFoundException;
import com.zestindia.productapi.repository.ItemRepository;
import com.zestindia.productapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;

    public ItemService(
            ItemRepository itemRepository,
            ProductRepository productRepository) {

        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
    }

    // Create Item
    public Item createItem(Integer productId, Item item) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + productId
                        )
                );

        item.setProduct(product);

        return itemRepository.save(item);
    }

    // Get All Items
    public List<Item> getAllItems() {

        return itemRepository.findAll();
    }

    // Get Item By ID
    public Item getItemById(Integer id) {

        return itemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Item not found with id: " + id
                        )
                );
    }

    // Update Item
    public Item updateItem(Integer id, Item item) {

        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Item not found with id: " + id
                        )
                );

        existingItem.setQuantity(item.getQuantity());

        return itemRepository.save(existingItem);
    }

    // Delete Item
    public void deleteItem(Integer id) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Item not found with id: " + id
                        )
                );

        itemRepository.delete(item);
    }
}