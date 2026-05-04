package com.rahul.taskapi.repository;

import com.rahul.taskapi.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // CREATE
    public int createTask(Task task) {
        String sql = "INSERT INTO tasks (title, description, status) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }

    // GET ALL
    public List<Task> getAllTasks() {
        String sql = "SELECT * FROM tasks ORDER BY id DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
    }

    // GET BY ID
    public Task getTaskById(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapRow(rs), id);
    }

    // ROW MAPPING
    private Task mapRow(ResultSet rs) throws java.sql.SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setStatus(rs.getString("status")); 
        task.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return task;
    }

    public List<Task> getTasksByStatus(String status) {
        String sql = "SELECT * FROM tasks WHERE status = ? ORDER BY id DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs), status);
    }
}