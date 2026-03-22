package ma.example.parcinfo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ma.example.parcinfo.entity.Affectation;

import java.util.List;

@Repository
public interface AffectationRepository extends CrudRepository<Affectation, Long> {
    List<Affectation> findByStatutContaining(String statut);
    @Query("SELECT COUNT(a) FROM Affectation a WHERE a.statut = 'active'")
    long countActive();
}