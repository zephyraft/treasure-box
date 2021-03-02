package zephyr.tdd.jdbc.dao;

import com.mockobjects.sql.MockMultiRowResultSet;
import org.junit.jupiter.api.Test;
import zephyr.tdd.jdbc.model.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class TestJdbcPersonDao {

    @Test
    void findByLastName() throws SQLException {
        // 模拟数据库连接
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        @SuppressWarnings("SqlResolve")
        String sql = "SELECT * FROM people WHERE last_name = ?";
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        preparedStatement.setString(1, "Smith");

        // 模拟查询结果
        MockMultiRowResultSet resultSet = new MockMultiRowResultSet();
        String[] columns = new String[] {"id", "first_name", "last_name"};
        resultSet.setupColumnNames(columns);
        List<Person> smiths = createListOfPeopleWithLastName("Smith");
        resultSet.setupRows(asResultSetArray(smiths));
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        resultSet.setExpectedCloseCalls(1);

        // 释放资源
        preparedStatement.close();
        connection.close();

        JdbcPersonDao dao = new JdbcPersonDao();
        dao.setDatasource(dataSource);
        List<Person> people = dao.findByLastName("Smith");
        assertEquals(smiths, people);
        resultSet.verify();
    }

    private Object[][] asResultSetArray(List<Person> people) {
        Object[][] array = new Object[people.size()][3];
        for (int i = 0; i < array.length; i++) {
            Person person = people.get(i);
            array[i] = new Object[] {
                    person.getId(),
                    person.getFirstName(),
                    person.getLastName()
            };
        }
        return array;
    }

    private List<Person> createListOfPeopleWithLastName(String lastName) {
        List<Person> expected = new ArrayList<>();
        expected.add(new Person(1, "Alice", lastName));
        expected.add(new Person(1, "Billy", lastName));
        expected.add(new Person(1, "Clark", lastName));
        return expected;
    }
}
