package zephyr.demo.model;

import java.io.Serializable;

public class Alert implements Serializable {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
