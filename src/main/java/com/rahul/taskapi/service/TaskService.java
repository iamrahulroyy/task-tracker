package com.rahul.taskapi.service;

import com.rahul.taskapi.model.Task;
import com.rahul.taskapi.model.TaskStatus;
import com.rahul.taskapi.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public int create(Task t) {

        // ---- TITLE VALIDATION ----
        if (t.getTitle() == null || t.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }

        // Trim values
        t.setTitle(t.getTitle().trim());

        if (t.getDescription() != null) {
            t.setDescription(t.getDescription().trim());
        }

        // ---- STATUS DEFAULT ----
        if (t.getStatus() == null || t.getStatus().isBlank()) {
            t.setStatus("PENDING");
        }

        // Normalize (important)
        String normalizedStatus = t.getStatus().trim().toUpperCase();

        // ---- STATUS VALIDATION ----
        try {
            TaskStatus.valueOf(normalizedStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid status. Allowed values: PENDING, IN_PROGRESS, DONE"
            );
        }

        // Set normalized value
        t.setStatus(normalizedStatus);

        return repo.createTask(t);
    }

    public Task getById(int id) {
        return repo.getTaskById(id);
    }
    
    // get by status filter
    public List<Task> getTasksByStatus(String status) {

        if (status == null || status.isBlank()) {
            return repo.getAllTasks();
        }

        String normalizedStatus = status.trim().toUpperCase();

        try {
            TaskStatus.valueOf(normalizedStatus);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "Invalid status. Allowed values: PENDING, IN_PROGRESS, DONE"
            );
        }

        return repo.getTasksByStatus(normalizedStatus);
    }
}