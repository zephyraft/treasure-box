package zephyr.tdd.mybatis.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import zephyr.tdd.mybatis.model.Person;

import java.util.List;


@Repository
@Mapper
public interface PersonDao {

    // language=SQL
    @Select("SELECT * FROM person WHERE id = #{id}")
    Person find(Integer id);
    void save(Person person);
    void update(Person person);
    void delete(Person person);
    List<Person> findAll();
    List<Person> findByLastName(String lastName);

}
