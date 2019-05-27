package miage.nanterre.m1app.realtimekeynote.Service;

import miage.nanterre.m1app.realtimekeynote.Exception.AnalyticsException;
import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.helpers.DateHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static miage.nanterre.m1app.realtimekeynote.Service.SeanceAnalyticsData.*;
import static miage.nanterre.m1app.realtimekeynote.Service.SeanceData.*;

public class SeanceAnalyticsService {


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
                if (months.isEmpty())
                    throw new AnalyticsException("Aucune session à analyser !");

                ArrayList sessions = sessionsPerMonths.get(date);
                AvgPerMonthList.add(getAttentionAveragePerMonth(sessions));
                AttentionPerMonthList.add(getAttentionDiffPerMonth(sessions));
                AbsentPerMonthList.add(getAbsentAveragePerMonth(sessions));
            }

        response.put(String.valueOf(MONTHS_KEY), months);
        response.put(String.valueOf(ATTENTION_AVG_PER_MONTH_KEY), AvgPerMonthList);
        response.put(String.valueOf(ATTENTION_DIFF_PER_MONTH_KEY), AttentionPerMonthList);
        response.put(String.valueOf(ABSENT_AVG_PER_MONTH_KEY), AbsentPerMonthList);
        response.put(String.valueOf(ATTENTION_AVG_KEY), AvgPerMonthList
                .stream()
                .reduce(0., (a, b) -> a + b)
                / AvgPerMonthList.size());

        return response;
    }

    public static HashMap<String, Object> getSessionStatistics(SeanceRepository seanceRepository, long sessionId)
            throws AnalyticsException {

        HashMap<String, Object> response = new HashMap<String, Object>();
        Seance seance = seanceRepository.findById(sessionId).get();

        if (seance != null) {
            HashMap<String, Object> seanceData = new HashMap<String, Object>();
            seanceData.put(String.valueOf(SUBJECT), seance.getSubject());
            seanceData.put(String.valueOf(PUBLIC), seance.getPubliq());
            seanceData.put(String.valueOf(ROOM), seance.getRoom());
            seanceData.put(String.valueOf(PARTICIPANTS), seance.getParticipants());
            seanceData.put(String.valueOf(DATE), seance.getDate());
            seanceData.put(String.valueOf(BEGGINING_TIME), seance.getBeginningTime());
            seanceData.put(String.valueOf(ENDING_TIME), seance.getEndingTime());

            int participants = seance.getParticipants();
            ArrayList<Integer> parsedAnalytics = parseAnalytics(seance.getSeanceAnalytics().getAnalyticsData());

            response.put(String.valueOf(ATTENTION_AVG_KEY), getAverageSessionAttention(participants,parsedAnalytics ));
            response.put(String.valueOf(ATTENTION_MAX_KEY), getBestSessionAttention(participants,parsedAnalytics ));
            response.put(String.valueOf(ATTENTION_MIN_KEY), getWorstSessionAttention(participants,parsedAnalytics ));
            response.put(String.valueOf(SESSION_KEY), seanceData);
            response.put(String.valueOf(SESSION_ANALYTICS_DATA), parsedAnalytics);
        } else {
            throw new AnalyticsException("Aucune seance à analyser !");
        }

        return response;
    }

    private static double getAttentionAveragePerMonth(ArrayList<SeanceAnalytics> sessions) throws AnalyticsException {
        if (sessions.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");

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
        if (sessions.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");

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
        if (sessions.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");

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

    private static double getAverageSessionAttention(int participants, ArrayList<Integer> dataAnalytics)
            throws AnalyticsException {
        if (dataAnalytics.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");

        return (((dataAnalytics
                .stream()
                .reduce(0, (a, b) -> a + b) * 1.)
                / (dataAnalytics.size() * 1.))
                / (participants * 1.))
                * Integer.parseInt(String.valueOf(MAX_ATTENTION));
    }

    private static double getBestSessionAttention(int participants, ArrayList<Integer> dataAnalytics)
            throws AnalyticsException {
        if (dataAnalytics.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");

        return ((Collections.max(dataAnalytics) * 1.)
                / (participants * 1.))
                * Integer.parseInt(String.valueOf(MAX_ATTENTION));
    }

    private static double getWorstSessionAttention(int participants, ArrayList<Integer> dataAnalytics)
            throws AnalyticsException {
        if (dataAnalytics.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");

        return ((Collections.min(dataAnalytics) * 1.)
                / (participants * 1.))
                * Integer.parseInt(String.valueOf(MAX_ATTENTION));
    }

    private static double getAverageAbsentParticipants(int participants, ArrayList<Integer> dataAnalytics)
            throws AnalyticsException {
        if (dataAnalytics.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");

        double data = participants * 1. -
                ((dataAnalytics.stream()
                        .reduce(0, (a, b) -> a + b) * 1.)
                        / (dataAnalytics.size() * 1.));
        return data;
    }

    private static HashMap<LocalDate, ArrayList> getSessionsPerMonths(ArrayList<SeanceAnalytics> analytics, ArrayList<LocalDate> months)
            throws AnalyticsException{
        if (months.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");

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
        String[] parts = analytics.split(String.valueOf(STATISTICS_SEPARATOR));
        for (String str : parts) {
            parsed.add(Integer.valueOf(str));
        }
        return parsed;
    }
}
