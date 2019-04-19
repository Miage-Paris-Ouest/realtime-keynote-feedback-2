package miage.nanterre.m1app.realtimekeynote.controller;

import miage.nanterre.m1app.realtimekeynote.model.Seance;
import miage.nanterre.m1app.realtimekeynote.model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.model.User;
import miage.nanterre.m1app.realtimekeynote.repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/seance")
public class SeanceController {

    private SeanceAnalyticsRepository analyticsRepository;
    private UserRepository userRepository;

    public SeanceController(SeanceAnalyticsRepository analyticsRepository, UserRepository userRepository) {
        this.analyticsRepository = analyticsRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Seance> getSeanceByUser(@RequestParam("userId") long userId) {
        User user = userRepository.findById(userId).get();
        return user.getSeances();
    }

}
