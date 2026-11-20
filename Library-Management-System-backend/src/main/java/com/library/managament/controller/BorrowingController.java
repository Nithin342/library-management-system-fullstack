package com.library.managament.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.managament.dto.BorrowingDTO;
import com.library.managament.entity.BorrowingStatus;
import com.library.managament.exception.InvalidBorrowingStatusException;
import com.library.managament.service.BorrowingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/borrowings")
public class BorrowingController {


    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping
    public BorrowingDTO borrowBook(
    		@Valid @RequestBody BorrowingDTO borrowingDTO) {

        return borrowingService.borrowBook(borrowingDTO);
    }
    
    @GetMapping("/overdue")
    public List<BorrowingDTO> getOverdueBorrowings() {

        return borrowingService.getOverdueBorrowings();
    }
    
    @GetMapping("/{id}")
    public BorrowingDTO getBorrowingById(@PathVariable Long id) {
        return borrowingService.getBorrowingById(id);
    }
    
    
    @PutMapping("/{id}/return")
    public BorrowingDTO returnBook(@PathVariable Long id) {
        return borrowingService.returnBook(id);
    }
    
    @GetMapping
    public List<BorrowingDTO> getAllBorrowings() {
        return borrowingService.getAllBorrowings();
    }
    
    @GetMapping("/status/{status}")
    public List<BorrowingDTO> getBorrowingsByStatus(
            @PathVariable String status) {

        try {
            BorrowingStatus borrowingStatus =
                    BorrowingStatus.valueOf(status.toUpperCase());

            return borrowingService.getBorrowingsByStatus(borrowingStatus);

        } catch (IllegalArgumentException ex) {

            throw new InvalidBorrowingStatusException(
                    "Invalid borrowing status: " + status);
        }
    }
   
    @GetMapping("/page")
    public Page<BorrowingDTO> getBorrowingsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return borrowingService.getBorrowingsWithPagination(
                page, size, sortBy, direction);
    }
    
    @GetMapping("/search/page")
    public Page<BorrowingDTO> searchBorrowingsWithPagination(

            @RequestParam(required = false) String status,

            @RequestParam(required = false) Long memberId,

            @RequestParam(required = false) Long bookId,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

    	BorrowingStatus borrowingStatus = null;

    	if (status != null && !status.isBlank()) {

    	    try {
    	        borrowingStatus =
    	                BorrowingStatus.valueOf(status.toUpperCase());

    	    } catch (IllegalArgumentException ex) {

    	        throw new InvalidBorrowingStatusException(
    	                "Invalid borrowing status: " + status);
    	    }
    	}
        

        return borrowingService.searchBorrowingsWithPagination(
                borrowingStatus,
                memberId,
                bookId,
                page,
                size,
                sortBy,
                direction);
    }
    
    
   
}
