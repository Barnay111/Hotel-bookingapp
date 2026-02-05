package com.hotel.hotelbooking.repository;

import com.hotel.hotelbooking.entity.StatisticEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticRepository extends MongoRepository<StatisticEvent, String> {
}
