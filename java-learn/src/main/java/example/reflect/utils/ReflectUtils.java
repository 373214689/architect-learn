package example.reflect.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReflectUtils {

    public static <S, T> void  clone(S source, T target) {
        for(Field field1: target.getClass().getDeclaredFields()) {
            try {
                Field field2 = source.getClass().getDeclaredField(field1.getName());
                // 类型一致可以复制值，如果类型不一致则需要处理后再复制值
                if (field1.getType() == field2.getType()) {
                    field1.setAccessible(true);
                    field2.setAccessible(true);
                    field1.set(target, field2.get(source));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // 不处理找不到的字段
                // e.printStackTrace();
            }
        }
    }

    public static <S, T> void  clone(Map<String, Object> source, T target) {
        for(Field field1: target.getClass().getDeclaredFields()) {
            try {
                Object value = source.get(field1.getName());
                if (null == value) {
                    continue;
                }
                field1.setAccessible(true);
                // 类型一致可以复制值，如果类型不一致则需要处理后再复制值
                if (value.getClass() == field1.getType()) {
                    field1.set(target, value);
                } else {
                    String name1 = field1.getType().getName();
                    String name2 = value.getClass().getName();
                    System.out.println(name1 + "<>" + name2);
                    switch(name1) {
                        case "int":
                        case "java.lang.Integer":
                            field1.set(target, Integer.parseInt(String.valueOf(value)));
                            break;
                        case "long":
                        case "java.lang.Long":
                            field1.set(target, Long.parseLong(String.valueOf(value)));
                            break;
                        case "float":
                        case "java.lang.Float":
                            field1.set(target, Float.parseFloat(String.valueOf(value)));
                            break;
                    }
                }
            } catch (IllegalAccessException e) {
                // 不处理找不到的字段
                // e.printStackTrace();
            }
        }

    }

    public static <S, T> T translate(S source, Class<T> cls) {
        try {
            T target = cls.newInstance();
            clone(source, target);
            return target;
        } catch (Exception e) {
            System.err.println("ReflectUtils.translate failed, cause: " + e.getLocalizedMessage());
            return null; // 如果出现异常，则返回空值
        }
    }

    public static <S, T> T translate(Map<String, Object> source, Class<T> cls) {
        try {
            T target = cls.newInstance();
            clone(source, target);
            return target;
        } catch (Exception e) {
            System.err.println("ReflectUtils.translate failed, cause: " + e.getLocalizedMessage());
            return null; // 如果出现异常，则返回空值
        }
    }

    public static <S, T> List<T> translate(List<S> sources, Class<T> cls) {
        try {
            List<T> targets = new ArrayList<>();
            for (S source: sources) {
                T target = cls.newInstance();
                clone(source, target);
                targets.add(target);
            }
            return targets;
        } catch (Exception e) {
            System.err.println("ReflectUtils.translate failed, cause: " + e.getLocalizedMessage());
            return null; // 如果出现异常，则返回空值
        }
    }
}
