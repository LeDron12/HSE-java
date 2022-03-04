package mapper;

import mapper.annotations.Ignored;
import mapper.annotations.PropertyName;

import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class Serrializer {
    private final int startDepth;
    private final Object object;
    private final StringBuilder stringBuilder;

    public Serrializer(Object object, int startDepth) {
        this.startDepth = startDepth;
        this.object = object;
        this.stringBuilder = new StringBuilder();
    }

    // Serializes object of this Serializer instance.
    // Returns human-readable format string.
    public String serialize() throws IllegalAccessException {
        if (!hasDefaultConstructor(object)) {
            throw new RuntimeException("Object has no default constructor");
        }

        stringBuilder.append("{ " + object.getClass().getSimpleName() + "\n");

        var fields = object.getClass().getDeclaredFields();
        for (var field : fields) {
            if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                continue;
            }

            String key = field.getName();
            boolean marker = true;

            var fieldAnnotations = field.getDeclaredAnnotations();
            for (var annotation : fieldAnnotations) {
                if (annotation instanceof Ignored) {
                    marker = false;
                } else if (annotation instanceof PropertyName) {
                    var propertyNameAnnotation = (PropertyName) annotation;
                    key = propertyNameAnnotation.value();
                }
            }

            // If field is not ignored.
            if (marker) {
                field.setAccessible(true);
                Object value = field.get(object);
                field.setAccessible(false);

                addToString(key, value, startDepth + 1);
            }
        }
        stringBuilder.append(getShift(startDepth) + "},\n");

        return stringBuilder.toString();
    }

    // Returns indent spaces for current serialization iteration.
    private String getShift(int depth) {
        String shift = "";
        for (int i = 0; i < depth; i++) {
            shift += "    ";
        }
        return shift;
    }

    // Serializes complex object to string.
    private <T, V> void addToString(String key, T value, int depth) throws IllegalAccessException {
        stringBuilder.append(getShift(depth) + "\"" + key + "\": ");

        if (!isPrimitive(value)) {
            if (value instanceof String) {
                stringBuilder.append("\"" + value + "\",\n");
            } else if (value instanceof List<?>) {
                stringBuilder.append("[ List\n");

                List<V> list = (List<V>) value;
                for (var item : list) {
                    if (isPrimitive(item) || item instanceof String) {
                        addToString("List-Item", item, depth + 1);
                    } else {
                        stringBuilder.append(getShift(depth + 1) + "\"List-Item\": ");
                        Serrializer listSerializer = new Serrializer(item, depth + 1);
                        stringBuilder.append(listSerializer.serialize());
                    }
                }

                stringBuilder.append(getShift(depth) + "],\n");
            } else if (value instanceof Set<?>) {
                stringBuilder.append("{ Set\n");

                Set<V> set = (Set<V>) value;
                for (var item : set) {
                    if (isPrimitive(item) || item instanceof String) {
                        addToString("Set-Item", item, depth + 1);
                    } else {
                        stringBuilder.append(getShift(depth + 1) + "\"Set-Item\": ");
                        Serrializer setSerializer = new Serrializer(item, depth + 1);
                        stringBuilder.append(setSerializer.serialize());
                    }
                }

                stringBuilder.append(getShift(depth) + "},\n");
            } else if (value instanceof LocalDate) {
                stringBuilder.append("{ LocalDate\n");

                var localDate = (LocalDate) value;
                addToString("LD-Day", localDate.getDayOfMonth(), depth + 1);
                addToString("LD-Month", localDate.getMonth().getValue(), depth + 1);
                addToString("LD-Year", localDate.getYear(), depth + 1);

                stringBuilder.append(getShift(depth) + "},\n");
            } else if (value instanceof LocalTime) {
                stringBuilder.append("{ LocalTime\n");

                var localTime = (LocalTime) value;
                addToString("LT-NanoSecond", localTime.getNano(), depth + 1);
                addToString("LT-Second", localTime.getSecond(), depth + 1);
                addToString("LT-Minute", localTime.getMinute(), depth + 1);
                addToString("LT-Hour", localTime.getHour(), depth + 1);

                stringBuilder.append(getShift(depth) + "},\n");
            } else if (value instanceof LocalDateTime) {
                stringBuilder.append("{ LocalDateTime\n");

                var localDateTime = (LocalDateTime) value;
                addToString("LDT-NanoSecond", localDateTime.getNano(), depth + 1);
                addToString("LDT-Second", localDateTime.getSecond(), depth + 1);
                addToString("LDT-Minute", localDateTime.getMinute(), depth + 1);
                addToString("LDT-Hour", localDateTime.getHour(), depth + 1);
                addToString("LDT-Day", localDateTime.getDayOfMonth(), depth + 1);
                addToString("LDT-Month", localDateTime.getMonth().getValue(), depth + 1);
                addToString("LDT-Year", localDateTime.getYear(), depth + 1);

                stringBuilder.append(getShift(depth) + "},\n");
            } else {
                Serrializer setSerializer = new Serrializer(value, depth);
                stringBuilder.append(setSerializer.serialize());
            }
        } else {
            stringBuilder.append(value + ",\n");
        }
    }

    // Shrinks to fit string and returns it.
    public String getSingleLine() {
        return stringBuilder.toString().replace("\n", "")
                .replace(" ", "").replace(":", ": ")
                .replace(",}", "}").replace(",]", "]");
    }

    // Returns human-readable format string.
    public String getBeautifulLine() {
        return stringBuilder.toString();
    }

    // Checking if value is primitive.
    private <T> boolean isPrimitive(T value) {
        var clazz = value.getClass();
        return clazz == Boolean.class || clazz == Character.class ||
                clazz == Byte.class || clazz == Short.class ||
                clazz == Integer.class || clazz == Long.class ||
                clazz == Float.class || clazz == Double.class;
    }

    // Checks if object has default constructor.
    private boolean hasDefaultConstructor(Object object) {
        for (var constructor : object.getClass().getConstructors()) {
            constructor.setAccessible(true);
            if(constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }
}
