package zephyr.jdbc.dao;

import lombok.extern.slf4j.Slf4j;
import zephyr.jdbc.model.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zephyr on 2019-10-03.
 */
@Slf4j
public class JdbcPersonDao implements PersonDao {

    private DataSource dataSource;

    @Override
    public Person find(Integer id) {
        return null;
    }

    @Override
    public void save(Person person) {
        //
    }

    @Override
    public void update(Person person) {
        //
    }

    @Override
    public void delete(Person person) {
        //
    }

    @Override
    public List<Person> findAll() {
        return Collections.emptyList();
    }

    @Override
    public List<Person> findByLastName(String lastName) {
        @SuppressWarnings("SqlResolve")
        String sql = "SELECT * FROM people WHERE last_name = ?";
        ResultSet resultSet = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, lastName);
            resultSet = statement.executeQuery();
            List<Person> people = new ArrayList<>();
            while (resultSet.next()) {
                Integer resultId = resultSet.getInt("id");
                String resultFirstName = resultSet.getString("first_name");
                String resultLastName = resultSet.getString("last_name");
                people.add(new Person(resultId, resultFirstName, resultLastName));
            }
            return people;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public void setDatasource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
