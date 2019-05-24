package miage.nanterre.m1app.realtimekeynote.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

public class AnalyticsHelper {

    private static final int MAX_ATTENTION = 50;

    public static double getAverageSeanceAttention(int participants, ArrayList<Integer> dataAnalytics) {
        return ((dataAnalytics.stream().reduce(0, (a, b) -> a + b)
                / dataAnalytics.size()) / participants) * MAX_ATTENTION;
    }

    public static double getBestSeanceAttention(int participants, ArrayList<Integer> dataAnalytics) {
        return (Collections.max(dataAnalytics)
                / participants) * MAX_ATTENTION;
    }

    public static double getWorstSeanceAttention(int participants, ArrayList<Integer> dataAnalytics) {
        return (Collections.min(dataAnalytics)
                / participants) * MAX_ATTENTION;
    }

    public static double getAverageMissingParticipants(int participants, ArrayList<Integer> dataAnalytics) {
       double data  = participants - (dataAnalytics.stream().reduce(0, (a, b) -> a + b)
                / dataAnalytics.size());
         if(data > 0 ) return data;
         else return 0;
    }

    public static int[] getSixLastMonthsLabels() {
        int[] months = {1, 2, 3, 4, 5, 6};
        return months;
    }
}
