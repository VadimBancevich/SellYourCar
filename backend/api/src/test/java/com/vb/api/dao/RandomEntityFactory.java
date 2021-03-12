package com.vb.api.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class RandomEntityFactory {

    @Autowired
    private TestEntityManager em;

    private final Random random = new Random();


    public <T> List<T> createRandomEntity(Class<T> clazz, int copies) {
        return createRandomEntity(clazz, true, copies);
    }

    public <T> List<T> createRandomEntity(Class<T> clazz, boolean persist, int copies) {
        ArrayList<T> result = new ArrayList<>(copies);
        while (copies-- != 0) {
            result.add((T) createRandomEntity(clazz, persist));
        }
        return result;
    }

    public <T> T createRandomEntity(Class<T> clazz) {
        return createRandomEntity(clazz, true);
    }

    public <T> T createRandomEntity(Class<T> clazz, Boolean persist) {
        verifyIsItEntity(clazz);
        Object o;
        try {
            o = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Id.class) && (field.isAnnotationPresent(Column.class) ||
                    field.isAnnotationPresent(ManyToOne.class))) {
                Class<?> type = field.getType();
                if (type.isEnum()) {
                    setFieldValue(field, o, getRandomEnumValue(type.getEnumConstants()));
                } else if (type.equals(String.class)) {
                    setFieldValue(field, o, getRandomStringValue());
                } else if (isEntity(type)) {
                    CriteriaBuilder cb = em.getEntityManager().getCriteriaBuilder();
                    CriteriaQuery<?> query = cb.createQuery(type);
                    Root<?> from = query.from(type);
                    List<?> resultList = em.getEntityManager().createQuery(query).getResultList();
                    Object dependedEntity = resultList.size() == 0 ? createRandomEntity(type) : resultList.get(0);
                    setFieldValue(field, o, dependedEntity);
                } else if (type.equals(LocalDate.class)) {
                    setFieldValue(field, o, LocalDate.now());
                } else if (type.equals(LocalDateTime.class)) {
                    setFieldValue(field, o, LocalDateTime.now());
                } else if (type.equals(List.class) && field.isAnnotationPresent(Column.class)) {
                    setFieldValue(field, o, new ArrayList<>());
                } else {
                    setFieldValue(field, o, handlePrimitiveOrWrapper(type));
                }
            }
        }

        return persist ? (T) em.persistAndFlush(o) : (T) o;
    }

    private Object getRandomEnumValue(Object[] enumConstants) {
        return enumConstants[random.nextInt(enumConstants.length - 1)];
    }

    private String getRandomStringValue(int length) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() <= length) {
            int k = random.nextInt(10);
            boolean useUpCase = k > 8;
            int i = useUpCase ? randomInt(1, 65, 91) : randomInt(1, 97, 123);
            sb.append((char) i);
        }
        return sb.toString();
    }

    private int randomInt(int size, int startValue, int endValue) {
        return random.ints(size, startValue, endValue).findFirst().getAsInt();
    }

    private String getRandomStringValue() {
        return getRandomStringValue(10);
    }

    private boolean isEntity(Class<?> clazz) {
        return clazz.isAnnotationPresent(Entity.class);
    }

    private void verifyIsItEntity(Class<?> clazz) {
        if (!isEntity(clazz)) {
            throw new IllegalArgumentException(
                    "Only " + Entity.class.getCanonicalName() + " marked classes can be created by RandomEntityFactory"
            );
        }
    }

    private Object handlePrimitiveOrWrapper(Class<?> type) {
        if (multiEquality(type, int.class, Integer.class)) {
            return random.nextInt(1000);
        } else if (multiEquality(type, long.class, Long.class)) {
            return (long) random.nextInt(10_000);
        } else if (multiEquality(type, boolean.class, Boolean.class)) {
            return random.nextInt(1) != 0;
        }
        return null;
    }

    private boolean multiEquality(Object test, Object... values) {
        for (Object value : values) {
            if (value.equals(test)) {
                return true;
            }
        }
        return false;
    }

    private void setFieldValue(Field field, Object object, Object value) {
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to set value");
        }
    }

}
