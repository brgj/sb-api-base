package com.bradgjohnson.restservice.controllers;

import com.bradgjohnson.restservice.models.*;
import com.bradgjohnson.restservice.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/items")
@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping
    public ResponseEntity<CreateItemResponse> postItem(@RequestBody ItemRequest itemRequest) {
        var id = itemService.storeItem(itemRequest.getItemModel());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateItemResponse(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetItemResponse> getItem(@PathVariable("id") String itemId) {
        var domainItem = itemService.getItem(itemId);

        if (domainItem.isPresent()) {
            return ResponseEntity.ok(new GetItemResponse(ItemModel.from(domainItem.get())));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DeleteItemResponse> deleteItem(@PathVariable("id") String itemId) {
        boolean success = itemService.deleteItem(itemId);

        return ResponseEntity.ok(new DeleteItemResponse(success));
    }
}
