package miage.nanterre.m1app.realtimekeynote.repository;

import miage.nanterre.m1app.realtimekeynote.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
