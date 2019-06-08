package miage.nanterre.m1app.realtimekeynote.Controller;

import miage.nanterre.m1app.realtimekeynote.Exception.AnalyticsException;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Service.SeanceAnalyticsService;
import miage.nanterre.m1app.realtimekeynote.helpers.AnalyticsHelper;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/get/dashboard", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> sendAnalyticsData() throws AnalyticsException {
         HashMap<String, Object> response = SeanceAnalyticsService.getDashboardStatistics(analyticsRepository);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/get/data", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> sendAnalyticsData(@RequestParam("id") long id) throws AnalyticsException {
        HashMap<String, Object> response = SeanceAnalyticsService.getSessionStatistics(seanceRepository,id);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


}