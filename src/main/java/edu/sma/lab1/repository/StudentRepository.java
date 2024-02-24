package edu.sma.lab1.repository;
/*
  @author   taras
  @project   lab1
  @class  StudentRepository
  @version  1.0.0
  @since 23.02.24 - 17.42
*/

import edu.sma.lab1.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
}
