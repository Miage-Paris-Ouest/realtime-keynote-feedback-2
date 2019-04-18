package miage.nanterre.m1app.realtimekeynote.controller;

import miage.nanterre.m1app.realtimekeynote.model.User;
import miage.nanterre.m1app.realtimekeynote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/")
    public String index() {
        return "hello world";
    }
}