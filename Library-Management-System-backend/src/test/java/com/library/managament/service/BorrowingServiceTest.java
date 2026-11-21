package com.library.managament.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.library.managament.entity.Book;
import com.library.managament.entity.Borrowing;
import com.library.managament.entity.BorrowingStatus;
import com.library.managament.entity.Member;
import com.library.managament.repository.BookRepository;
import com.library.managament.repository.BorrowingRepository;
import com.library.managament.repository.FineRepository;
import com.library.managament.repository.MemberRepository;

class BorrowingServiceTest {

    @Test
    void includesBookTitleInBorrowingResponse() {
        BorrowingRepository borrowingRepository = mock(BorrowingRepository.class);
        BorrowingService borrowingService = new BorrowingService(
                borrowingRepository,
                mock(MemberRepository.class),
                mock(BookRepository.class),
                mock(FineRepository.class));

        Member member = new Member();
        member.setId(1L);
        Book book = new Book();
        book.setId(2L);
        book.setTitle("The Library");
        Borrowing borrowing = new Borrowing();
        borrowing.setId(3L);
        borrowing.setMember(member);
        borrowing.setBook(book);
        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setDueDate(LocalDate.now().plusDays(14));
        borrowing.setStatus(BorrowingStatus.BORROWED);
        when(borrowingRepository.findAll()).thenReturn(List.of(borrowing));

        var result = borrowingService.getAllBorrowings();

        assertEquals("The Library", result.get(0).getBookTitle());
        assertEquals(2L, result.get(0).getBookId());
    }
}
