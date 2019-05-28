package miage.nanterre.m1app.realtimekeynote.Controller;

import ch.qos.logback.core.joran.spi.ConsoleTarget;
import miage.nanterre.m1app.realtimekeynote.Builder.SeanceBuilder;
import miage.nanterre.m1app.realtimekeynote.Exception.UserNotFoundException;
import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.UserRepository;
import miage.nanterre.m1app.realtimekeynote.Service.SeanceAnalyticsService;
import miage.nanterre.m1app.realtimekeynote.Service.SeanceService;
import miage.nanterre.m1app.realtimekeynote.View.SeanceView;
import miage.nanterre.m1app.realtimekeynote.helpers.Helper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping(value = "/seance")
public class SeanceController extends SeanceBuilder {

    private UserRepository userRepository;
    private SeanceRepository seanceRepository;
    private SeanceAnalyticsRepository analyticsRepo;

    public SeanceController(UserRepository userRepository, SeanceRepository seanceRepository, SeanceAnalyticsRepository analyticsRepo) {
        this.userRepository = userRepository;
        this.seanceRepository = seanceRepository;
        this.analyticsRepo = analyticsRepo;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/all/user/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> getSeanceByUser(@PathVariable("id") long userId) {
        User user = userRepository.findById(userId).get();
        return new ResponseEntity<Object>(user.getSeances(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/all", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> getAllSeanceData() {
        ArrayList<HashMap> response = SeanceService.getDurationSession(seanceRepository);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path= "/create")
    public Seance createSeance(@RequestBody SeanceView seanceView) throws UserNotFoundException {
        User user = userRepository
                    .findById((long)1)
                    .orElseThrow(UserNotFoundException::new);

        String fileToAnalyse = seanceView.getFile();

        Seance seance = new Seance();
        seance
                .setName(seanceView.getName())
                .setSubject(seanceView.getSubject())
                .setDescription(seanceView.getDescription())
                .setPubliq(seanceView.getPubliq())
                .setParticipants(seanceView.getParticipants())
                .setDate(seanceView.getDate())
                .setBeginningTime(seanceView.getBeginningTime())
                .setEndingTime(seanceView.getEndingTime())
                .setRoom(seanceView.getRoom())
                .setUser(user);

        if (seance.getParticipants() == 0 ) {
            seance.setParticipants(1);
        }

        SeanceAnalytics analytics = seance.getSeanceAnalytics();
        analytics.setAnalyticsData(Helper.getRandomData(seance));
        seanceRepository.save(seance);

        SeanceAnalyticsService service = new SeanceAnalyticsService(analyticsRepo, seanceRepository);
        service.analyse(fileToAnalyse, seance.getId());
        return seance;
    }
}
