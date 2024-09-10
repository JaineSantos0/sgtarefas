package com.jainejosiane.sgtarefas.services;

import com.jainejosiane.sgtarefas.dto.ItemDTO;
import com.jainejosiane.sgtarefas.entities.Item;
import com.jainejosiane.sgtarefas.entities.ItemStatus;
import com.jainejosiane.sgtarefas.entities.Task;
import com.jainejosiane.sgtarefas.repositories.ItemRepository;
import com.jainejosiane.sgtarefas.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private TaskService service;

    @Transactional
    public ItemDTO insert(ItemDTO dto, Long taskId) {

        Item entity = new Item();

        copyDtoToEntity(entity, dto);

        if (taskId != null) {
            Task task = service.findTaskById(taskId);
            entity.setTask(task);
        }

        entity = repository.save(entity);
        return new ItemDTO(entity);
    }

    @Transactional
    public ItemDTO update(ItemDTO dto, Long itemId) {
        Item entity = repository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        copyDtoToEntity(entity, dto);

        Item saved = repository.save(entity);
        return new ItemDTO(saved);
    }

    @Transactional()
    public void delete(Long id){
        Item entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        repository.deleteById(id);
    }

    @Transactional
    public ItemDTO updateStatus(ItemDTO dto, Long itemId) {
        Item entity = repository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        entity.setStatus(dto.getStatus());

        Item saved = repository.save(entity);
        return new ItemDTO(saved);
    }

    @Transactional
    public ItemDTO updateHighlighted(ItemDTO dto, Long itemId) {
        Item entity = repository.findById(itemId).orElseThrow( () -> new ResourceNotFoundException("Item not found"));

        entity.setHighlighted(dto.getHighlighted());

        Item saved = repository.save(entity);
        return new ItemDTO(saved);
    }

    @Transactional(readOnly = true)
    public Page<ItemDTO> findAll(Pageable pageable, ItemStatus status) {
        Page<Item> items;

        if (status != null) {
            items = repository.findByStatusOrderByHighlighted(status, pageable);
        } else {
            items = repository.findAll(pageable);
        }

        List<ItemDTO> itemDTOs = items.stream()
                .map(ItemDTO::new)
                .sorted(Comparator.comparing(ItemDTO::getHighlighted).reversed())
                .collect(Collectors.toList());

        return new PageImpl<>(itemDTOs, pageable, items.getTotalElements());
    }

    private void copyDtoToEntity(Item entity, ItemDTO dto) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setHighlighted(dto.getHighlighted());
    }
}