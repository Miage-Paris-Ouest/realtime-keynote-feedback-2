package miage.nanterre.m1app.realtimekeynote.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table
public class Seance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false, unique = true)
    private long id;

    @Column(length = 60, nullable = false)
    private String subject;

    @Column(length = 60, nullable = false)
    private String room;

    @Column(length = 60, nullable = false)
    private String publiq;

    @Column(length = 60, nullable = true)
    private Date beginningTime;

    @Column(length = 60, nullable = true)
    private Date endingTime;

    @Column(length = 60, nullable = true)
    private Date description;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "seanceId")
    private SeanceAnalytics seanceAnalyticsId;

    @Column(nullable = false)
    private int participants;


    public Seance() {
    }

    public Seance(String subject, String room, String publiq, Date beginningTime, Date endingTime, Date description, SeanceAnalytics seanceAnalyticsId, int participants) {
        this.subject = subject;
        this.room = room;
        this.publiq = publiq;
        this.beginningTime = beginningTime;
        this.endingTime = endingTime;
        this.description = description;
        this.seanceAnalyticsId = seanceAnalyticsId;
        this.participants = participants;
    }

    public long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPubliq() {
        return publiq;
    }

    public void setPubliq(String publiq) {
        this.publiq = publiq;
    }

    public Date getBeginningTime() {
        return beginningTime;
    }

    public void setBeginningTime(Date beginningTime) {
        this.beginningTime = beginningTime;
    }

    public Date getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(Date endingTime) {
        this.endingTime = endingTime;
    }

    public Date getDescription() {
        return description;
    }

    public void setDescription(Date description) {
        this.description = description;
    }

    public SeanceAnalytics getSeanceAnalyticsId() {
        return seanceAnalyticsId;
    }

    public void setSeanceAnalyticsId(SeanceAnalytics seanceAnalyticsId) {
        this.seanceAnalyticsId = seanceAnalyticsId;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seance seance = (Seance) o;
        return id == seance.id &&
                seanceAnalyticsId == seance.seanceAnalyticsId &&
                participants == seance.participants &&
                Objects.equals(subject, seance.subject) &&
                Objects.equals(room, seance.room) &&
                Objects.equals(publiq, seance.publiq) &&
                Objects.equals(beginningTime, seance.beginningTime) &&
                Objects.equals(endingTime, seance.endingTime) &&
                Objects.equals(description, seance.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, room, publiq, beginningTime, endingTime, description, seanceAnalyticsId, participants);
    }
}
