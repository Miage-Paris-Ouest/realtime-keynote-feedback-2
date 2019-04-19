package miage.nanterre.m1app.realtimekeynote.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @Column(name = "public", length = 60, nullable = false)
    private String publiq;

    @Column(name="beginning_time", length = 60, nullable = true)
    private Date beginningTime;

    @Column(name = "ending_time",length = 60, nullable = true)
    private Date endingTime;

    @Column(length = 60, nullable = true)
    private Date description;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id", nullable = false)
    private SeanceAnalytics seanceAnalytics;

    @ManyToOne
    @JoinColumn(name="user", nullable=false)
    private User user;

    @Column(nullable = false)
    private int participants;

    public Seance() {
    }

    public Seance(String subject, String room, String publiq, Date beginningTime, Date endingTime, Date description, SeanceAnalytics seanceAnalytics, User user, int participants) {
        this.subject = subject;
        this.room = room;
        this.publiq = publiq;
        this.beginningTime = beginningTime;
        this.endingTime = endingTime;
        this.description = description;
        this.seanceAnalytics = seanceAnalytics;
        this.user = user;
        this.participants = participants;

        SeanceAnalytics analytics = new SeanceAnalytics();
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

    public SeanceAnalytics getSeanceAnalytics() {
        return seanceAnalytics;
    }

    public void setSeanceAnalytics(SeanceAnalytics seanceAnalytics) {
        this.seanceAnalytics = seanceAnalytics;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }
}
