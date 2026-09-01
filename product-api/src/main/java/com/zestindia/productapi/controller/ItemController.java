package com.zestindia.productapi.controller;

import com.zestindia.productapi.entity.Item;
import com.zestindia.productapi.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // CREATE ITEM
    @PostMapping("/product/{productId}")
    public ResponseEntity<Item> createItem(
            @PathVariable Integer productId,
            @RequestBody Item item) {

        Item savedItem = itemService.createItem(productId, item);

        return new ResponseEntity<>(
                savedItem,
                HttpStatus.CREATED
        );
    }

    // GET ALL ITEMS
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {

        return ResponseEntity.ok(
                itemService.getAllItems()
        );
    }

    // GET ITEM BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                itemService.getItemById(id)
        );
    }

    // UPDATE ITEM
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(
            @PathVariable Integer id,
            @RequestBody Item item) {

        return ResponseEntity.ok(
                itemService.updateItem(id, item)
        );
    }

    // DELETE ITEM
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Integer id) {

        itemService.deleteItem(id);

        return ResponseEntity.noContent().build();
    }
}