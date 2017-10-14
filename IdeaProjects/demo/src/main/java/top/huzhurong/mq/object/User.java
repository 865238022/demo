package top.huzhurong.queueMq.object.bean;

import java.io.Serializable;

/**
 * Created by 竹 on 2017/10/14.
 * Serializable 根据ObjectMessage 而得知，不过这样的pojo一般都是要实现这个接口的吧
 */
public class User implements Serializable {
    private static final long serialVersionUID = -3581868791819100032L;
    private String name;
    private int age;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
