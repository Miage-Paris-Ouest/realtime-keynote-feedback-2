package service;

import java.util.Hashtable;

import org.springframework.stereotype.Service;

import model.Person;

@Service
public class PersonService {
	Hashtable<String, Person> persons = new Hashtable<String,Person>();
	
	public PersonService() {
		persons.put("1", new Person("1","John","Doe",21));
		persons.put("2", new Person("2","Jane","Rover",26));
		persons.put("3", new Person("3","Jack","Jacker",31));
	}
	
	public Person getPerson(String id) {
		return persons.get(id);
	}
	
	public Hashtable<String, Person> getAll(){
		return persons;
	}
	
	

}
