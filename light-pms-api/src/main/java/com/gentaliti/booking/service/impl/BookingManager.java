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

@Service
@AllArgsConstructor
public class BookingManager {
    private BookingRepository bookingRepository;
    private PropertyService propertyService;

    BookingDto createBooking(BookingDto bookingDto) {
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
        booking.setStatus(bookingDto.getStatus());
        checkForOverlappingBookings(bookingDto);
        booking = bookingRepository.save(booking);
        return BookingDtoMapper.mapBooking(booking);
    }

    private BookingDto updateBlock(BookingDto bookingDto) {
        Booking booking = bookingRepository.findById(bookingDto.getId()).orElseThrow(() -> new NotFoundException("Block not found"));

        booking.setStartDate(bookingDto.getStartDate());
        booking.setEndDate(bookingDto.getEndDate());
        booking.setStatus(bookingDto.getStatus());
        booking = bookingRepository.save(booking);
        return BookingDtoMapper.mapBooking(booking);
    }

    private void checkForOverlappingBookings(BookingDto bookingDto) {
    }

}
