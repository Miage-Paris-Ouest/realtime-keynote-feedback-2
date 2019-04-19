package miage.nanterre.m1app.realtimekeynote.DAO;

import miage.nanterre.m1app.realtimekeynote.model.User;
import miage.nanterre.m1app.realtimekeynote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserDAO {

    @Autowired
    private UserRepository repository;

    public UserDAO(UserRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/list")
    public List<User> getAllUsers() {
        return (List<User>) repository.findAll();
    }

    @RequestMapping("/add")
    public void createUser() {

    }

    @RequestMapping("/delete/userId")
    public void deleteUser(@RequestParam("userId") long userId) {
        repository.deleteById(userId);
    }

    @RequestMapping("/update/userId")
    public User updateUser(@RequestParam("userId") long userId) {
        User user = repository.findById(userId).get();
        user.setName("blablabla");
        repository.save(user);
        return user;
    }

}
