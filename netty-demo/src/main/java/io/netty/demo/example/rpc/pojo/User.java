package io.netty.demo.example.rpc.pojo;

import java.io.Serializable;

/**
 * @author Jie Zhao
 * @date 2021/7/18 21:16
 */
public class User implements Serializable {

    private long id;
    private String name;

    public String publicField;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}