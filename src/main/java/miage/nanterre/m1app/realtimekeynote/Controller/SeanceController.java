package miage.nanterre.m1app.realtimekeynote.controller;

import miage.nanterre.m1app.realtimekeynote.model.User;
import miage.nanterre.m1app.realtimekeynote.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/seance")
public class SeanceController {

    private UserRepository userRepository;

    public SeanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/all/user", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> getSeanceByUser(@RequestParam("userId") long userId) {
        User user = userRepository.findById(userId).get();
        return new ResponseEntity<Object>(user.getSeances(), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> getAllSeance() {
        return new ResponseEntity<Object>(userRepository.findAll(), HttpStatus.OK);
    }

}
