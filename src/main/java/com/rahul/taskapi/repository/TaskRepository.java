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

    // CREATE — returns generated id via PostgreSQL RETURNING clause
    public int createTask(Task task) {
        String sql = "INSERT INTO tasks (title, description, status) VALUES (?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Integer.class,
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }

    // GET ALL (with pagination)
    public List<Task> getAllTasks(int page, int size) {
        String sql = "SELECT * FROM tasks ORDER BY id DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs), size, page * size);
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

    public List<Task> getTasksByStatus(String status, int page, int size) {
        String sql = "SELECT * FROM tasks WHERE status = ? ORDER BY id DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs), status, size, page * size);
    }

    // UPDATE
    public int updateTask(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, status = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getId());
    }

    // DELETE
    public int deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}