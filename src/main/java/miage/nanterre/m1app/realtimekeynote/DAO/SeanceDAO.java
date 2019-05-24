package miage.nanterre.m1app.realtimekeynote.DAO;

import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.UserRepository;
import miage.nanterre.m1app.realtimekeynote.helpers.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.text.ParseException;
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
        try {
            Seance seance1 = new Seance(
                    "Conférence espace des Science 1",
                    "2",
                    "Public",
                    new Date(2018,01,12),
                    new Date(2018,01,13, 13, 30),
                    new Date(2018,01,13, 17, 30),
                    "Description d'une conférence scientifique.",
                    user,
                    50);
            repository.save(seance1);

            String analytics1 = "34,27,39,45,23,19,34,34,35,26,45,45,45,45,45,45";
            SeanceAnalytics seanceAnalytics1 = new SeanceAnalytics(seance1, analytics1);
            seanceAnalyticsRepository.save(seanceAnalytics1);
            Seance seance2 = new Seance(
                    "Conférence espace des Science 2",
                    "2",
                    "Public",
                    Helper.getDateFromString("2018-02-20"),
                    Helper.getTimeFromString("14:30:00"),
                    Helper.getTimeFromString("17:36:00"),
                    "Description d'une conférence scientifique.",
                    user,
                    50);
            repository.save(seance2);

            Seance seance3 = new Seance(
                    "Conférence espace des Science 3",
                    "2",
                    "Public",
                    Helper.getDateFromString("2018-03-12"),
                    Helper.getTimeFromString("12:30:00"),
                    Helper.getTimeFromString("17:15:00"),
                    "Description d'une conférence scientifique.",
                    user,
                    50);
            repository.save(seance3);

            Seance seance4 = new Seance(
                    "Conférence espace des Science 4",
                    "2",
                    "Public",
                    Helper.getDateFromString("2018-04-12"),
                    Helper.getTimeFromString("12:30:00"),
                    Helper.getTimeFromString("17:15:00"),
                    "Description d'une conférence scientifique.",
                    user,
                    50);
            repository.save(seance4);

            Seance seance5 = new Seance(
                    "Conférence espace des Science 5",
                    "2",
                    "Public",
                    Helper.getDateFromString("2018-05-12"),
                    Helper.getTimeFromString("12:30:00"),
                    Helper.getTimeFromString("17:15:00"),
                    "Description d'une conférence scientifique.",
                    user,
                    50);
            repository.save(seance5);

            Seance seance6 = new Seance(
                    "Conférence espace des Science 6",
                    "2",
                    "Public",
                    Helper.getDateFromString("2018-06-12"),
                    Helper.getTimeFromString("12:30:22"),
                    Helper.getTimeFromString("17:15:11"),
                    "Description d'une conférence scientifique.",
                    user,
                    48);
            repository.save(seance6);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        User user1 = userRepository.findById((long) 1).get();

    }
/*
    @PostConstruct
    public void testGetSeance(){
       System.out.println("Seance : "+getSeance(1));
    }

    @PostConstruct
    public void testDeleteSeance(){
        deleteSeance(1);
    }
*/
}
