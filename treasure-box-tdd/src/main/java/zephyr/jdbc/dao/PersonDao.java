package zephyr.jdbc.dao;

import zephyr.jdbc.model.Person;

import java.util.List;

/**
 * Created by zephyr on 2019-10-03.
 */
public interface PersonDao {

    Person find(Integer id);
    void save(Person person);
    void update(Person person);
    void delete(Person person);
    List<Person> findAll();
    List<Person> findByLastName(String lastName);

}
