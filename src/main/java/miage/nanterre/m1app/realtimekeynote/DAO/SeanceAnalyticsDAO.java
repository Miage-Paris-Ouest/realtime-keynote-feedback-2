package miage.nanterre.m1app.realtimekeynote.DAO;

import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeanceAnalyticsDAO {

    @Query("SELECT analytics_data, FROM 'seance_analytics' AS 'sa' 'seance' AS 's' " +
            "WHERE 'seance.id = sa.id' AND 's.beginning_time = 0' "
            , nativeQuery=true)
    public List<SeanceAnalytics> Get6LastMonth();
}
