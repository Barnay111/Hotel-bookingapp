package com.hotel.hotelbooking.specification;

import com.hotel.hotelbooking.entity.Room;
import com.hotel.hotelbooking.filter.RoomFilterDto;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomSpecification {

    public static Specification<Room> filterBy(RoomFilterDto filter) {
        return (root, query, cb) -> {

            query.distinct(true);

            List<Predicate> predicates = new ArrayList<>();

            addId(predicates, root, cb, filter);
            addName(predicates, root, cb, filter);
            addPrice(predicates, root, cb, filter);
            addMaxPeople(predicates, root, cb, filter);
            addHotel(predicates, root, cb, filter);
            addDateAvailability(predicates, root, cb, filter, query);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addId(List<Predicate> predicates, Root<Room> root,
                              CriteriaBuilder cb, RoomFilterDto filter) {
        if (filter.id() != null) {
            predicates.add(cb.equal(root.get("id"), filter.id()));
        }
    }

    private static void addName(List<Predicate> predicates, Root<Room> root,
                                CriteriaBuilder cb, RoomFilterDto filter) {
        if (filter.name() != null) {
            predicates.add(
                    cb.like(
                            cb.lower(root.get("name")),
                            "%" + filter.name().toLowerCase() + "%"
                    )
            );
        }
    }

    private static void addPrice(List<Predicate> predicates, Root<Room> root,
                                 CriteriaBuilder cb, RoomFilterDto filter) {
        if (filter.priceMin() != null) {
            predicates.add(cb.ge(root.get("price"), filter.priceMin()));
        }
        if (filter.priceMax() != null) {
            predicates.add(cb.le(root.get("price"), filter.priceMax()));
        }
    }

    private static void addMaxPeople(List<Predicate> predicates, Root<Room> root,
                                     CriteriaBuilder cb, RoomFilterDto filter) {
        if (filter.maxPeople() != null) {
            predicates.add(cb.ge(root.get("maxPeople"), filter.maxPeople()));
        }
    }

    private static void addHotel(List<Predicate> predicates, Root<Room> root,
                                 CriteriaBuilder cb, RoomFilterDto filter) {
        if (filter.hotelId() != null) {
            predicates.add(cb.equal(root.get("hotel").get("id"), filter.hotelId()));
        }
    }

    private static void addDateAvailability(List<Predicate> predicates,
                                            Root<Room> root,
                                            CriteriaBuilder cb,
                                            RoomFilterDto filter,
                                            CriteriaQuery<?> query) {

        LocalDate checkIn = filter.checkIn();
        LocalDate checkOut = filter.checkOut();

        if (checkIn == null || checkOut == null) {
            return;
        }

        Subquery<Long> subquery = query.subquery(Long.class);

        Root<Room> subRoot = subquery.from(Room.class);
        ListJoin<Room, LocalDate> dates = subRoot.joinList("unavailableDates");

        subquery.select(subRoot.get("id"))
                .where(
                        cb.equal(subRoot.get("id"), root.get("id")),
                        cb.and(
                                cb.greaterThanOrEqualTo(dates, checkIn),
                                cb.lessThan(dates, checkOut)
                        )
                );

        predicates.add(cb.not(cb.exists(subquery)));
    }
}
