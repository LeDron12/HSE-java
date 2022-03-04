package mapper;

import mapper.annotations.Exported;
import mapper.annotations.Ignored;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import mapper.annotations.PropertyName;
import org.apache.commons.lang3.math.NumberUtils;

public class Deserializer {
    private final Scanner scanner;
    private final String serializedObject;
    private Object object;
    Stack<Object[]> stack;

    public <T> Deserializer(String serializedObject, Class<T> object) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        this.scanner = new Scanner(serializedObject);
        this.serializedObject = serializedObject;
        this.object = object.getConstructor().newInstance();
        this.stack = new Stack<Object[]>();
    }

    // Deserializing object form string.
    public <T, V> void deserialize() throws IllegalAccessException, ParseException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
        addFieldsToStack(new Object[]{null, object});
        scanner.nextLine();

        while (scanner.hasNextLine()) {
            Object[] curr_object;
            String currStr = scanner.nextLine();

            if (currStr.contains("{") || currStr.contains("[")) {
                curr_object = stack.pop();
                addFieldsToStack(curr_object);
            } else if (currStr.contains("}") || currStr.contains("]")) {
                if (!stack.empty()) {
                    curr_object = stack.peek();
                    Object obj = ((Field) curr_object[1]).get(curr_object[0]);
                    if (obj != null) {
                        String topChildName = obj.getClass().getCanonicalName();
                        if ((topChildName.contains("List") || topChildName.contains("Set"))
                                && topChildName.contains("java.util.")
                                || topChildName.equals("java.time.LocalDate")
                                || topChildName.equals("java.time.LocalTime")
                                || topChildName.equals("java.time.LocalDateTime")) {
                            stack.pop();
                        }
                    }
                }
            } else {
                curr_object = stack.pop();

                Field curr_field = (Field) curr_object[1];

                String fieldName = getFieldName(currStr);
                Object fieldValue;

                if (fieldName.equals("List-Item")) {
                    curr_field.setAccessible(true);
                    List<V> list = (List<V>) (curr_field.get(curr_object[0]));
                    fieldValue = getFieldValue(currStr, list.getClass().getGenericSuperclass());
                    list.add((V) fieldValue);
                    curr_field.setAccessible(false);

                    stack.push(curr_object);
                } else if (fieldName.equals("Set-Item")) {
                    curr_field.setAccessible(true);
                    Set<V> set = (Set<V>) (curr_field.get(curr_object[0]));
                    fieldValue = getFieldValue(currStr, set.getClass().getGenericSuperclass());
                    set.add((V) fieldValue);
                    curr_field.setAccessible(false);

                    stack.push(curr_object);
                } else if (fieldName.startsWith("LT-")) {
                    fieldValue = getFieldValue(currStr, 0);
                    LocalTime localTime = (LocalTime) curr_field.get(curr_object[0]);

                    if (fieldName.contains("NanoSecond")) {
                        curr_field.set(curr_object[0], localTime.withNano((int) fieldValue));
                    } else if (fieldName.contains("Second")) {
                        curr_field.set(curr_object[0], localTime.withSecond((int) fieldValue));
                    } else if (fieldName.contains("Minute")) {
                        curr_field.set(curr_object[0], localTime.withMinute((int) fieldValue));
                    } else if (fieldName.contains("Hour")) {
                        curr_field.set(curr_object[0], localTime.withHour((int) fieldValue));
                    } else {
                        throw new RuntimeException("Something went wrong while" +
                                "deserializing " + fieldName + " to LocalTime");
                    }
                    stack.push(curr_object);
                } else if (fieldName.startsWith("LD-")) {
                    fieldValue = getFieldValue(currStr, 0);
                    LocalDate localDate = (LocalDate) curr_field.get(curr_object[0]);

                    if (fieldName.contains("Day")) {
                        curr_field.set(curr_object[0], localDate.withDayOfMonth((int) fieldValue));
                    } else if (fieldName.contains("Month")) {
                        curr_field.set(curr_object[0], localDate.withMonth((int) fieldValue));
                    } else if (fieldName.contains("Year")) {
                        curr_field.set(curr_object[0], localDate.withYear((int) fieldValue));
                    } else {
                        throw new RuntimeException("Something went wrong while" +
                                "deserializing " + fieldName + " to LocalTime");
                    }
                    stack.push(curr_object);
                } else if (fieldName.startsWith("LDT-")) {
                    fieldValue = getFieldValue(currStr, 0);
                    LocalDateTime localDateTime = (LocalDateTime) curr_field.get(curr_object[0]);

                    if (fieldName.contains("NanoSecond")) {
                        curr_field.set(curr_object[0], localDateTime.withNano((int) fieldValue));
                    } else if (fieldName.contains("Second")) {
                        curr_field.set(curr_object[0], localDateTime.withSecond((int) fieldValue));
                    } else if (fieldName.contains("Minute")) {
                        curr_field.set(curr_object[0], localDateTime.withMinute((int) fieldValue));
                    } else if (fieldName.contains("Hour")) {
                        curr_field.set(curr_object[0], localDateTime.withHour((int) fieldValue));
                    } else if (fieldName.contains("Day")) {
                        curr_field.set(curr_object[0], localDateTime.withDayOfMonth((int) fieldValue));
                    } else if (fieldName.contains("Month")) {
                        curr_field.set(curr_object[0], localDateTime.withMonth((int) fieldValue));
                    } else if (fieldName.contains("Year")) {
                        curr_field.set(curr_object[0], localDateTime.withYear((int) fieldValue));
                    } else {
                        throw new RuntimeException("Something went wrong while" +
                                "deserializing " + fieldName + " to LocalTime");
                    }
                    stack.push(curr_object);
                } else if (curr_object[1] instanceof Field) {
                    curr_field.setAccessible(true);
                    fieldValue = getFieldValue(currStr, curr_field.get(curr_object[0]));
                    isCorrectItem(curr_field, fieldName); // Throws exception if mismatched.
                    curr_field.set(curr_object[0], fieldValue);
                    curr_field.setAccessible(false);
                }
            }
        }
    }

    // Checks if current object is a field and has declared name.
    private boolean hasName(String str) {
        if (str.indexOf("{") == 0) {
            return false;
        }
        return true;
    }

    // Returns current object name.
    private String getFieldName(String str) {
        int startIndex = str.indexOf("\"") + 1;
        int endIndex = str.indexOf("\"", startIndex);

        return str.substring(startIndex, endIndex);
    }

    // Returns object type name.
    private String getClassName(String str) {
        int startIndex = Math.max(str.indexOf("{"), str.indexOf("[")) + 2;
        int endIndex = str.length() - 1;

        return str.substring(startIndex, endIndex);
    }

    // Returns field value.
    private Object getFieldValue(String str, Object clazz) throws ParseException {
        int startIndex = str.indexOf(":") + 2;
        int endIndex = str.indexOf(",");
        String ans = str.substring(startIndex, endIndex);

        if (NumberUtils.isParsable(ans)) {
            if (clazz instanceof java.lang.Integer) {
                return Integer.parseInt(ans);
            } else if (clazz instanceof java.lang.Long) {
                return Long.parseLong(ans);
            } else if (clazz instanceof java.lang.Short) {
                return Short.parseShort(ans);
            } else if (clazz instanceof java.lang.Double) {
                return Double.parseDouble(ans);
            } else if (clazz instanceof java.lang.Float) {
                return Float.parseFloat(ans);
            } else if (clazz instanceof java.lang.Byte) {
                return Byte.parseByte(ans);
            }
        }

        if (clazz instanceof java.lang.Character) {
            return ans.charAt(0);
        } else if (clazz instanceof java.lang.Boolean) {
            return Boolean.parseBoolean(ans);
        }

        return str.substring(startIndex + 1, endIndex - 1);
    }

    // Push valid fields to stack in reverse order.
    private <T> void addFieldsToStack(Object[] object) throws IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, InstantiationException {
        Object curr_object = object[1];
        String ChildTypeName = curr_object.getClass().getCanonicalName();

        if (object[1] instanceof Field) {
            ChildTypeName = ((Field) object[1]).getType().getCanonicalName();
        }

        if (ChildTypeName.contains("List") && ChildTypeName.contains("java.util.")) {
            ((Field) object[1]).set(object[0], createGenericList((Class<T>) ((Field) object[1]).getType()));
            stack.push(new Object[]{object[0], object[1]});
        } else if (ChildTypeName.contains("Set") && ChildTypeName.contains("java.util.")) {
            ((Field) object[1]).set(object[0], createGenericSet((Class<T>) ((Field) object[1]).getType()));
            stack.push(new Object[]{object[0], object[1]});
        } else if (ChildTypeName.equals("java.time.LocalDate")) {
            LocalDate localDate = LocalDate.of(0, 1, 1);
            ((Field) object[1]).set(object[0], localDate);
            stack.push(new Object[]{object[0], object[1]});
        } else if (ChildTypeName.equals("java.time.LocalTime")) {
            LocalTime localTime = LocalTime.of(0, 0, 0, 0);
            ((Field) object[1]).set(object[0], localTime);
            stack.push(new Object[]{object[0], object[1]});
        } else if (ChildTypeName.equals("java.time.LocalDateTime")) {
            LocalDateTime localDateTime = LocalDateTime.of(0, 1, 1, 0, 0, 0, 0);
            ((Field) object[1]).set(object[0], localDateTime);
            stack.push(new Object[]{object[0], object[1]});
        } else {
            if (object[1] instanceof Field) {
                curr_object = ((Field) object[1]).get(object[0]);

                if (curr_object == null && hasDefaultConstructor(((Field) object[1]).getType())) {
                    curr_object = ((Field) object[1]).getType().getConstructor().newInstance();
                    ((Field) object[1]).set(object[0], curr_object);
                }
                if (curr_object == null) {
                    throw new RuntimeException("Object " + ((Field) object[1]).getType().getCanonicalName()
                            + " has no parameterless constructor\n");
                }
            }
            if (!isExported(curr_object)) {
                return;
            }
            if (!hasDefaultConstructor(curr_object.getClass())) {
                throw new RuntimeException("Object has no default constructor\n");
            }

            var fields = Arrays.asList(curr_object.getClass().getDeclaredFields());
            Collections.reverse(fields);

            for (var field : fields) {
                field.setAccessible(true);

                if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                    continue;
                }

                boolean marker = true;
                for (var annotation : field.getDeclaredAnnotations()) {
                    if (annotation instanceof Ignored) {
                        marker = false;
                        break;
                    }
                }

                if (marker) {
                    stack.push(new Object[]{curr_object, field});
                }
                field.setAccessible(false);
            }
        }
    }

    private <T> List<T> createGenericList(Class<T> clazz) {
        return new ArrayList<T>();
    }

    private <T> Set<T> createGenericSet(Class<T> clazz) {
        return new HashSet<T>();
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

    // Checks if object has default constructor.
    private <T> boolean hasDefaultConstructor(Class<T> object) {
        var x = object.getDeclaredConstructors();
        for (var constructor : object.getDeclaredConstructors()) {
            int z = constructor.getParameterCount();
            if (constructor.getParameterCount() == 0) {
                return true;
            }
        }
        var y = object.getConstructors();
        for (var constructor : object.getConstructors()) {
            int z = constructor.getParameterCount();
            if (constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }

    // Checks if name of the serializing field is correct.
    private void isCorrectItem(Field field, String name) {
        if (!field.getName().equals(name)) {
            var propertyNameAnnotation = field.getDeclaredAnnotation(PropertyName.class);
            if (propertyNameAnnotation == null ||
                    !propertyNameAnnotation.value().equals(name)) {
                throw new RuntimeException("Something went wrong while deserialization" +
                        " field " + name + " to " + field.getName());
            }
        }
    }

    // Returns deserialized object.
    public Object getObject() {
        return this.object;
    }
}
