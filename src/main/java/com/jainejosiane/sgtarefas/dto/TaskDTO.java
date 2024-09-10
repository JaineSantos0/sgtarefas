package com.jainejosiane.sgtarefas.dto;

import com.jainejosiane.sgtarefas.entities.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskDTO {

    private Long id;

    private String title;

    private List<ItemDTO> itemList = new ArrayList<>();

    public TaskDTO() {
    }

    public TaskDTO(Long id, String title, List<ItemDTO> itemList) {
        this.id = id;
        this.title = title;
        this.itemList = itemList;
    }

    public TaskDTO(Task entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.itemList = entity.getItemList() != null ? entity.getItemList().stream()
                .map(ItemDTO::new)
                .collect(Collectors.toList()) : List.of();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ItemDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemDTO> itemList) {
        this.itemList = itemList;
    }
}