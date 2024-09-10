package com.jainejosiane.sgtarefas.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_task")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 20, message = "Title must have a minimum of 5 characters and a maximum of 20")
    private String title;

    @OneToMany(mappedBy = "task",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itemList = new ArrayList<>();

    public void addItem(Item item) {
        this.itemList.add(item);
        item.setTask(this);
    }

    public void removeItem(Item item) {
        this.itemList.remove(item);
        item.setTask(null);
    }
}