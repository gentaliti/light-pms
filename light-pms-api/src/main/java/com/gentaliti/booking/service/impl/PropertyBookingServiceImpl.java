package com.gentaliti.booking.service.impl;

import com.gentaliti.booking.domain.BookingRepository;
import com.gentaliti.booking.dto.BookingDto;
import com.gentaliti.booking.dto.BookingDtoMapper;
import com.gentaliti.booking.dto.ResponseDto;
import com.gentaliti.booking.service.PropertyBookingService;
import com.gentaliti.common.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PropertyBookingServiceImpl implements PropertyBookingService {
    private BookingRepository bookingRepository;

    private BookingManager bookingManager;

    @Override
    public List<BookingDto> findAll() {
        return bookingRepository
                .findAll()
                .stream()
                .map(BookingDtoMapper::mapBooking)
                .toList();
    }

    @Override
    public List<BookingDto> findAllByPropertyId(Integer propertyId) {
        return bookingRepository
                .findAllByPropertyId(propertyId)
                .stream()
                .map(BookingDtoMapper::mapBooking)
                .toList();
    }

    @Override
    public BookingDto findById(int id) {
        return bookingRepository.findById(id)
                .map(BookingDtoMapper::mapBooking)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
    }

    @Override
    @Transactional
    public BookingDto create(BookingDto bookingDto) {
        return bookingManager.createBooking(bookingDto);
    }

    @Override
    @Transactional
    public BookingDto update(BookingDto bookingDto) {
        return bookingManager.updateBooking(bookingDto);
    }

    @Override
    public ResponseDto delete(int id) {
        bookingRepository.deleteById(id);
        return ResponseDto.builder()
                .messaage("Booking deleted successfully")
                .build();
    }
}
