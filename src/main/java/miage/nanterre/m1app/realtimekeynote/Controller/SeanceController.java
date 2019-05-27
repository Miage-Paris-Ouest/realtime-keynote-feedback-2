package miage.nanterre.m1app.realtimekeynote.Controller;

import miage.nanterre.m1app.realtimekeynote.Builder.SeanceBuilder;
import miage.nanterre.m1app.realtimekeynote.Exception.UserNotFoundException;
import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.UserRepository;
import miage.nanterre.m1app.realtimekeynote.Service.SeanceService;
import miage.nanterre.m1app.realtimekeynote.View.SeanceView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Controller
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

    @RequestMapping(value = "/all", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> getAllSeanceData() {
        ArrayList<HashMap> response = SeanceService.getDurationSession(seanceRepository);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path= "/create")
    public Seance createSeance(@RequestBody SeanceView seanceView) throws UserNotFoundException {
    User user = userRepository
                .findById(Long.parseLong(seanceView.getUser()))
                .orElseThrow(UserNotFoundException::new);

        Date beginnningDate = new Date();
        beginnningDate.setHours(12);
        beginnningDate.setMinutes(30);

        Seance seance = new Seance();
        seance
                .setName(seanceView.getName())
                .setSubject(seanceView.getSubject())
                .setDescription(seanceView.getDescription())
                .setPubliq(seanceView.getPubliq())
                .setParticipants(seance.getParticipants())
                .setDate(seanceView.getDate())
                .setBeginningTime(seanceView.getBeginningTime())
                .setEndingTime(seanceView.getEndingTime())
                .setRoom(seanceView.getRoom())
                .setUser(user);

        seanceRepository.save(seance);
        return seance;
    }
}
