package com.library.managament.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.managament.dto.FineDTO;
import com.library.managament.service.FineService;

@RestController
@RequestMapping("/fines")
public class FineController {


    private final FineService fineService;

    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    // GET ALL FINES
    @GetMapping
    public List<FineDTO> getAllFines() {
        return fineService.getAllFines();
    }
    
   

    // GET FINE BY BORROWING ID
    @GetMapping("/borrowing/{borrowingId}")
    public FineDTO getFineByBorrowing(
            @PathVariable Long borrowingId) {

        return fineService.getFineByBorrowing(borrowingId);
    }
    
   
    @GetMapping("/member/{memberId}")
    public List<FineDTO> getFinesByMember(
            @PathVariable Long memberId) {

        return fineService.getFinesByMember(memberId);
    }

    // MARK FINE AS PAID
    @PutMapping("/{fineId}/pay")
    public FineDTO markFineAsPaid(
            @PathVariable Long fineId) {

        return fineService.markFineAsPaid(fineId);
    }
    
}
