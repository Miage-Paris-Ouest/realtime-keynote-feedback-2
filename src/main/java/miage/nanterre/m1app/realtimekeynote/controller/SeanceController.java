package miage.nanterre.m1app.realtimekeynote.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeanceController {

    @RequestMapping(value = "/analytics/data/seanceId",produces = "application/json", method = RequestMethod.GET)
    public void getAnalyticsData(@RequestParam("seanceId") long seanceId) {

    }

}
