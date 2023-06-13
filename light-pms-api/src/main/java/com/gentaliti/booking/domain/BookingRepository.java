package com.gentaliti.booking.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByPropertyId(Integer propertyId);

    @Query(value = "FROM pms_booking WHERE property.id = :propertyId AND status <> 'CANCELED' AND id <> :excludeId AND ((startDate <= :startDate AND endDate >= :startDate) OR (startDate <= :endDate AND endDate >= :endDate))")
    List<Booking> findCollision(@Param("propertyId") Integer propertyId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("excludeId") Integer excludeId);
}
