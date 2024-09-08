package com.jainejosiane.sgtarefas.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter()
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 20)
    private String title;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "task")
    private List<Item> itemList = new ArrayList<>();
}