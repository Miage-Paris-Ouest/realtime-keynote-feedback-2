package miage.nanterre.m1app.realtimekeynote.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import miage.nanterre.m1app.realtimekeynote.DAO.UserDAO;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.UserRepository;
import miage.nanterre.m1app.realtimekeynote.helpers.AnalyticsHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/analytics")
public class SeanceAnalyticsController {

    private SeanceAnalyticsRepository analyticsRepository;

    public SeanceAnalyticsController(SeanceAnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    private double calculateAverageAttention(double[] analyticsData) {
        if (analyticsData.length == 0) {
            return Double.parseDouble(null);
        }

        return Arrays.stream(analyticsData).sum();
    }

    @RequestMapping(value = "/get/dashboard", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> sendAnalyticsData() {

        ArrayList<SeanceAnalytics> analytics = (ArrayList<SeanceAnalytics>) analyticsRepository.findAll();
        ObjectMapper mapper = new ObjectMapper();
        int months[] = AnalyticsHelper.getSixLastMonthsLabels();

        //double[] analyticsData = analytics.getAnalyticsData();
        // double average = this.calculateAverageAttention(analyticsData);
        /*HashMap<String,ArrayList<>>
        for(int i = 0 ; i < months.length; i++){
            HashMap<String,Integer> hash = new HashMap<String,Integer>();
            for(SeanceAnalytics analytic : analytics){
                Seance seance =  analytic.getSeance();
                java.sql.Date date =  seance.getDate();
                LocalDate localDate = date.toLocalDate();
                int month = localDate.getMonthValue();
                if(months[i] == month){
                    hash.put("absences", );
                }

            }
        }*/

        JsonArray response = new JsonArray();
        //response.add(average);
        try {
            response.add(mapper.writeValueAsString(months));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Object>(months, HttpStatus.OK);
    }

    @RequestMapping(value = "/get/data", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> sendAnalyticsData(@RequestParam("seanceId") long seanceId) {
        SeanceAnalytics analytics = analyticsRepository.findById(seanceId).get();
        ObjectMapper mapper = new ObjectMapper();

        double average = 12;

        JsonArray response = new JsonArray();
        response.add(average);
        try {
            response.add(mapper.writeValueAsString(analytics));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


}