package zephyr.tdd.mybatis.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import zephyr.tdd.mybatis.model.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@MybatisTest
@ActiveProfiles("unit")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.NONE)
@Slf4j
public class TestMybatis {

    @Autowired
    private PersonDao personDao;

    @Test
    public void query() {
        Person person = personDao.find(1);
        log.info("{}", person);
        assertEquals(1, person.getId());
    }

}
