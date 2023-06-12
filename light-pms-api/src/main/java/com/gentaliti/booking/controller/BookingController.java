package com.gentaliti.booking.controller;

import com.gentaliti.booking.dto.BookingDto;
import com.gentaliti.booking.dto.ResponseDto;
import com.gentaliti.booking.service.PropertyBookingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
@AllArgsConstructor
public class BookingController {
    private final PropertyBookingService propertyBookingService;

    @GetMapping
    public List<BookingDto> findAll() {
        return propertyBookingService.findAll();
    }

    @GetMapping("/property/{propertyId}")
    public List<BookingDto> findAllByPropertyId(@PathVariable("propertyId") Integer propertyId) {
        return propertyBookingService.findAllByPropertyId(propertyId);
    }

    @GetMapping("/{id}")
    public BookingDto findById(@PathVariable("id") Integer id) {
        return propertyBookingService.findById(id);
    }

    @PostMapping
    public BookingDto create(@RequestBody BookingDto bookingDto) {
        return propertyBookingService.create(bookingDto);
    }

    @PutMapping
    public BookingDto update(@RequestBody BookingDto bookingDto) {
        return propertyBookingService.update(bookingDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable("id") Integer id) {
        return propertyBookingService.delete(id);
    }
}
