package com.superloop.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "TODO_ITEM")
public class TodoItem {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME",nullable = false)
    @Size(min = 1, max = 50, message = "description must be between 1 and 50 chars long")
    String name;

    @Column(name = "DESCRIPTION")
    @Size(min = 1, max = 50, message = "description must be between 1 and 50 chars long")
    String description;

    @Column(name = "STATUS",nullable = false)
    String status;

    @Column(name = "DUE_DATE")
    LocalDate dueDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TodoItem(Long id, String name, String description,
                    String status, LocalDate dueDate) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.name = name;
        this.status = status;
    }
    public TodoItem (){}

}
