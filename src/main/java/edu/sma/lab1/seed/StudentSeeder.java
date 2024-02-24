package edu.sma.lab1.seed;
/*
  @author   taras
  @project   lab1
  @class  StudentSeeder
  @version  1.0.0
  @since 23.02.24 - 17.42
*/

import edu.sma.lab1.model.Student;
import edu.sma.lab1.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StudentSeeder implements CommandLineRunner {
    private final StudentRepository studentRepository;
    private final List<Student> studentsData = List.of(
            new Student(
                    null,
                    "Rostik",
                    "Teterchuk",
                    21,
                    "0987645163"
            ),
            new Student(
                    null,
                    "Mark",
                    "Havrylov",
                    20,
                    "0987339173"
            )
    );


    @Override
    public void run(String... args) throws Exception {
        if (!studentRepository.findAll().isEmpty()) {
            log.info("db already contains some data");
            return;
        }

        studentRepository.saveAll(studentsData);

        log.info("Filled db with sample data");
    }
}
