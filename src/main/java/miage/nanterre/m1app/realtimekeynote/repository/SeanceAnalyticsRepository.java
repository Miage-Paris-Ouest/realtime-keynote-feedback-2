package miage.nanterre.m1app.realtimekeynote.repository;

import miage.nanterre.m1app.realtimekeynote.model.SeanceAnalytics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface SeanceAnalyticsRepository  extends CrudRepository<SeanceAnalytics, Long> {
}
