package miage.nanterre.m1app.realtimekeynote.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table
public class SeanceAnalytics implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false, unique = true)
    private long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false, mappedBy = "seanceAnalytics")
    @JoinColumn(name="id")
    private Seance seance;

    @Column(nullable = true, unique = false)
    private double[] analyticsData;

    @Column(nullable = true, unique = false)
    private double averageAttention;

    public SeanceAnalytics() {
    }

    public SeanceAnalytics(Seance seance, double[] analyticsData, double averageAttention) {
        this.seance = seance;
        this.analyticsData = analyticsData;
        this.averageAttention = averageAttention;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public double[] getAnalyticsData() {
        return analyticsData;
    }

    public void setAnalyticsData(double[] analyticsData) {
        this.analyticsData = analyticsData;
    }

    public double getAverageAttention() {
        return averageAttention;
    }

    public void setAverageAttention(double averageAttention) {
        this.averageAttention = averageAttention;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeanceAnalytics that = (SeanceAnalytics) o;
        return id == that.id &&
                Double.compare(that.averageAttention, averageAttention) == 0 &&
                Objects.equals(seance, that.seance) &&
                Arrays.equals(analyticsData, that.analyticsData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, seance, averageAttention);
        result = 31 * result + Arrays.hashCode(analyticsData);
        return result;
    }
}