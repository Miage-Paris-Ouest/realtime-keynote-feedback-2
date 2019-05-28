package miage.nanterre.m1app.realtimekeynote.Service;

import miage.nanterre.m1app.realtimekeynote.Exception.AnalyticsException;
import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.helpers.DateHelper;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static miage.nanterre.m1app.realtimekeynote.Enum.SeanceAnalyticsEnum.*;
import static miage.nanterre.m1app.realtimekeynote.Enum.SeanceEnum.*;

public class SeanceAnalyticsService {
    public final static int MAX_ATTENTION = 50;
    public final static String STATISTICS_SEPARATOR = ",";

    private SeanceAnalyticsRepository analyticsRepository;
    private SeanceRepository seanceRepository;

    public SeanceAnalyticsService(SeanceAnalyticsRepository analyticsRepository, SeanceRepository seanceRepository) {
        this.analyticsRepository = analyticsRepository;
        this.seanceRepository = seanceRepository;
    }


    public static HashMap<String, Object> getDashboardStatistics(SeanceAnalyticsRepository analyticsRepository)
            throws AnalyticsException {

        HashMap<String, Object> response = new HashMap<String, Object>();
        ArrayList<LocalDate> months = getSixLastMonthsLabels();
        ArrayList<SeanceAnalytics> analytics = (ArrayList<SeanceAnalytics>) analyticsRepository.findAll();
        HashMap<LocalDate, ArrayList> sessionsPerMonths = getSessionsPerMonths(analytics, months);
        ArrayList<Double> AvgPerMonthList = new ArrayList<Double>();
        ArrayList<Double> AttentionPerMonthList = new ArrayList<Double>();
        ArrayList<Double> AbsentPerMonthList = new ArrayList<Double>();

        for (LocalDate date : months) {
            ArrayList sessions = sessionsPerMonths.get(date);
            AvgPerMonthList.add(getAttentionAveragePerMonth(sessions));
            AttentionPerMonthList.add(getAttentionDiffPerMonth(sessions));
            AbsentPerMonthList.add(getAbsentAveragePerMonth(sessions));
        }

        response.put(String.valueOf(MONTHS), months);
        response.put(String.valueOf(ATTENTION_AVG_PER_MONTH), AvgPerMonthList);
        response.put(String.valueOf(ATTENTION_DIFF_PER_MONTH), AttentionPerMonthList);
        response.put(String.valueOf(ABSENT_AVG_PER_MONTH), AbsentPerMonthList);
        response.put(String.valueOf(ATTENTION_AVG), AvgPerMonthList
                .stream()
                .reduce(0., (a, b) -> a + b)
                / AvgPerMonthList.size());

        return response;
    }

    public static HashMap<String, Object> getSessionStatistics(SeanceRepository seanceRepository, long sessionId)
            throws AnalyticsException {

        HashMap<String, Object> response = new HashMap<String, Object>();
        Seance seance = seanceRepository.findById(sessionId).get();

        if (seance != null && seance.getSeanceAnalytics().getAnalyticsData() != null) {
            HashMap<String, Object> seanceData = new HashMap<String, Object>();
            seanceData.put(String.valueOf(SUBJECT), seance.getSubject());
            seanceData.put(String.valueOf(PUBLIC), seance.getPubliq());
            seanceData.put(String.valueOf(ROOM), seance.getRoom());
            seanceData.put(String.valueOf(PARTICIPANTS), seance.getParticipants());
            seanceData.put(String.valueOf(DATE), seance.getDate());
            seanceData.put(String.valueOf(BEGINNING_TIME), seance.getBeginningTime());
            seanceData.put(String.valueOf(ENDING_TIME), seance.getEndingTime());
            seanceData.put(String.valueOf(DURATION), SeanceService.CalcDuration(seance));

            int participants = seance.getParticipants();
            ArrayList<Integer> parsedAnalytics = parseAnalytics(seance.getSeanceAnalytics().getAnalyticsData());

            response.put(String.valueOf(ATTENTION_AVG), getAverageSessionAttention(participants, parsedAnalytics));
            response.put(String.valueOf(ATTENTION_MAX), getBestSessionAttention(participants, parsedAnalytics));
            response.put(String.valueOf(ATTENTION_MIN), getWorstSessionAttention(participants, parsedAnalytics));
            response.put(String.valueOf(SESSION), seanceData);
            response.put(String.valueOf(SESSION_ANALYTICS_DATA), parseAnalyticsResume(seance,parsedAnalytics));
        } else {
            //throw new AnalyticsException("Aucune seance à analyser !");

        }

        return response;
    }

    private static double getAttentionAveragePerMonth(ArrayList<SeanceAnalytics> sessions) throws AnalyticsException {
       /* if (sessions.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");*/

        ArrayList<Double> collector = new ArrayList<Double>();
        for (SeanceAnalytics session : sessions) {
            collector.add(getAverageSessionAttention(session.getSeance().getParticipants(),
                    parseAnalytics(session.getAnalyticsData())));
        }
        return collector
                .stream()
                .reduce(0., (a, b) -> a + b)
                / collector.size();
    }

    private static double getAttentionDiffPerMonth(ArrayList<SeanceAnalytics> sessions) throws AnalyticsException {
       /* if (sessions.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");*/

        ArrayList<Double> collector = new ArrayList<Double>();
        for (SeanceAnalytics session : sessions) {
            collector.add(getBestSessionAttention(session.getSeance().getParticipants(),
                    parseAnalytics(session.getAnalyticsData()))
                    - getWorstSessionAttention(session.getSeance().getParticipants(),
                    parseAnalytics(session.getAnalyticsData())));
        }
        return (collector
                .stream()
                .reduce(0., (a, b) -> a + b) * 1.)
                / (collector.size() * 1.);
    }

    private static double getAbsentAveragePerMonth(ArrayList<SeanceAnalytics> sessions) throws AnalyticsException {
       /* if (sessions.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");*/

        ArrayList<Double> collector = new ArrayList<Double>();
        for (SeanceAnalytics session : sessions) {
            collector.add(getAverageAbsentParticipants(session.getSeance().getParticipants(),
                    parseAnalytics(session.getAnalyticsData()))
            );
        }
        return (collector
                .stream()
                .reduce(0., (a, b) -> a + b) * 1.)
                / (collector.size() * 1.);
    }

    public static double getAverageSessionAttention(int participants, ArrayList<Integer> dataAnalytics) {
       /* if (dataAnalytics.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");*/

        return (((dataAnalytics
                .stream()
                .reduce(0, (a, b) -> a + b) * 1.)
                / (dataAnalytics.size() * 1.))
                / (participants * 1.))
                * MAX_ATTENTION;
    }

    private static double getBestSessionAttention(int participants, ArrayList<Integer> dataAnalytics)
            throws AnalyticsException {
      /*  if (dataAnalytics.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");*/

        return ((Collections.max(dataAnalytics) * 1.)
                / (participants * 1.))
                * MAX_ATTENTION;
    }

    private static double getWorstSessionAttention(int participants, ArrayList<Integer> dataAnalytics)
            throws AnalyticsException {
       /* if (dataAnalytics.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");*/

        return ((Collections.min(dataAnalytics) * 1.)
                / (participants * 1.))
                * MAX_ATTENTION;
    }

    private static double getAverageAbsentParticipants(int participants, ArrayList<Integer> dataAnalytics)
            throws AnalyticsException {
       /* if (dataAnalytics.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");*/

        double data = participants * 1. -
                ((dataAnalytics.stream()
                        .reduce(0, (a, b) -> a + b) * 1.)
                        / (dataAnalytics.size() * 1.));
        return data;
    }

    private static HashMap<LocalDate, ArrayList> getSessionsPerMonths(ArrayList<SeanceAnalytics> analytics, ArrayList<LocalDate> months)
            throws AnalyticsException {
      /*  if (months.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");*/

        HashMap<LocalDate, ArrayList> datasets = new HashMap<LocalDate, ArrayList>();
        for (LocalDate date : months) {
            datasets.put(date, getSessionPerMonth(analytics, date));
        }
        return datasets;
    }

    private static ArrayList<LocalDate> getSixLastMonthsLabels() {
        LocalDate dateNow = LocalDate.now();
        ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
        dates.add(dateNow);
        dates.add(dateNow.minusMonths(1));
        dates.add(dateNow.minusMonths(2));
        dates.add(dateNow.minusMonths(3));
        dates.add(dateNow.minusMonths(4));
        dates.add(dateNow.minusMonths(5));
        Collections.reverse(dates);
        return dates;
    }

    private static ArrayList getSessionPerMonth(ArrayList<SeanceAnalytics> analytics, LocalDate date) {
        ArrayList sessions = new ArrayList();
        for (SeanceAnalytics analytic : analytics) {
            LocalDate current = DateHelper.asLocalDate(analytic.getSeance().getDate());
            if (current.getMonthValue() == date.getMonthValue() &&
                    current.getYear() == date.getYear()) {
                sessions.add(analytic);
            }
        }
        return sessions;
    }

    public static ArrayList<Integer> parseAnalytics(String analytics) {
        ArrayList<Integer> parsed = new ArrayList<Integer>();
        String[] parts = analytics.split(STATISTICS_SEPARATOR);
        for (String str : parts) {
            parsed.add(Integer.valueOf(str));
        }
        return parsed;
    }

    public static ArrayList<HashMap> parseAnalyticsResume(Seance seance, ArrayList<Integer> parsed) {
        ArrayList<HashMap> collector = new ArrayList<>();
        int count = 0;
        long durationMin = (seance.getEndingTime().getTime()
                - seance.getBeginningTime().getTime())
                / 1000
                / 60;
        long partsNumber = (long)Math.ceil(durationMin*1. / 15*1.);
        long frameByParts = parsed.size()/partsNumber;
        ArrayList<Double> part;
        HashMap datas = new HashMap();
        datas.put(String.valueOf(DATA),(double)25.);
        collector.add(datas);
        while (count < partsNumber) {
             part = new ArrayList<Double>();
            for (long i = count * frameByParts; i < ((count + 1) * frameByParts )
                    && i <parsed.size(); i++) {
                part.add(parsed.get((int) i) * 1.);
            }
            datas = new HashMap();
            double value = part.stream()
                    .reduce(0.,(a,b)-> (a+b))* 1.
                    /part.size()
                    /seance.getParticipants()
                    *MAX_ATTENTION;
            datas.put(String.valueOf(DATA),value);
            collector.add(datas);
            count++;
        }
        Date date = seance.getBeginningTime();
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        date.setTime(date.getTime()+3600000);
        for(int i =0 ; i < collector.size(); i++){
             collector.get(i).put(String.valueOf(LABEL),localDateFormat.format(date));
            date =  new Date(date.getTime()+60000*15);
        }
        collector.get(collector.size()-1).replace(String.valueOf(LABEL),seance.getEndingTime());
        return collector;
    }

    public  void analyse(String nom,long id) {

        String path ="C:\\data\\"+nom;
        String nb = "";

        String chemin = path.replace("~","\\");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        SeanceAnalytics s = this.seanceRepository.findById(id).get().getSeanceAnalytics();
        //Create new MAT object
        Mat frame = new Mat();
        //Create new VideoCapture object
        VideoCapture camera = new VideoCapture(chemin);
        String xmlFile = "XML\\lbpcascade_frontalface.xml";


        int batch=0 ;
        MatOfRect faceDetection = new MatOfRect();
        while (camera.read(frame)) {
            //If next video frame is available

            if (batch % 10 == 0 ) {
                if (camera.read(frame)) {
                    CascadeClassifier cc = new CascadeClassifier(xmlFile);
                    cc.detectMultiScale(frame, faceDetection);
                    nb = nb + "," + faceDetection.toArray().length;
                } else {
                    break;
                }
            }
        }

        s.setAnalyticsData(nb);
        analyticsRepository.save(s) ;

        System.out.println(nb);
    }
}
