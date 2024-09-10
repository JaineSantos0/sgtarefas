package com.jainejosiane.sgtarefas.controllers;

import com.jainejosiane.sgtarefas.dto.ItemDTO;
import com.jainejosiane.sgtarefas.entities.ItemStatus;
import com.jainejosiane.sgtarefas.services.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService service;

    @PostMapping("/tasks/{taskId}")
    public ResponseEntity<ItemDTO> insert(@Valid @RequestBody ItemDTO dto, @PathVariable Long taskId) {
        dto = service.insert(dto, taskId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{taskId}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> update(@Valid @RequestBody ItemDTO dto, @PathVariable Long id) {
        dto = service.update(dto, id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("status/{id}")
    public ResponseEntity<ItemDTO> updateStatus(@Valid @RequestBody ItemDTO dto, @PathVariable Long id) {
        dto = service.updateStatus(dto, id);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("highlighted/{id}")
    public ResponseEntity<ItemDTO> updateHighlighted(@Valid @RequestBody ItemDTO dto, @PathVariable Long id) {
        dto = service.updateHighlighted(dto, id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ItemDTO>> findAll(
            @RequestParam(value = "status", required = false) ItemStatus status,
            Pageable pageable
    ) {
        Page<ItemDTO> items = service.findAll(pageable, status);
        return ResponseEntity.ok(items);
    }
}