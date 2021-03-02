package zephyr.tdd.jdbc.dao;

import zephyr.tdd.jdbc.model.Person;

import java.util.List;


public interface PersonDao {

    Person find(Integer id);
    void save(Person person);
    void update(Person person);
    void delete(Person person);
    List<Person> findAll();
    List<Person> findByLastName(String lastName);

}
