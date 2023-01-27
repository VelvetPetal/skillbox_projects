package main;

import main.model.Task;
import main.model.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    public List<Task> list() {
        Iterable<Task> iterable = taskRepository.findAll();
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : iterable) {
            tasks.add(task);
        }
        return tasks;
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> get(@PathVariable int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PatchMapping(value = "/tasks/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> change(@PathVariable int id, @RequestBody Task task) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            if(task.getIsDone() != null){
                optionalTask.get().setIsDone(task.getIsDone());
            }
            if (task.getTitle() != null) {
                optionalTask.get().setTitle(task.getTitle());
            }
            if (task.getDescription() != null) {
                optionalTask.get().setDescription(task.getDescription());
            }
            taskRepository.save(optionalTask.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            taskRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping(value = "/tasks",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> add(@RequestBody Task task) {
        if (task.getTitle() == null
                || task.getDescription() == null
                || task.getCreationDate() != null
                || task.getIsDone() != null) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
        }
        task.setCreationDate(LocalDateTime.now());
        task.setIsDone(false);
        taskRepository.save(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
