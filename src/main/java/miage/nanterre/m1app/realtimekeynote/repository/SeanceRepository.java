package miage.nanterre.m1app.realtimekeynote.repository;

import miage.nanterre.m1app.realtimekeynote.model.Seance;
import miage.nanterre.m1app.realtimekeynote.model.SeanceAnalytics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeanceRepository extends CrudRepository<Seance, Long> {

}
