package miage.nanterre.m1app.realtimekeynote.Test.DAO;

import junit.framework.TestCase;
import miage.nanterre.m1app.realtimekeynote.DAO.SeanceDAO;
import miage.nanterre.m1app.realtimekeynote.Model.*;
import miage.nanterre.m1app.realtimekeynote.Repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
public class SeanceDAOTest extends TestCase {
    @Autowired
    private static SeanceRepository seanceRepository;

    @Autowired
    private static SeanceDAO seanceDAO = new SeanceDAO(seanceRepository);

    @Autowired
    private static UserRepository userRepository;

    @Autowired
    private static SeanceAnalyticsRepository seanceAnalyticsRepository;

    @Autowired
    private static User user;

    @Test
    public void create(){
        user = new User("Christelle","Ilunga",null);
        userRepository.save(user);

        Seance seance = new Seance("Test","2","Public",
                new Date(2019, 01, 01, 9, 0, 0),
                new Date(2019, 01, 01, 11, 0, 0),
                null, user, 50);

        seanceRepository.save(seance);
        seanceAnalyticsRepository.save(new SeanceAnalytics(seance, null));
        userRepository.findById((long)1).get();
    }

    @Test
    public void testDeleteSeance(){
        seanceDAO.deleteSeance(user.getId());
    }

    @Test
    public void testCreateManySeances(){
        for (int i = 1; i<13; i++){
            Seance seance = new Seance("Test"+i, String.valueOf(i),"Public",
                    new Date(2019, i, 10, 9, 00, 00),
                    new Date(2019, i, 10, 11, 00, 00),
                    "Seance du mois "+i, user, 50);
            seanceRepository.save(seance);
            seanceAnalyticsRepository.save(new SeanceAnalytics(seance, null));
        }
    }
/*
    @AfterClass
    public void tearDown() throws Exception {
        seanceRepository = null;
        seanceDAO = null;
        userRepository = null;
        seanceAnalyticsRepository = null;
        user = null;
    }*/
}
