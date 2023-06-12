package com.gentaliti.booking.dto;

import com.gentaliti.booking.domain.Booking;

public class BookingDtoMapper {
    private BookingDtoMapper() {
    }

    public static BookingDto mapBooking(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .propertyId(booking.getProperty().getId())
                .type(booking.getType())
                .status(booking.getStatus())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    public static Booking mapBooking(BookingDto booking) {
        return Booking.builder()
                .id(booking.getId())
                .type(booking.getType())
                .status(booking.getStatus())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }
}
