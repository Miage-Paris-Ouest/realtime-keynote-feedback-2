package miage.nanterre.m1app.realtimekeynote.Service;

import com.mysql.cj.x.protobuf.MysqlxExpr;
import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.helpers.DateHelper;
import org.hibernate.type.DateType;

import java.time.LocalDate;
import java.util.*;

public class SeanceAnalyticsService {
    // attention maximum
    private static final int MAX_ATTENTION = 50;
    //separator
    private static final String STATISTICS_SEPARATOR = ",";
    //dashboard data keys
    private static final String MONTHS_KEY = "months";
    private static final String ATTENTION_AVG_PER_MONTH_KEY = "attentionAvgPerMonth";
    private static final String ATTENTION_DIFF_PER_MONTH_KEY = "attentionDiffPerMonth";
    private static final String ABSENT_AVG_PER_MONTH_KEY = "absentAvgPerMonth";
    private static final String ATTENTION_AVG_KEY = "attentionAverage";
    //Session analytics data keys
    private static final String ATTENTION_MAX_KEY = "attentionMax";
    private static final String ATTENTION_MIN_KEY = "attentionMin";
    private static final String SESSION_KEY = "session";
    private static final String SESSION_ANALYTICS_DATA = "analyticsData";



    public static HashMap<String, Object> getDashboardStatistics(SeanceAnalyticsRepository analyticsRepository) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        ArrayList<LocalDate> months = getSixLastMonthsLabels();
        ArrayList<SeanceAnalytics> analytics  = (ArrayList<SeanceAnalytics>)analyticsRepository.findAll();
        HashMap<LocalDate, ArrayList> sessionsPerMonths = getSessionsPerMonths(analytics,months);
        ArrayList<Double> AvgPerMonthList = new ArrayList<Double>();
        ArrayList<Double> AttentionPerMonthList = new ArrayList<Double>();
        ArrayList<Double> AbsentPerMonthList = new ArrayList<Double>();

        for (LocalDate date : months) {
            ArrayList sessions = sessionsPerMonths.get(date);
            AvgPerMonthList.add(getAttentionAveragePerMonth(sessions));
            AttentionPerMonthList.add(getAttentionDiffPerMonth(sessions));
            AbsentPerMonthList.add(getAbsentAveragePerMonth(sessions));
        }

        response.put(MONTHS_KEY, months);
        response.put(ATTENTION_AVG_PER_MONTH_KEY, AvgPerMonthList);
        response.put(ATTENTION_DIFF_PER_MONTH_KEY, AttentionPerMonthList);
        response.put(ABSENT_AVG_PER_MONTH_KEY, AbsentPerMonthList);
        response.put(ATTENTION_AVG_KEY, AvgPerMonthList
                .stream()
                .reduce(0., (a, b) -> a + b)
                / AvgPerMonthList.size());

        return response;
    }

    public static HashMap<String,Object> getSessionStatistics(SeanceRepository seanceRepository, long sessionId){
        HashMap<String, Object> response = new HashMap<String, Object>();
        Seance seance = seanceRepository.findById(sessionId).get();
        if(seance != null) {
            
            HashMap<String,Object> seanceData  = new HashMap<String,Object>();
            seanceData.put("subject",seance.getSubject());
            seanceData.put("public",seance.getPubliq());
            seanceData.put("room", seance.getRoom());
            seanceData.put("participants",seance.getParticipants());
            seanceData.put("date",seance.getDate());
            seanceData.put("begginingTime",seance.getBeginningTime());
            seanceData.put("endingTime",seance.getEndingTime());
            response.put(ATTENTION_AVG_KEY, getAverageSessionAttention(seance.getParticipants(),
                    parseAnalytics(seance.getSeanceAnalytics().getAnalyticsData())));
            response.put(ATTENTION_MAX_KEY, getBestSessionAttention(seance.getParticipants(),
                    parseAnalytics(seance.getSeanceAnalytics().getAnalyticsData())));
            response.put(ATTENTION_MIN_KEY, getWorstSessionAttention(seance.getParticipants(),
                    parseAnalytics(seance.getSeanceAnalytics().getAnalyticsData())));
            response.put(SESSION_KEY, seanceData);
            response.put(SESSION_ANALYTICS_DATA, seance.getSeanceAnalytics().getAnalyticsData());
            return response;
        }
        else{
            return response;
        }
    }

    private static double getAttentionAveragePerMonth(ArrayList<SeanceAnalytics> sessions) {
        ArrayList<Double> collector = new ArrayList<Double>();
        for (SeanceAnalytics session : sessions) {
            collector.add(getAverageSessionAttention(session.getSeance().getParticipants(),
                    parseAnalytics(session.getAnalyticsData())));
        }
        return collector
                .stream()
                .reduce(0., (a, b) -> a + b) / collector.size();
    }

    private static double getAttentionDiffPerMonth(ArrayList<SeanceAnalytics> sessions) {
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

    private static double getAbsentAveragePerMonth(ArrayList<SeanceAnalytics> sessions) {
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

    private static double getAverageSessionAttention(int participants, ArrayList<Integer> dataAnalytics) {
        return (((dataAnalytics
                .stream()
                .reduce(0, (a, b) -> a + b) * 1.)
                / (dataAnalytics.size() * 1.))
                / (participants * 1.)) * MAX_ATTENTION;
    }

    private static double getBestSessionAttention(int participants, ArrayList<Integer> dataAnalytics) {
        return ((Collections.max(dataAnalytics)*1.)
                /(participants*1.)) * MAX_ATTENTION;
    }

    private static double getWorstSessionAttention(int participants, ArrayList<Integer> dataAnalytics) {
        return ((Collections.min(dataAnalytics)*1.)
                / (participants*1.)) * MAX_ATTENTION;
    }

    private static double getAverageAbsentParticipants(int participants, ArrayList<Integer> dataAnalytics) {
        double data = participants * 1. - ((dataAnalytics.stream().reduce(0, (a, b) -> a + b) * 1.)
                / (dataAnalytics.size() * 1.));
     return data;
    }

    private static HashMap<LocalDate, ArrayList> getSessionsPerMonths(ArrayList<SeanceAnalytics> analytics,ArrayList<LocalDate> months) {
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

    private static ArrayList<Integer> parseAnalytics(String analytics) {
        ArrayList<Integer> parsed = new ArrayList<Integer>();
        String[] parts = analytics.split(STATISTICS_SEPARATOR);
        for (String str : parts) {
            parsed.add(Integer.valueOf(str));
        }
        return parsed;
    }
}
