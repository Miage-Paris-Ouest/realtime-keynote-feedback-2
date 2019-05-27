package miage.nanterre.m1app.realtimekeynote.Controller;

import miage.nanterre.m1app.realtimekeynote.Exception.AnalyticsException;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Service.SeanceAnalyticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/analytics")
public class SeanceAnalyticsController {

    private SeanceAnalyticsRepository analyticsRepository;
    private SeanceRepository seanceRepository;

    public SeanceAnalyticsController(SeanceAnalyticsRepository analyticsRepository, SeanceRepository seanceRepository) {
        this.analyticsRepository = analyticsRepository;
        this.seanceRepository = seanceRepository;
    }

    @RequestMapping(value = "/get/dashboard", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> sendAnalyticsData() throws AnalyticsException {
         HashMap<String, Object> response = SeanceAnalyticsService.getDashboardStatistics(analyticsRepository);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/get/data", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> sendAnalyticsData(@RequestParam("id") long id) throws AnalyticsException {
        HashMap<String, Object> response = SeanceAnalyticsService.getSessionStatistics(seanceRepository,id);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


}