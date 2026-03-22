package ma.example.parcinfo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ma.example.parcinfo.entity.Materiel;

import java.util.List;

@Repository
public interface MaterielRepository extends CrudRepository<Materiel, Long> {
    List<Materiel> findByTypeContainingAndEtatContaining(String type, String etat);
    @Query("SELECT m.etat, COUNT(m) FROM Materiel m GROUP BY m.etat")
    List<Object[]> countByEtat();
}