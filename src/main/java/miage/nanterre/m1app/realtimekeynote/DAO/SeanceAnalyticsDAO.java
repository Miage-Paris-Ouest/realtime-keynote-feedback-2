package miage.nanterre.m1app.realtimekeynote.DAO;

import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeanceAnalyticsDAO {

    @Query("SELECT analytics_data, FROM 'seance_analytics' AS 'sa' 'seance' AS 's' " +
            "WHERE 'seance.id = sa.id' AND 's.beginning_time = 0' "
            , nativeQuery=true)
    public List<Double> EvalAttention() {
        List<SeanceAnalytics> Analytics = new ArrayList<>();
        List<Double> evol = new ArrayList<>();

        // Boucle pour parcourir tout les resultats de la requete.

        /* On récupère une le resultat de la requete
        Pour chaque mois on fait la moyenne de'attention et on ajoute le resultat dans evol.
        L'indice de l'element correspond à sont moi. Le mois le plus ancien sera à 0 et le plus recent à 5*/
        SeanceAnalytics sa = new SeanceAnalytics();
        // Faire le set des données et
        //Calcule de la moyenne
        evol.add(sa.calcMoyenne());

        return evol;
    }

    @Query("SELECT analytics_data, FROM 'seance_analytics' AS 'sa' 'seance' AS 's' " +
            "WHERE 'seance.id = sa.id' AND 's.beginning_time = 0' "
            , nativeQuery=true)
    public List<Double> stabAttention(){
        List<SeanceAnalytics> Analytics = new ArrayList<>();
        List<Double> att = new ArrayList<>();

        SeanceAnalytics sa = new SeanceAnalytics();

        att.add(sa.stabAttention());

        return att;
    }

    @Query("SELECT 'analytics_data', FROM 'seance_analytics' AS 'sa' 'seance' AS 's' " +
            "WHERE 'seance.id = sa.id' AND 's.beginning_time = 0'"
            , nativeQuery=true)
    public List<Double> absMoyenne(){
        List<SeanceAnalytics> Analytics = new ArrayList<>();
        List<Double> stab = new ArrayList<>();

        int nbPart = 0;
        //Recuperer le nombre de participant

        SeanceAnalytics sa = new SeanceAnalytics();

        stab.add(sa.absMoyenne(nbPart));

        return stab;
    }
}
