package com.library.managament.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.managament.dto.BorrowingDTO;
import com.library.managament.dto.FineDTO;
import com.library.managament.dto.MemberDTO;
import com.library.managament.service.BorrowingService;
import com.library.managament.service.FineService;
import com.library.managament.service.MemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberController {

	 private final MemberService memberService;
	 private final BorrowingService borrowingService;
	 private final  FineService fineService;

	 public MemberController(
		        MemberService memberService,
		        BorrowingService borrowingService,
		        FineService fineService) {

		    this.memberService = memberService;
		    this.borrowingService = borrowingService;
		    this.fineService = fineService;
		}

	    @PostMapping
	    public MemberDTO addMember(
	            @Valid @RequestBody MemberDTO memberDTO) {

	        return memberService.saveMember(memberDTO);
	    }
	    
	    @GetMapping
	    public List<MemberDTO> getAllMembers() {
	        return memberService.getAllMembers();
	    }
	    
	    @GetMapping("/{id}")
	    public MemberDTO getMemberById(@PathVariable Long id) {
	        return memberService.getMemberById(id);
	    }
	    
	    @PutMapping("/{id}")
	    public MemberDTO updateMember(
	            @PathVariable Long id,
	            @Valid @RequestBody MemberDTO updatedMember) {

	        return memberService.updateMember(id, updatedMember);
	    }
	    
	    @GetMapping("/{id}/fines")
	    public List<FineDTO> getMemberFines(
	            @PathVariable Long id) {

	        return fineService.getFinesByMember(id);
	    }
	    
	    @DeleteMapping("/{id}")
	    public void deleteMember(@PathVariable Long id) {
	        memberService.deleteMember(id);
	    }
	    
	    
	    @GetMapping("/{id}/borrowings")
	    public List<BorrowingDTO> getMemberBorrowings(
	            @PathVariable Long id) {

	        return borrowingService.getBorrowingsByMember(id);
	    }
}
