package zephyr.model.album;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zephyr on 2019-06-27.
 */
public class Artist {
    public String name;
    public Date birthDate;
    public int age;
    public String homeTown;
    public List<String> awardsWon = new ArrayList<>();
}
