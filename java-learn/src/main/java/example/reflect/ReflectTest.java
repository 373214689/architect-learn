package example.reflect;


import example.reflect.model.Person;
import example.reflect.utils.ReflectUtils;

import java.util.HashMap;
import java.util.Map;

public class ReflectTest {

    public static void main(String[] args) {

        Map<String, Object> source = new HashMap<>();

        source.put("name", "张三");
        source.put("sex", "男");
        source.put("age", 20);

        Person a = ReflectUtils.translate(source, Person.class);

        System.out.println(a);
    }
}
