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

        service.create(task);
        return ResponseEntity.ok(msg("Task created"));
    }

    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(required = false) String status) {

        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("data", service.getTasksByStatus(status));

        return ResponseEntity.ok(res);
    }  

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {

        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("data", service.getById(id));

        return ResponseEntity.ok(res);
    }

    private Map<String, Object> msg(String m) {
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("message", m);
        return res;
    }
}