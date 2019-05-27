package miage.nanterre.m1app.realtimekeynote.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import miage.nanterre.m1app.realtimekeynote.DAO.UserDAO;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.UserRepository;
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
    public ResponseEntity<Object> sendAnalyticsData() {
         HashMap<String, Object> response = SeanceAnalyticsService.getDashboardStatistics(analyticsRepository);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/get/data", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> sendAnalyticsData(@RequestParam("id") long id) {
        HashMap<String, Object> response = SeanceAnalyticsService.getSessionStatistics(seanceRepository,id);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/analyse/{path}")

    public  String analyse(@PathVariable("path") String path     ) throws UnsupportedEncodingException {

        //String chemin ="C:\\Users\\amine\\Desktop\\VID_20190417_112105.mp4";
        String nb = "";
        String chemin = path.replace("~","\\");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //Create new MAT object
        Mat frame = new Mat();
        //Create new VideoCapture object
        VideoCapture camera = new VideoCapture(chemin);
        String xmlFile = "XML\\lbpcascade_frontalface.xml";


        int batch=0 ;
        MatOfRect faceDetection = new MatOfRect();
        while (camera.read(frame)) {
            //If next video frame is available

            if (batch % 10 == 0 )
            if (camera.read(frame)) {


                CascadeClassifier cc = new CascadeClassifier(xmlFile);


                cc.detectMultiScale(frame, faceDetection);

                nb=nb+","+faceDetection.toArray().length;


            }else {break;}

        }



        System.out.println(nb);
        return "susscess";

    }



}