package com.jainejosiane.sgtarefas.services;

import com.jainejosiane.sgtarefas.dto.ItemDTO;
import com.jainejosiane.sgtarefas.dto.TaskDTO;
import com.jainejosiane.sgtarefas.entities.Item;
import com.jainejosiane.sgtarefas.entities.Task;
import com.jainejosiane.sgtarefas.repositories.TaskRepository;
import com.jainejosiane.sgtarefas.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Transactional
    public TaskDTO insert(TaskDTO dto) {

        Task task = new Task();
        task.setTitle(dto.getTitle());

        List<Item> items = new ArrayList<>();
        for (ItemDTO itemDTO : dto.getItemList()) {
            Item item = new Item();
            item.setTitle(itemDTO.getTitle());
            item.setDescription(itemDTO.getDescription());
            item.setStatus(itemDTO.getStatus());
            item.setHighlighted(itemDTO.getHighlighted());
            item.setTask(task);
            items.add(item);
        }
        task.setItemList(items);

        Task entityTask = repository.save(task);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(entityTask.getId());
        taskDTO.setTitle(entityTask.getTitle());

        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (Item item : entityTask.getItemList()) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setTitle(item.getTitle());
            itemDTO.setDescription(item.getDescription());
            itemDTO.setStatus(item.getStatus());
            itemDTO.setHighlighted(item.getHighlighted());
            itemDTOList.add(itemDTO);
        }
        taskDTO.setItemList(itemDTOList);

        return taskDTO;
    }

    @Transactional(readOnly = true)
    public Task findTaskById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    @Transactional(readOnly = true)
    public TaskDTO findById(Long id) {
        Task entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        return new TaskDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<TaskDTO> findAll(Pageable pageable) {
        Page<Task> tasks = repository.findAll(pageable);

        List<TaskDTO> taskDTOs = tasks.stream()
                .map(task -> {
                    List<ItemDTO> sortedItems = task.getItemList().stream()
                            .map(ItemDTO::new)
                            .sorted(Comparator.comparing(ItemDTO::getHighlighted).reversed())
                            .collect(Collectors.toList());

                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setId(task.getId());
                    taskDTO.setTitle(task.getTitle());
                    taskDTO.setItemList(sortedItems);

                    return taskDTO;
                }).collect(Collectors.toList());

        return new PageImpl<>(taskDTOs, pageable, tasks.getTotalElements());
    }
}