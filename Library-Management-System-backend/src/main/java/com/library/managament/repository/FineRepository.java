package com.library.managament.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.managament.entity.Fine;

public interface FineRepository extends JpaRepository<Fine, Long>{

	Optional<Fine> findByBorrowingId(Long borrowingId);
	
	List<Fine> findByBorrowingMemberId(Long memberId);
}
