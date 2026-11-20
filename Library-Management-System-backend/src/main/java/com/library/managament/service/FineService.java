package com.library.managament.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.library.managament.dto.FineDTO;
import com.library.managament.entity.Fine;
import com.library.managament.exception.FineAlreadyPaidException;
import com.library.managament.exception.FineNotFoundException;
import com.library.managament.repository.FineRepository;

@Service
public class FineService {

	private final FineRepository fineRepository;
	
	 public FineService(FineRepository fineRepository) {
	        this.fineRepository = fineRepository;
	    }
	 
	 // GET ALL FINES
	    public List<FineDTO> getAllFines() {

	        return fineRepository.findAll()
	                .stream()
	                .map(this::convertToDTO)
	                .toList();
	    }
	    
	    // GET FINE BY BORROWING ID
	    public FineDTO getFineByBorrowing(Long borrowingId) {

	        Fine fine = fineRepository.findByBorrowingId(borrowingId)
	                .orElseThrow(() ->
	                        new FineNotFoundException(
	                                "Fine not found for borrowing id: "
	                                + borrowingId));

	        return convertToDTO(fine);
	    }
	    
	    // MARK FINE AS PAID
	    public FineDTO markFineAsPaid(Long fineId) {

	        Fine fine = fineRepository.findById(fineId)
	                .orElseThrow(() ->
	                        new FineNotFoundException(
	                                "Fine not found with id: " + fineId));
	        
	        if (fine.isPaid()) {
	            throw new FineAlreadyPaidException(
	                    "Fine has already been paid");
	        }

	        fine.setPaid(true);

	        Fine updatedFine = fineRepository.save(fine);

	        return convertToDTO(updatedFine);
	    }
	    
	    public List<FineDTO> getFinesByMember(Long memberId) {

	        return fineRepository.findByBorrowingMemberId(memberId)
	                .stream()
	                .map(this::convertToDTO)
	                .toList();
	    }
	    
	    // ENTITY -> DTO
	    private FineDTO convertToDTO(Fine fine) {

	        FineDTO dto = new FineDTO();

	        dto.setId(fine.getId());
	        dto.setBorrowingId(fine.getBorrowing().getId());
	        dto.setAmount(fine.getAmount());
	        dto.setPaid(fine.isPaid());

	        return dto;
	    }
}
