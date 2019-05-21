package miage.nanterre.m1app.realtimekeynote.Controller;

import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/seance")
public class SeanceController {

    private UserRepository userRepository;
    private SeanceRepository seanceRepository;

    public SeanceController(UserRepository userRepository, SeanceRepository seanceRepository) {
        this.userRepository = userRepository;
        this.seanceRepository = seanceRepository;
    }

    @RequestMapping(value = "/all/user/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> getSeanceByUser(@PathVariable("id") long userId) {
        User user = userRepository.findById(userId).get();
        return new ResponseEntity<Object>(user.getSeances(), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> getAllSeance() {
        return new ResponseEntity<>(seanceRepository.findAll(), HttpStatus.OK);
    }

}
