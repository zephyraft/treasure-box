package zephyr.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by zephyr on 2019/12/13.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public static final User SKYLER = new User("swhite", "Skyler", "White");
    public static final User JESSE = new User("jpinkman", "Jesse", "Pinkman");
    public static final User WALTER = new User("wwhite", "Walter", "White");
    public static final User SAUL = new User("sgoodman", "Saul", "Goodman");

    private String username;
    private String firstname;
    private String lastname;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;

        if (!username.equals(user.username)) {
            return false;
        }
        if (!firstname.equals(user.firstname)) {
            return false;
        }
        return lastname.equals(user.lastname);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + firstname.hashCode();
        result = 31 * result + lastname.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
