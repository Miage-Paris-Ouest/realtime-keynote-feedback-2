package miage.nanterre.m1app.realtimekeynote.DAO;

import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequestMapping("/seance")
public class SeanceDAO {

    @Autowired
    private SeanceRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeanceAnalyticsRepository seanceAnalyticsRepository;

    public SeanceDAO(SeanceRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/list")
    public Iterable<Seance> getAllSeance() {
        return repository.findAll();
    }

    @RequestMapping("/get/sceanceId")
    public Seance getSeance(@RequestParam("seanceId") long seanceId) {
        return repository.findById(seanceId).get();
    }

    @RequestMapping("/get/delete")
    public void deleteSeance(@RequestParam("seanceId") long seanceId) {
        repository.deleteById(seanceId);
    }
}
