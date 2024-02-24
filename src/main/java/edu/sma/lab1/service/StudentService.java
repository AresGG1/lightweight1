package edu.sma.lab1.service;
/*
  @author   taras
  @project   lab1
  @class  StudentService
  @version  1.0.0
  @since 23.02.24 - 17.42
*/

import edu.sma.lab1.model.Student;
import edu.sma.lab1.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    @Cacheable(value = "students", key = "'all'")
    public List<Student> getAll() {
        log.info("Getting all records");

        return studentRepository.findAll();
    }

    @Cacheable(value = "students", key = "#id")
    public Student getById(String id) {
        log.info("Getting record with id " + id);

        return studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student with such id not found"));
    }
    @CacheEvict(value = "students", key="'all'")
    @CachePut(value = "students", key = "#student.id")
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "students", key = "'all'"),
                    @CacheEvict(value = "students", key = "#id")}
    )
    public void delete(String id) {
        if (!studentRepository.existsById(id)) {
            throw new NoSuchElementException("Student with such id not found");
        }

        studentRepository.deleteById(id);
    }

    @CacheEvict(value = "students", key="'all'")
    @CachePut(value = "students", key = "#student.id")
    public Student update(Student student) {
        if (!studentRepository.existsById(student.getId())) {
            throw new NoSuchElementException("Student with such id not found");
        }

        return studentRepository.save(student);
    }

}
