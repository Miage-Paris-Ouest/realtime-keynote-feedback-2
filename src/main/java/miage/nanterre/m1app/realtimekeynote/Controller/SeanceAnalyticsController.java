package miage.nanterre.m1app.realtimekeynote.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

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

    @RequestMapping(value = "/get/data", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> sendAnalyticsData(@RequestParam("seanceId") long seanceId) {
        SeanceAnalytics analytics = analyticsRepository.findById(seanceId).get();
        ObjectMapper mapper = new ObjectMapper();

        double[] analyticsData = analytics.getAnalyticsData();
        double average = this.calculateAverageAttention(analyticsData);

        JsonArray response = new JsonArray();
        response.add(average);
        try {
            response.add(mapper.writeValueAsString(analytics));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    public void EvalAttention (){
        //Les statistiques des 6 derniers mois dans la base
            //Requete
    }


}

