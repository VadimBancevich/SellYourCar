package com.vb.api.dao.impl;

import com.vb.api.dao.CarCustomDao;
import com.vb.entities.AEntity;
import com.vb.entities.Car;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarCustomDaoImpl implements CarCustomDao {

    @PersistenceContext
    private EntityManager em;

    private static final Map<String, Object[]> stringOnEnum = new HashMap<>();

    @PostConstruct
    private void init() {
        Field[] fields = Car.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().isEnum()) {
                stringOnEnum.put(field.getName(), field.getType().getEnumConstants());
            }
        }
    }


    @Transactional
    @Override
    public LightPage<Car> findByParams(MultiValueMap<String, String> params, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Car> query = cb.createQuery(Car.class);
        Root<Car> carRoot = query.from(Car.class);

        Predicate[] predicates = createPredicates(params, cb, carRoot);
        Order order = createOrder(params, carRoot);

        Long carsCount = findCountByParams(params);

        query.where(predicates).orderBy(order);

        List<Car> resultList = em.createQuery(query)
                .setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize())
                .getResultList();
        return new LightPage<>(resultList, pageable, carsCount);
    }

    @Transactional
    @Override
    public Long findCountByParams(MultiValueMap<String, String> params) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Car> root = query.from(Car.class);
        query.select(cb.count(root)).where(createPredicates(params, cb, root));
        return em.createQuery(query).getSingleResult();
    }

    private Predicate[] createPredicates(MultiValueMap<String, String> params, CriteriaBuilder cb, Root<? extends AEntity> root) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        params.forEach((paramName, paramValues) -> {
            if (paramName.endsWith("From")) {
                String fieldName = paramName.split("From")[0];
                predicates.add(cb.greaterThanOrEqualTo(root.get(fieldName), paramValues.get(0)));
            } else if (paramName.endsWith("To")) {
                String fieldName = paramName.split("To")[0];
                predicates.add(cb.lessThanOrEqualTo(root.get(fieldName), paramValues.get(0)));
            } else if (paramName.endsWith("Id")) {
                String fieldName = paramName.split("Id")[0];
                predicates.add(cb.equal(root.get(fieldName).get("id"), paramValues.get(0)));

            } else if (stringOnEnum.containsKey(paramName)) {
                List<Object> inValues = Stream.of(stringOnEnum.get(paramName))
                        .filter(o -> paramValues.contains(o.toString())).collect(Collectors.toList());
                predicates.add(cb.in(root.get(paramName)).value(inValues));

            }
        });

        return predicates.toArray(new Predicate[0]);
    }

    private Order createOrder(MultiValueMap<String, String> params, Root<? extends AEntity> root) {
        String sortParam = params.getFirst("sortBy");

        if (sortParam == null) {
            sortParam = "uppingDate";
        }

        String sortDirection = params.getFirst("sortDir");

        if (sortDirection != null && !sortDirection.equals("asc") && !sortDirection.equals("desc")) {
            throw new IllegalArgumentException("Sort direction value should be asc or desc");
        }

        boolean isAsc = sortDirection == null || sortDirection.equals("asc");

        return new OrderImpl(root.get(sortParam), isAsc);
    }

}
