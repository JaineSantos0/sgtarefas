package com.jainejosiane.sgtarefas.dto;

import com.jainejosiane.sgtarefas.entities.Item;
import com.jainejosiane.sgtarefas.entities.ItemStatus;

public class ItemDTO {

    private Long id;

    private String title;

    private String description;

    private ItemStatus status;

    private Boolean highlighted;

    private Long taskId;

    public ItemDTO() {
    }

    public ItemDTO(Long id, String title, String description, ItemStatus status, Boolean highlighted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.highlighted = highlighted;
    }

    public ItemDTO(Item entity) {
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        status = entity.getStatus();
        highlighted = entity.getHighlighted();
        taskId = entity.getTask().getId();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public Boolean getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(Boolean highlighted) {
        this.highlighted = highlighted;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}