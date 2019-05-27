package miage.nanterre.m1app.realtimekeynote.Service;

import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;

import java.util.ArrayList;
import java.util.HashMap;

import static miage.nanterre.m1app.realtimekeynote.Enum.SeanceEnum.*;
import static miage.nanterre.m1app.realtimekeynote.Enum.SeanceAnalyticsEnum.*;


public class SeanceService {

    public static ArrayList<HashMap> getDurationSession(SeanceRepository seanceRepository) {

        ArrayList<HashMap> response = new ArrayList<>();
        Iterable<Seance> seance = seanceRepository.findAll();

        for (Seance s : seance)
            if (s != null) {
                HashMap<String, Object> seanceData = new HashMap<String, Object>();
                seanceData.put(String.valueOf(SUBJECT), s.getSubject());
                seanceData.put(String.valueOf(PUBLIC), s.getPubliq());
                seanceData.put(String.valueOf(ROOM), s.getRoom());
                seanceData.put(String.valueOf(PARTICIPANTS), s.getParticipants());
                seanceData.put(String.valueOf(DATE), s.getDate());
                seanceData.put(String.valueOf(BEGINNING_TIME), s.getBeginningTime());
                seanceData.put(String.valueOf(ENDING_TIME), s.getEndingTime());
                seanceData.put(String.valueOf(ID), s.getId());
                seanceData.put(String.valueOf(ATTENTION_AVG),
                        SeanceAnalyticsService.getAverageSessionAttention(s.getParticipants(),
                                SeanceAnalyticsService.parseAnalytics(s.getSeanceAnalytics().getAnalyticsData())));

                long duration = s.getEndingTime().getTime() - s.getBeginningTime().getTime();

                int hours = Math.toIntExact(duration / 1000 / 60 / 60);
                int min = Math.toIntExact((duration / 1000 / 60) - hours * 60);

                seanceData.put(String.valueOf(DURATION), CalcDuration(s));
                seanceData.put(String.valueOf(DURATION), CalcDuration(s));
                response.add(seanceData);
            }
        return response;
    }

    public static String CalcDuration(Seance seance) {
        long duration = seance.getEndingTime().getTime() - seance.getBeginningTime().getTime();
        int hours = Math.toIntExact(duration / 1000 / 60 / 60);
        int min = Math.toIntExact((duration / 1000 / 60) - hours * 60);

        return hours + ":" + min;
    }

}
