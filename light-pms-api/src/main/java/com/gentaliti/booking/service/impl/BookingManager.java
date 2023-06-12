package com.gentaliti.booking.service.impl;

import com.gentaliti.booking.domain.Booking;
import com.gentaliti.booking.domain.BookingRepository;
import com.gentaliti.booking.domain.BookingStatus;
import com.gentaliti.booking.domain.BookingType;
import com.gentaliti.booking.dto.BookingDto;
import com.gentaliti.booking.dto.BookingDtoMapper;
import com.gentaliti.common.exceptions.NotFoundException;
import com.gentaliti.property.domain.Property;
import com.gentaliti.property.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingManager {
    private BookingRepository bookingRepository;
    private PropertyService propertyService;

    BookingDto createBooking(BookingDto bookingDto) {
        validateBooking(bookingDto);

        Property property = propertyService.findById(bookingDto.getPropertyId());
        Booking booking = BookingDtoMapper.mapBooking(bookingDto);
        booking.setProperty(property);
        booking.setType(booking.getType());
        booking.setStatus(BookingStatus.OPEN);

        if (BookingType.RESERVATION.equals(bookingDto.getType())) {
            checkForOverlappingBookings(bookingDto);
        }

        booking = bookingRepository.save(booking);
        return BookingDtoMapper.mapBooking(booking);
    }

    BookingDto updateBooking(BookingDto bookingDto) {
        validateBooking(bookingDto);
        if (bookingDto.getId() == null) {
            throw new IllegalArgumentException("Missing booking id");
        }
        if (BookingType.RESERVATION.equals(bookingDto.getType())) {
            return this.updateReservationBooking(bookingDto);
        }
        return this.updateBlock(bookingDto);
    }

    private BookingDto updateReservationBooking(BookingDto bookingDto) {
        Booking booking = bookingRepository.findById(bookingDto.getId()).orElseThrow(() -> new NotFoundException("Reservation not found"));
        booking.setStartDate(bookingDto.getStartDate());
        booking.setEndDate(bookingDto.getEndDate());
        booking.setStatus(bookingDto.getStatus());
        booking.setType(bookingDto.getType());

        checkForOverlappingBookings(bookingDto);

        booking = bookingRepository.save(booking);
        return BookingDtoMapper.mapBooking(booking);
    }

    private BookingDto updateBlock(BookingDto bookingDto) {
        Booking booking = bookingRepository.findById(bookingDto.getId()).orElseThrow(() -> new NotFoundException("Block not found"));

        booking.setStartDate(bookingDto.getStartDate());
        booking.setEndDate(bookingDto.getEndDate());
        booking.setStatus(bookingDto.getStatus());
        booking.setType(bookingDto.getType());
        booking = bookingRepository.save(booking);
        return BookingDtoMapper.mapBooking(booking);
    }


    private void validateBooking(BookingDto bookingDto) {
        if (bookingDto.getType() == null) {
            throw new IllegalArgumentException("Booking must have a type");
        }

        if (bookingDto.getStartDate() == null) {
            throw new IllegalArgumentException("Booking must have a startDate");
        }

        if (bookingDto.getEndDate() == null) {
            throw new IllegalArgumentException("Booking must have a endDate");
        }

        if (bookingDto.getStartDate().isAfter(bookingDto.getEndDate())) {
            throw new IllegalArgumentException("Start date must be lower than end date");
        }
    }

    private void checkForOverlappingBookings(BookingDto bookingDto) {
        List<Booking> collision = bookingRepository.findCollision(bookingDto.getStartDate(), bookingDto.getEndDate(), bookingDto.getId() != null ? bookingDto.getId() : -1);
        if (!collision.isEmpty()) {
            throw new IllegalArgumentException("Booking is overlapping with an existing one");
        }
    }

}
