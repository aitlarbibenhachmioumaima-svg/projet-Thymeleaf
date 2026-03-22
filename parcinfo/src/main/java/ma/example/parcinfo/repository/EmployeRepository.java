package ma.example.parcinfo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ma.example.parcinfo.entity.Employe;

@Repository
public interface EmployeRepository extends CrudRepository<Employe, Long> {
}