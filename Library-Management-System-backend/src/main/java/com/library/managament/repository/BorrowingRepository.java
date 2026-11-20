package com.library.managament.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.library.managament.entity.Borrowing;
import com.library.managament.entity.BorrowingStatus;

public interface BorrowingRepository extends  JpaRepository<Borrowing, Long>{

	 List<Borrowing> findByMemberId(Long memberId);
	 
	 List<Borrowing> findByBookId(Long bookId);
	 
	 boolean existsByMemberIdAndBookIdAndStatus(
		        Long memberId,Long bookId,
		        BorrowingStatus status);
	 
	 List<Borrowing> findByStatusAndDueDateBefore(
		        BorrowingStatus status,
		        LocalDate date);
	 
	 List<Borrowing> findByDueDateBeforeAndStatus(
		        LocalDate date,
		        BorrowingStatus status);
	 
	 boolean existsByMemberIdAndStatus(
		        Long memberId,
		        BorrowingStatus status);
	 
	 List<Borrowing> findByStatus(BorrowingStatus status);
	 
	 @Query("""
			    SELECT b FROM Borrowing b
			    WHERE (:status IS NULL OR b.status = :status)
			    AND (:memberId IS NULL OR b.member.id = :memberId)
			    AND (:bookId IS NULL OR b.book.id = :bookId)
			""")
			Page<Borrowing> searchBorrowings(
			        @Param("status") BorrowingStatus status,
			        @Param("memberId") Long memberId,
			        @Param("bookId") Long bookId,
			        Pageable pageable);
}


