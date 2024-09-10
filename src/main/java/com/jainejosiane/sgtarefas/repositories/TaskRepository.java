package com.jainejosiane.sgtarefas.repositories;

import com.jainejosiane.sgtarefas.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}