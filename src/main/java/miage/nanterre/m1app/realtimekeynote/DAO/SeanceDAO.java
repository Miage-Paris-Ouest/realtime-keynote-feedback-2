package miage.nanterre.m1app.realtimekeynote.DAO;

import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.UserRepository;
import miage.nanterre.m1app.realtimekeynote.helpers.DateHelper;
import miage.nanterre.m1app.realtimekeynote.helpers.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

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


    @PostConstruct
    public void testCreateSeance() {
        User user = new User("Christelle", "Ilunga", null);
        userRepository.save(user);
        String analytics1 = "20,20,20,20,20,20,20,20,20,20,25";
        Seance seance1 = new Seance(
                "Conférence espace des Science 1",
                "2",
                "Public",
                DateHelper.asDate(LocalDate.of(2018, 12, 20)),
                new Date(2018,1,13, 13, 30),
                new Date(2018,1,13, 17, 30),
                "Description d'une conférence scientifique.",
                user,
                50);
        seance1.getSeanceAnalytics().setAnalyticsData(analytics1);
        repository.save(seance1);

        String analytics2 = "30,30,30,30,30,30,30,30,30,30,30,35";
        Seance seance2 = new Seance(
                "Conférence espace des Science 2",
                "2",
                "Public",
                DateHelper.asDate(LocalDate.of(2019, 1, 28)),
                new Date(2018,1,13, 13, 30),
                new Date(2018,1,13, 17, 30),
                "Description d'une conférence scientifique.",
                user,
                50);
        seance2.getSeanceAnalytics().setAnalyticsData(analytics2);
        repository.save(seance2);

        String analytics3 = "40,40,40,40,40,40,40,40,40,40,40,45";
        Seance seance3  = new Seance(
                "Conférence espace des Science 1",
                "2",
                "Public",
                DateHelper.asDate(LocalDate.of(2019, 2, 28)),
                new Date(2018,1,13, 13, 30),
                new Date(2018,1,13, 17, 30),
                "Description d'une conférence scientifique.",
                user,
                50);
        seance3.getSeanceAnalytics().setAnalyticsData(analytics3);
        repository.save(seance3);

        String analytics4 = "42,42,42,42,42,42,42,42,42,43";
        Seance seance4 = new Seance(
                "Conférence espace des Science 1",
                "2",
                "Public",
                DateHelper.asDate(LocalDate.of(2019, 3, 28)),
                new Date(2018,1,13, 13, 30),
                new Date(2018,1,13, 17, 30),
                "Description d'une conférence scientifique.",
                user,
                50);
        seance4.getSeanceAnalytics().setAnalyticsData(analytics4);
        repository.save(seance4);

        String analytics5 = "45,45,45,45,45,45";
        Seance seance5 = new Seance(
                "Conférence espace des Science 1",
                "2",
                "Public",
                DateHelper.asDate(LocalDate.of(2019, 4, 28)),
                new Date(2018,1,13, 13, 30),
                new Date(2018,1,13, 17, 30),
                "Description d'une conférence scientifique.",
                user,
                50);
        seance5.getSeanceAnalytics().setAnalyticsData(analytics5);
        repository.save(seance5);

        String analytics6 = "47,47,47,47,47,47";
        Seance seance6 = new Seance(
                "Conférence espace des Science 1",
                "2",
                "Public",
                DateHelper.asDate(LocalDate.of(2019, 5, 28)),
                new Date(2018,1,13, 13, 30),
                new Date(2018,1,13, 17, 30),
                "Description d'une conférence scientifique.",
                user,
                50);
        seance6.getSeanceAnalytics().setAnalyticsData(analytics6);
        repository.save(seance6);





        User user1 = userRepository.findById((long) 1).get();

    }
}
