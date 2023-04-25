package isel.mpd.mvc.utils;

import java.io .*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe que que contém um conjunto de métodos estáticos
 * de suporte à utilizaçáo da API de introspecção/reflexão
 */
public class ReflectionUtils {
    public static List<Field> getFields(Class<?> cls) {
        List<Field> fields = new ArrayList<>();

        while (cls != Object.class) {
            Field[] lf = cls.getDeclaredFields();
            fields.addAll(Arrays.asList(lf));
            cls = cls.getSuperclass();
        }
        return fields;
    }
}
