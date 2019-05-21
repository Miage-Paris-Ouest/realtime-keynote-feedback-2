package DAO;


import miage.nanterre.m1app.realtimekeynote.DAO.SeanceDAO;
import miage.nanterre.m1app.realtimekeynote.DAO.UserDAO;
import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;

import org.junit.Before;
import org.junit.Test;

public class UserDAOTest {

    SeanceRepository seanceRepository;
    SeanceDAO seanceDAO;
    UserDAO userDAO;


    @Before
    public void setUp() throws Exception {
        seanceDAO = new SeanceDAO(seanceRepository);
    }
    @Test
    public void testAddUsers(){
        User user = new User("Christelle","Ilunga",null);
        userDAO.createUser(user);
    }

    @Test
    public void testGetAllUsers(){
        System.out.println("Users : "+userDAO.getAllUsers());
    }

    @Test
    public void testDeleteUsers(){
        userDAO.deleteUser(1);
    }

    @Test
    public void testUpdateUsers(){
        userDAO.updateUser(2);
    }


}
