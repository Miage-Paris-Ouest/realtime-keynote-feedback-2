package miage.nanterre.m1app.realtimekeynote.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Seance seance;

    @Column(nullable = true, unique = false)
    private double[] analyticsData;

    public SeanceAnalytics() {
    }

    public SeanceAnalytics(Seance seance, double[] analyticsData) {
        this.seance = seance;
        this.analyticsData = analyticsData;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeanceAnalytics that = (SeanceAnalytics) o;
        return id == that.id &&
                Objects.equals(seance, that.seance) &&
                Arrays.equals(analyticsData, that.analyticsData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, seance);
        result = 31 * result + Arrays.hashCode(analyticsData);
        return result;
    }
}