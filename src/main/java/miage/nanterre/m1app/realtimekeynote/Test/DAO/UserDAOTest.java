package DAO;

import miage.nanterre.m1app.realtimekeynote.DAO.UserDAO;
import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserDAOTest {

    @Autowired
    private static UserRepository userRepository;
    private static UserDAO userDAO = new UserDAO(userRepository);

    @Autowired
    private static User user = new User("Christelle","Ilunga",null);;

    @Test
    public void testAddUsers(){
        userDAO.createUser(user);
    }

    @Test
    public void testGetAllUsers(){
        System.out.println("Users : "+userDAO.getAllUsers());
    }

    @Test
    public void testDeleteUsers(){
        userDAO.deleteUser(user.getId());
    }

    @Test
    public void testUpdateUsers(){
        userDAO.updateUser(2);
    }


}
