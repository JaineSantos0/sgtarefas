package com.jainejosiane.sgtarefas.repositories;

import com.jainejosiane.sgtarefas.entities.Item;
import com.jainejosiane.sgtarefas.entities.ItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findByStatusOrderByHighlighted(@Param("status") ItemStatus status, Pageable pageable);
}