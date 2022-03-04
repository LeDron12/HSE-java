package mapper;

import mapper.annotations.Exported;
import mapper.annotations.Ignored;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public class Mapper {
    // Restores object from json-like string.
    public <T> T readFromString(Class<T> clazz, String input) throws ParseException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Deserializer deserializer = new Deserializer(input, clazz);
        deserializer.deserialize();

        return (T) deserializer.getObject();
    }

    // Write object to json-like string.
    public String writeToString(Object object) throws IllegalAccessException {
        if(!isExported(object)) {
            throw new RuntimeException("Object has no essential attribute \"Exported\"");
        }
        // TODO : rework cycled check.
        if(isCycled(object)) {
            throw new RuntimeException("Cant serialize cycled object");
        }

        Serrializer serrializer = new Serrializer(object, 0);
        String ret = serrializer.serialize();

        return ret;
    }

    // Checking if object has serializing cycles.
    private boolean isCycled(Object object) throws IllegalAccessException {
        var fields = object.getClass().getDeclaredFields();

        for (var field : fields) {
            var fieldAnnotations = field.getDeclaredAnnotations();

            field.setAccessible(true);
            for (var annotation : fieldAnnotations) {
                if (field.get(object).getClass() == object.getClass() &&
                        !(annotation instanceof Ignored)) {
                    return true;
                }
            }
            field.setAccessible(false);
        }

        return false;
    }

    // Checking if object is annotated as Exported.
    private boolean isExported(Object object) {
        for (var annotation : object.getClass().getDeclaredAnnotations()) {
            if (annotation instanceof Exported) {
                return true;
            }
        }
        return false;
    }
}
