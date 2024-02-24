package edu.sma.lab1.controller;
/*
  @author   taras
  @project   lab1
  @class  StudentController
  @version  1.0.0
  @since 23.02.24 - 17.42
*/

import edu.sma.lab1.exception.IdentifierMismatchException;
import edu.sma.lab1.model.Response;
import edu.sma.lab1.model.Student;
import edu.sma.lab1.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> fetchAll() {
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    public Student fetchById(@PathVariable String id) {
        return studentService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Student> insert(@RequestBody Student student) {
        return new ResponseEntity<Student>(studentService.create(student), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void eraseById(@PathVariable String id) {
        studentService.delete(id);
    }

    @PutMapping("/{id}")
    public Student update(@RequestBody Student student, @PathVariable String id) throws IdentifierMismatchException {
        if (!Objects.equals(id, student.getId())) {
            throw new IdentifierMismatchException("ID in URL does not match ID in the request body");
        }

        return studentService.update(student);
    }

    @ExceptionHandler({ NoSuchElementException.class, IdentifierMismatchException.class })
    public ResponseEntity<Response> handleException(Exception e) {
        HttpStatusCode code = switchException(e);

        return new ResponseEntity<Response>(new Response(e.getMessage()), code);
    }

    private HttpStatusCode switchException(Exception e) {
        return switch (e.getClass().getSimpleName()) {
            case "NoSuchElementException" -> HttpStatus.NOT_FOUND;
            case "IdentifierMismatchException" -> HttpStatus.BAD_REQUEST;

            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
