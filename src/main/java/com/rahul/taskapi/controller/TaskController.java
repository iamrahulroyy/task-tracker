package com.rahul.taskapi.controller;

import com.rahul.taskapi.dto.TaskRequest;
import com.rahul.taskapi.model.Task;
import com.rahul.taskapi.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TaskRequest req) {

        Task task = new Task();
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setStatus(req.getStatus());

        Task created = service.create(task);

        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(required = false) String status,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("page", page);
        res.put("size", size);
        res.put("data", service.getTasksByStatus(status, page, size));

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {

        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("data", service.getById(id));

        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody TaskRequest req) {

        Task task = new Task();
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setStatus(req.getStatus());

        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("message", "Task updated");
        res.put("data", service.update(id, task));
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Map<String, Object> msg(String m) {
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("message", m);
        return res;
    }
}