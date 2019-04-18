package miage.nanterre.m1app.realtimekeynote.DAO;

import miage.nanterre.m1app.realtimekeynote.model.Seance;
import miage.nanterre.m1app.realtimekeynote.repository.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seance")
public class SeanceDAO {

    @Autowired
    private SeanceRepository repository;

    @RequestMapping("/list")
    public Iterable<Seance> getAllSeance() {
        return repository.findAll();
    }

    @RequestMapping("/get/sceanceId")
    public Seance getSeance(@RequestParam("seanceId") long seanceId) {
        return repository.findById(seanceId).get();
    }

    @RequestMapping("/get/delete")
    public void deleteSeance(@RequestParam("seanceId") long seanceId) {
        repository.deleteById(seanceId);
    }
}
