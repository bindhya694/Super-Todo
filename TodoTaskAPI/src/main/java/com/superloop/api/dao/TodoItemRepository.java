package com.superloop.api.dao;

import com.superloop.api.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TodoItemRepository - is the interface which use spring data JPA and extends
 * JpaRepository to execute database queries
 * 
 * @author Bindhya Maya
 * Version 1.0
 * Created Date 29/01/2021
 *
 */
@Repository("toDoItemRepository")
public interface TodoItemRepository extends JpaRepository<TodoItem,Long> {

    List<TodoItem> findByStatus(String todoStatus);
}
