package com.gentaliti.booking.service.impl;

import com.gentaliti.booking.domain.Booking;
import com.gentaliti.booking.domain.BookingRepository;
import com.gentaliti.booking.domain.BookingStatus;
import com.gentaliti.booking.domain.BookingType;
import com.gentaliti.booking.dto.BookingDto;
import com.gentaliti.booking.dto.BookingDtoMapper;
import com.gentaliti.common.exceptions.LightPmsValidationException;
import com.gentaliti.common.exceptions.NotFoundException;
import com.gentaliti.common.lock.LockedExecutionService;
import com.gentaliti.property.domain.Property;
import com.gentaliti.property.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingManager {
    private static final String LOCK_NAME = "PROPERTY_LOCK__";

    private BookingRepository bookingRepository;
    private PropertyService propertyService;
    private LockedExecutionService lockedExecutionService;

    public BookingDto createBooking(BookingDto bookingDto) {
        validateBooking(bookingDto);

        Property property = propertyService.findById(bookingDto.getPropertyId());
        Booking booking = BookingDtoMapper.mapBooking(bookingDto);
        booking.setProperty(property);
        booking.setType(booking.getType());
        booking.setStatus(BookingStatus.OPEN);

        booking = saveBookingWithLock(booking);
        return BookingDtoMapper.mapBooking(booking);
    }

    public BookingDto updateBooking(BookingDto bookingDto) {
        validateBooking(bookingDto);
        if (bookingDto.getId() == null) {
            throw new LightPmsValidationException("Missing booking id");
        }
        if (BookingType.RESERVATION.equals(bookingDto.getType())) {
            return this.updateReservation(bookingDto);
        }
        return this.updateBlock(bookingDto);
    }

    private BookingDto updateReservation(BookingDto bookingDto) {
        Booking booking = bookingRepository.findById(bookingDto.getId()).orElseThrow(() -> new NotFoundException("Reservation not found"));
        booking.setStartDate(bookingDto.getStartDate());
        booking.setEndDate(bookingDto.getEndDate());
        booking.setStatus(bookingDto.getStatus());
        booking.setType(bookingDto.getType());

        booking = saveBookingWithLock(booking);
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
            throw new LightPmsValidationException("Booking must have a type");
        }

        if (bookingDto.getStartDate() == null) {
            throw new LightPmsValidationException("Booking must have a startDate");
        }

        if (bookingDto.getEndDate() == null) {
            throw new LightPmsValidationException("Booking must have a endDate");
        }

        if (bookingDto.getStartDate().isAfter(bookingDto.getEndDate())) {
            throw new LightPmsValidationException("Start date must be lower than end date");
        }
    }

    private Booking saveBookingWithLock(Booking booking) {
        final String lockName = LOCK_NAME + booking.getProperty().getId();

        return lockedExecutionService.runLocked(() -> checkCollisionAndUpdate(booking), lockName);
    }

    private Booking checkCollisionAndUpdate(Booking booking) {
        if (BookingType.RESERVATION.equals(booking.getType())) {
            checkForOverlappingBookings(booking);
        }
        return bookingRepository.save(booking);
    }

    private void checkForOverlappingBookings(Booking booking) {
        List<Booking> collision = bookingRepository.findCollision(booking.getProperty().getId(), booking.getStartDate(), booking.getEndDate(), booking.getId() != null ? booking.getId() : -1);
        if (!collision.isEmpty()) {
            throw new LightPmsValidationException("Booking is overlapping with an existing one");
        }
    }
}
