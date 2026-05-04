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

    public Task create(Task t) {

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

        int id = repo.createTask(t);

        // Fetch full row so DB-generated fields (created_at) are included
        return repo.getTaskById(id);
    }

    public Task getById(int id) {
        return repo.getTaskById(id);
    }
    
    // get by status filter (with pagination)
    public List<Task> getTasksByStatus(String status, int page, int size) {

        if (status == null || status.isBlank()) {
            return repo.getAllTasks(page, size);
        }

        String normalizedStatus = status.trim().toUpperCase();

        try {
            TaskStatus.valueOf(normalizedStatus);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "Invalid status. Allowed values: PENDING, IN_PROGRESS, DONE"
            );
        }

        return repo.getTasksByStatus(normalizedStatus, page, size);
    }

    public Task update(int id, Task t) {

        // Ensure task exists first
        Task existing = repo.getTaskById(id);

        if (t.getTitle() == null || t.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }

        t.setTitle(t.getTitle().trim());
        if (t.getDescription() != null) t.setDescription(t.getDescription().trim());

        if (t.getStatus() == null || t.getStatus().isBlank()) {
            t.setStatus(existing.getStatus()); // keep existing status if not provided
        }

        String normalizedStatus = t.getStatus().trim().toUpperCase();
        try {
            TaskStatus.valueOf(normalizedStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status. Allowed values: PENDING, IN_PROGRESS, DONE");
        }
        t.setStatus(normalizedStatus);
        t.setId(id);

        repo.updateTask(t);
        return repo.getTaskById(id);
    }

    public void delete(int id) {
        // Ensure task exists before deleting
        repo.getTaskById(id);
        repo.deleteTask(id);
    }
}