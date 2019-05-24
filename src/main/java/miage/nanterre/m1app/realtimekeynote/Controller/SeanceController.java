package miage.nanterre.m1app.realtimekeynote.Controller;

import miage.nanterre.m1app.realtimekeynote.Builder.SeanceBuilder;
import miage.nanterre.m1app.realtimekeynote.DAO.SeanceDAO;
import miage.nanterre.m1app.realtimekeynote.DAO.UserDAO;
import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.UserRepository;
import miage.nanterre.m1app.realtimekeynote.helpers.Helper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/seance")
public class SeanceController extends SeanceBuilder {

    private UserRepository userRepository;
    private SeanceRepository seanceRepository;

    public SeanceController(UserRepository userRepository, SeanceRepository seanceRepository) {
        this.userRepository = userRepository;
        this.seanceRepository = seanceRepository;
    }

    @RequestMapping(value = "/all/user/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> getSeanceByUser(@PathVariable("id") long userId) {
        User user = userRepository.findById(userId).get();
        return new ResponseEntity<Object>(user.getSeances(), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> getAllSeance() {
        return new ResponseEntity<>(seanceRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value="/create")
    public void createSeance() {

        User user =  userRepository
                .findById((long)1)
                .orElse(new User(
                        "Basile",
                        "Mauvieux",
                        null)
                );

        Date beginnningDate = new Date();
        beginnningDate.setHours(12);
        beginnningDate.setMinutes(30);

        Seance seance = new Seance();
        seance
                .setName("Test")
                .setSubject("Je suis une séance de test")
                .setDescription("Lorem ipsum dotrhari manawe")
                .setPubliq("Promotion de M1")
                .setParticipants(412)
                .setDate(new Date())
                .setBeginningTime(beginnningDate)
                .setEndingTime(Helper.getTimeFromString("12:30:45"))
                .setRoom("123")
                .setUser(user);

        seanceRepository.save(seance);
    }
}
