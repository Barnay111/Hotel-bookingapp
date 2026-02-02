package com.hotel.hotelbooking.specification;

import com.hotel.hotelbooking.entity.Hotel;
import com.hotel.hotelbooking.filter.HotelFilterDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

public class HotelSpecification {

    public static Specification<Hotel> filterBy(HotelFilterDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            addIdPredicate(predicates, root, cb, filter);
            addNamePredicate(predicates, root, cb, filter);
            addHeadlinePredicate(predicates, root, cb, filter);
            addCityPredicate(predicates, root, cb, filter);
            addAddressPredicate(predicates, root, cb, filter);
            addDistanceFromCenterPredicate(predicates, root, cb, filter);
            addRatingPredicate(predicates, root, cb, filter);
            addNumberOfRatingPredicate(predicates, root, cb, filter);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addIdPredicate(List<Predicate> predicates, Root<Hotel> root,
                                       CriteriaBuilder cb, HotelFilterDto filter) {
        if (filter.id() != null) {
            predicates.add(cb.equal(root.get("id"), filter.id()));
        }
    }

    private static void addNamePredicate(List<Predicate> predicates, Root<Hotel> root, CriteriaBuilder cb, HotelFilterDto filter) {
        if (filter.name() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.name().toLowerCase() + "%"));
        }
    }

    private static void addHeadlinePredicate(List<Predicate> predicates, Root<Hotel> root, CriteriaBuilder cb, HotelFilterDto filter) {
        if (filter.headline() != null) {
            predicates.add(cb.like(cb.lower(root.get("headline")), "%" + filter.headline().toLowerCase() + "%"));
        }
    }

    private static void addCityPredicate(List<Predicate> predicates, Root<Hotel> root, CriteriaBuilder cb, HotelFilterDto filter) {
        if (filter.city() != null) {
            predicates.add(cb.like(cb.lower(root.get("city")), "%" + filter.city().toLowerCase() + "%"));
        }
    }

    private static void addAddressPredicate(List<Predicate> predicates, Root<Hotel> root, CriteriaBuilder cb, HotelFilterDto filter) {
        if (filter.address() != null) {
            predicates.add(cb.like(cb.lower(root.get("address")), "%" + filter.address().toLowerCase() + "%"));
        }
    }


    private static void addDistanceFromCenterPredicate(List<Predicate> predicates, Root<Hotel> root, CriteriaBuilder cb, HotelFilterDto filter) {
        if (filter.distanceFromCenterMin() != null) {
            predicates.add(cb.ge(root.get("distanceFromCenter"), filter.distanceFromCenterMin()));
        }
        if (filter.distanceFromCenterMax() != null) {
            predicates.add(cb.le(root.get("distanceFromCenter"), filter.distanceFromCenterMax()));
        }
    }

    private static void addRatingPredicate(List<Predicate> predicates, Root<Hotel> root, CriteriaBuilder cb, HotelFilterDto filter) {
        if (filter.ratingMin() != null) {
            predicates.add(cb.ge(root.get("rating"), filter.ratingMin()));
        }
        if (filter.ratingMax() != null) {
            predicates.add(cb.le(root.get("rating"), filter.ratingMax()));
        }
    }

    private static void addNumberOfRatingPredicate(List<Predicate> predicates, Root<Hotel> root, CriteriaBuilder cb, HotelFilterDto filter) {
        if (filter.numberOfRatingMin() != null) {
            predicates.add(cb.ge(root.get("numberOfRating"), filter.numberOfRatingMin()));
        }
        if (filter.numberOfRatingMax() != null) {
            predicates.add(cb.le(root.get("numberOfRating"), filter.numberOfRatingMax()));
        }
    }
}
