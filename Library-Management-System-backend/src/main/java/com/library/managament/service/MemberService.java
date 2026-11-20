package com.library.managament.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.library.managament.dto.MemberDTO;
import com.library.managament.entity.BorrowingStatus;
import com.library.managament.entity.Member;
import com.library.managament.exception.DuplicateEmailException;
import com.library.managament.exception.MemberDeletionException;
import com.library.managament.exception.MemberNotFoundException;
import com.library.managament.repository.BorrowingRepository;
import com.library.managament.repository.MemberRepository;

@Service
public class MemberService {

	 private final MemberRepository memberRepository;
	 private final BorrowingRepository borrowingRepository;

	 public MemberService(
		        MemberRepository memberRepository,
		        BorrowingRepository borrowingRepository) {

		    this.memberRepository = memberRepository;
		    this.borrowingRepository = borrowingRepository;
		}
	    
	    public MemberDTO saveMember(MemberDTO memberDTO) {
	    	
	    	if (memberRepository.existsByEmail(memberDTO.getEmail())) {
	    	    throw new DuplicateEmailException(
	    	            "Member already exists with email: "
	    	            + memberDTO.getEmail());
	    	}

	        Member member = new Member();

	        member.setName(memberDTO.getName());
	        member.setEmail(memberDTO.getEmail());

	        Member savedMember = memberRepository.save(member);

	        return convertToDTO(savedMember);
	    }
	    
	    public List<MemberDTO> getAllMembers() {

	        return memberRepository.findAll()
	                .stream()
	                .map(this::convertToDTO)
	                .toList();
	    }
	    
	    public MemberDTO getMemberById(Long id) {

	        Member member = memberRepository.findById(id)
	                .orElseThrow(() ->
	                        new MemberNotFoundException(
	                                "Member not found with id: " + id));

	        return convertToDTO(member);
	    }
	    
	    public MemberDTO updateMember(Long id, MemberDTO updatedMember) {

	        Member existingMember = memberRepository.findById(id)
	                .orElseThrow(() ->
	                        new MemberNotFoundException(
	                                "Member not found with id: " + id));

	        existingMember.setName(updatedMember.getName());
	        existingMember.setEmail(updatedMember.getEmail());

	        if (memberRepository.existsByEmailAndIdNot(
	                updatedMember.getEmail(), id)) {

	            throw new DuplicateEmailException(
	                    "Member already exists with email: "
	                    + updatedMember.getEmail());
	        }
	        
	        Member savedMember = memberRepository.save(existingMember);
	        
	        return convertToDTO(savedMember);
	    }
	    
	    public void deleteMember(Long id) {

	        Member member = memberRepository.findById(id)
	                .orElseThrow(() ->
	                        new MemberNotFoundException(
	                                "Member not found with id: " + id));

	        boolean hasActiveBorrowing =
	                borrowingRepository.existsByMemberIdAndStatus(
	                        id,
	                        BorrowingStatus.BORROWED);

	        if (hasActiveBorrowing) {
	            throw new MemberDeletionException(
	                    "Cannot delete member because the member "
	                    + "has an active borrowing");
	        }

	        memberRepository.delete(member);
	    }
	    
	    private MemberDTO convertToDTO(Member member) {

	        MemberDTO dto = new MemberDTO();

	        dto.setId(member.getId());
	        dto.setName(member.getName());
	        dto.setEmail(member.getEmail());

	        return dto;
	    }
}
