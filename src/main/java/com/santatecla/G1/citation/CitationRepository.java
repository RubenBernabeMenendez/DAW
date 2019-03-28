package com.santatecla.G1.citation;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santatecla.G1.book.Book;


public interface CitationRepository extends JpaRepository<Citation,Long>{
	
	Citation findByTextIgnoreCase(String text);
	List<Citation> findByText(String text);
	List<Citation> findCitationByBook(Book book);
	Citation findById(long id);
	List<Citation> findByTextContaining(String text);
	List<Citation> findByTextContaining(String text, Pageable page);
}
