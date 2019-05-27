package miage.nanterre.m1app.realtimekeynote.Service;

public enum SeanceAnalyticsData {

    // attention maximum
    MAX_ATTENTION("50"),
    //separator
    STATISTICS_SEPARATOR(","),
    //dashboard data keys
    MONTHS_KEY("months"),
    ATTENTION_AVG_PER_MONTH_KEY("attentionAvgPerMonth"),
    ATTENTION_DIFF_PER_MONTH_KEY("attentionDiffPerMonth"),
    ABSENT_AVG_PER_MONTH_KEY("absentAvgPerMonth"),
    ATTENTION_AVG_KEY("attentionAverage"),
    //Session analytics data keys
    ATTENTION_MAX_KEY("attentionMax"),
    ATTENTION_MIN_KEY("attentionMin"),
    SESSION_KEY("session"),
    SESSION_ANALYTICS_DATA("analyticsData");

    private final String value;

    SeanceAnalyticsData(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
