package com.gentaliti.booking.service;

import com.gentaliti.booking.dto.BookingDto;
import com.gentaliti.booking.dto.ResponseDto;

import java.util.List;

public interface PropertyBookingService {
    List<BookingDto> findAll();

    BookingDto findById(int id);

    BookingDto create(BookingDto bookingDto);

    BookingDto update(BookingDto bookingDto);

    ResponseDto delete(int id);

    List<BookingDto> findAllByPropertyId(Integer propertyId);
}
