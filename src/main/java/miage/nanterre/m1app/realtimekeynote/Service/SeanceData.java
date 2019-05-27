package miage.nanterre.m1app.realtimekeynote.Service;

public enum SeanceData {

    SUBJECT("subject"),
    PUBLIC("public"),
    ROOM("room"),
    PARTICIPANTS("participants"),
    DATE("date"),
    BEGGINING_TIME("begginingTime"),
    ENDING_TIME("endingTime"),
    DURATION("duration");

    private final String value;

    SeanceData(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
