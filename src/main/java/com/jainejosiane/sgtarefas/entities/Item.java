package com.jainejosiane.sgtarefas.entities;

import com.jainejosiane.sgtarefas.dto.ItemDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "tb_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 20, message = "Title must have a minimum of 5 characters and a maximum of 20")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    private ItemStatus status;

    @NotNull
    private Boolean highlighted;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Item(ItemDTO dto) {
        id = dto.getId();
        title = dto.getTitle();
        description = dto.getDescription();
        status = dto.getStatus();
        highlighted = dto.getHighlighted();
    }
}