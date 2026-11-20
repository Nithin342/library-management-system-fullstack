package com.library.managament.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.library.managament.dto.BorrowingDTO;
import com.library.managament.entity.Book;
import com.library.managament.entity.Borrowing;
import com.library.managament.entity.BorrowingStatus;
import com.library.managament.entity.Fine;
import com.library.managament.entity.Member;
import com.library.managament.exception.AlreadyBorrowedException;
import com.library.managament.exception.AlreadyReturnedException;
import com.library.managament.exception.BookNotAvailableException;
import com.library.managament.exception.BookNotFoundException;
import com.library.managament.exception.BorrowingNotFoundException;
import com.library.managament.exception.InvalidPaginationException;
import com.library.managament.exception.InvalidSortFieldException;
import com.library.managament.exception.MemberNotFoundException;
import com.library.managament.repository.BookRepository;
import com.library.managament.repository.BorrowingRepository;
import com.library.managament.repository.FineRepository;
import com.library.managament.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
public class BorrowingService {
	
	    private static final double FINE_PER_DAY = 10.0;

	    private final BorrowingRepository borrowingRepository;
	    private final MemberRepository memberRepository;
	    private final BookRepository bookRepository;
	    private final FineRepository fineRepository;
	    
	    public BorrowingService(
	            BorrowingRepository borrowingRepository,
	            MemberRepository memberRepository,
	            BookRepository bookRepository,
	            FineRepository fineRepository) {

	        this.borrowingRepository = borrowingRepository;
	        this.memberRepository = memberRepository;
	        this.bookRepository = bookRepository;
	        this.fineRepository = fineRepository;
	    }
	    
	    @Transactional
	    public BorrowingDTO borrowBook(BorrowingDTO borrowingDTO) {

	        Member member = memberRepository.findById(
	                borrowingDTO.getMemberId())
	                .orElseThrow(() ->
	                new MemberNotFoundException(
	                	    "Member not found with id: " + borrowingDTO.getMemberId()));
	        
	        Book book = bookRepository.findById(
	                borrowingDTO.getBookId())
	                .orElseThrow(() ->
	                new BookNotFoundException(
	                        "Book not found with id: "
	                        + borrowingDTO.getBookId()));


	        boolean alreadyBorrowed =
	                borrowingRepository.existsByMemberIdAndBookIdAndStatus(
	                        borrowingDTO.getMemberId(),
	                        borrowingDTO.getBookId(),
	                        BorrowingStatus.BORROWED);

	        if (alreadyBorrowed) {
	            throw new AlreadyBorrowedException(
	                    "Member has already borrowed this book");
	        }


	        if (book.getAvailableQuantity() <= 0) {
	            throw new BookNotAvailableException(
	                    "Book is not available with id: "
	                    + borrowingDTO.getBookId());
	        }

	        book.setAvailableQuantity(
	                book.getAvailableQuantity() - 1);

	        bookRepository.save(book);

	        Borrowing borrowing = new Borrowing();

	        borrowing.setMember(member);
	        borrowing.setBook(book);
	        borrowing.setBorrowDate(LocalDate.now());
	        borrowing.setDueDate(LocalDate.now().plusDays(14));
	        borrowing.setStatus(BorrowingStatus.BORROWED);

	        Borrowing savedBorrowing =
	                borrowingRepository.save(borrowing);

	        return convertToDTO(savedBorrowing);
	    }
	    
	    @Transactional
	    public BorrowingDTO returnBook(Long borrowingId) {

	        Borrowing borrowing = borrowingRepository.findById(borrowingId)
	                .orElseThrow(() ->
	                    new BorrowingNotFoundException(
	                        "Borrowing not found with id: " + borrowingId));

	        if (BorrowingStatus.RETURNED.equals(borrowing.getStatus())) {
	            throw new AlreadyReturnedException(
	                    "Book has already been returned");
	        }

	        Book book = borrowing.getBook();

	        book.setAvailableQuantity(
	                book.getAvailableQuantity() + 1);

	        bookRepository.save(book);

	        // Calculate fine
	        long overdueDays = 0;
	        double fine = 0;

	        if (borrowing.getDueDate() != null
	                && borrowing.getDueDate().isBefore(LocalDate.now())) {

	            overdueDays = java.time.temporal.ChronoUnit.DAYS.between(
	                    borrowing.getDueDate(),
	                    LocalDate.now());

	            fine = overdueDays * FINE_PER_DAY;
	        }

	        borrowing.setReturnDate(LocalDate.now());
	        borrowing.setStatus(BorrowingStatus.RETURNED);

	        Borrowing updatedBorrowing =
	                borrowingRepository.save(borrowing);

	        // CREATE FINE
	        if (fine > 0) {

	            boolean fineExists =
	                    fineRepository.findByBorrowingId(
	                            updatedBorrowing.getId()).isPresent();

	            if (!fineExists) {

	                Fine fineEntity = new Fine();

	                fineEntity.setAmount(fine);
	                fineEntity.setPaid(false);
	                fineEntity.setBorrowing(updatedBorrowing);

	                fineRepository.save(fineEntity);
	            }
	        }

	        // Prepare response
	        BorrowingDTO dto = convertToDTO(updatedBorrowing);

	        dto.setOverdueDays(overdueDays);
	        dto.setFine(fine);

	        return dto;
	    }
	    
	    public List<BorrowingDTO> getAllBorrowings() {

	        return borrowingRepository.findAll()
	                .stream()
	                .map(this::convertToDTO)
	                .toList();
	    }
	    
	    public BorrowingDTO getBorrowingById(Long id) {

	        Borrowing borrowing = borrowingRepository.findById(id)
	                .orElseThrow(() ->
	                
	                new BorrowingNotFoundException(
	                        "Borrowing not found with id: " + id));

	        return convertToDTO(borrowing);
	    }
	    
	    public List<BorrowingDTO> getBorrowingsByMember(Long memberId) {

	        memberRepository.findById(memberId)
	                .orElseThrow(() ->
	                        new MemberNotFoundException(
	                                "Member not found with id: " + memberId));

	        return borrowingRepository.findByMemberId(memberId)
	                .stream()
	                .map(this::convertToDTO)
	                .toList();
	    }
	    
	    public List<BorrowingDTO> getBorrowingsByBook(Long bookId) {

	        bookRepository.findById(bookId)
	                .orElseThrow(() ->
	                        new BookNotFoundException(
	                                "Book not found with id: " + bookId));

	        return borrowingRepository.findByBookId(bookId)
	                .stream()
	                .map(this::convertToDTO)
	                .toList();
	    }
	    
	    public List<BorrowingDTO> getOverdueBorrowings() {

	        return borrowingRepository
	                .findByStatusAndDueDateBefore(
	                        BorrowingStatus.BORROWED,
	                        LocalDate.now())
	                .stream()
	                .map(this::convertToDTO)
	                .toList();
	    }
	    
	    public List<BorrowingDTO> getBorrowingsByStatus(BorrowingStatus status) {

	        return borrowingRepository.findByStatus(status)
	                .stream()
	                .map(this::convertToDTO)
	                .toList();
	    }
	    
	    public Page<BorrowingDTO> getBorrowingsWithPagination(
	            int page,
	            int size,
	            String sortBy,
	            String direction) {
	    	

	        validatePagination(page, size);
	    	validateSortField(sortBy);
	    	

	        Sort sort = direction.equalsIgnoreCase("desc")
	                ? Sort.by(sortBy).descending()
	                : Sort.by(sortBy).ascending();

	        Pageable pageable = PageRequest.of(page, size, sort);

	        return borrowingRepository.findAll(pageable)
	                .map(this::convertToDTO);
	    }
	    
	    public Page<BorrowingDTO> searchBorrowingsWithPagination(
	            BorrowingStatus status,
	            Long memberId,
	            Long bookId,
	            int page,
	            int size,
	            String sortBy,
	            String direction) {
	    	
	    	validatePagination(page, size);
	    	validateSortField(sortBy);

	        Sort sort = direction.equalsIgnoreCase("desc")
	                ? Sort.by(sortBy).descending()
	                : Sort.by(sortBy).ascending();

	        Pageable pageable = PageRequest.of(page, size, sort);

	        return borrowingRepository.searchBorrowings(
	                status,
	                memberId,
	                bookId,
	                pageable
	        ).map(this::convertToDTO);
	    }
	    
	    private void validateSortField(String sortBy) {

	        List<String> allowedFields = List.of(
	                "id",
	                "borrowDate",
	                "dueDate",
	                "returnDate",
	                "status"
	        );

	        if (!allowedFields.contains(sortBy)) {
	            throw new InvalidSortFieldException(
	                    "Invalid sort field: " + sortBy);
	        }
	    }
	    
	    private void validatePagination(int page, int size) {


	    	
	        if (page < 0) {
	            throw new InvalidPaginationException(
	                    "Page number cannot be negative");
	        }

	        if (size <= 0) {
	            throw new InvalidPaginationException(
	                    "Page size must be greater than 0");
	        }

	        if (size > 100) {
	            throw new InvalidPaginationException(
	                    "Page size cannot exceed 100");
	        }
	    }
	    
	    private BorrowingDTO convertToDTO(Borrowing borrowing) {

	        BorrowingDTO dto = new BorrowingDTO();

	        dto.setId(borrowing.getId());
	        dto.setMemberId(borrowing.getMember().getId());
	        dto.setBookId(borrowing.getBook().getId());
	        dto.setBorrowDate(borrowing.getBorrowDate());
	        dto.setReturnDate(borrowing.getReturnDate());
	        dto.setStatus(borrowing.getStatus().name());
	        dto.setDueDate(borrowing.getDueDate());
	        
	        if (borrowing.getDueDate() != null
	                && BorrowingStatus.BORROWED.equals(borrowing.getStatus())
	                && borrowing.getDueDate().isBefore(LocalDate.now())) {

	            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(
	                    borrowing.getDueDate(),
	                    LocalDate.now());

	            dto.setOverdueDays(overdueDays);
	            
	            double fine = overdueDays * FINE_PER_DAY;
	            dto.setFine(fine);
	            
	        } else {
	            dto.setOverdueDays(0);
	            dto.setFine(0);
	        }

	        return dto;
	    }
	    
}
