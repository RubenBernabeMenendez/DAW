package com.santatecla.G1.citation;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonView;
import com.santatecla.G1.book.Book;
import com.santatecla.G1.book.BookService;
import com.santatecla.G1.theme.Theme;

@RestController
@RequestMapping("/api")
public class CitationRestController {
	interface CitationDetailView extends Citation.BasicView, Citation.BookView, Citation.ThemeView, Book.BasicView, Theme.BasicView {}
	
	@Autowired
	private CitationService citationService;

	@Autowired
	private BookService bookService;


	@JsonView(Citation.BasicView.class)
	@RequestMapping(value="/citation", method = GET)
	public Collection<Citation> citations(){
		return citationService.findAll();
	}
	
	@JsonView(CitationDetailView.class)
	@RequestMapping(value="/citation", method = POST)
	public ResponseEntity<Citation> saveCitation(Model model, Citation citation,@RequestParam Long bookId) {
		citationService.save(citation);
		if(bookId!=null) {
			Book book = bookService.findOne(bookId);
			if(book!=null) {
				book.addCitations(citation);
				citation.setBook(book);
				bookService.save(book);
			}
			else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		citationService.save(citation);
		model.addAttribute("text","Citation Created");
		return new ResponseEntity<>(citation, HttpStatus.OK);
	}
	
	@JsonView(CitationDetailView.class)
	@RequestMapping(value = "/citation/{id}", method = GET)
	public ResponseEntity<Citation> citation(Model model, @PathVariable long id){
		Citation citation = citationService.findById(id);
		if(citation!=null) {
			System.out.println(citation.toString());
			Book book = citation.getBook();
			Theme theme = citation.getTheme();
			model.addAttribute("Citation", citation);
			model.addAttribute("Book", book);
			model.addAttribute("Theme", theme);
			return new ResponseEntity<>(citation, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@JsonView(CitationDetailView.class)
	@RequestMapping(value="/citation/{id}", method=PATCH)
	public ResponseEntity<Citation> updateCitation(Model model, Citation newCitation, @PathVariable long id, Long bookId) {
		Citation oldCitation=citationService.findById(id);
		if(oldCitation!=null) {
			oldCitation.update(newCitation);
			if(bookId!=null) {
				Book book = bookService.findOne(bookId);
				if(book!=null) {
					book.addCitations(oldCitation);
					oldCitation.setBook(book);
					bookService.save(book);
				}else
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			citationService.save(oldCitation);		
			model.addAttribute("text","Cita editada de forma correcta");
			return new ResponseEntity<>(oldCitation, HttpStatus.OK);
		}
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@JsonView(CitationDetailView.class)
	@RequestMapping(value="/citation/{id}", method = DELETE)
	public ResponseEntity<Citation> deleteCitation(Model model, @PathVariable long id) {
		Citation citation = citationService.findById(id);
		if (citation!=null) {
			model.addAttribute("Citation", citation);
			model.addAttribute("text","Cita eliminada de forma correcta");
			citationService.deleteById(id);	
			return new ResponseEntity<>(citation, HttpStatus.OK);
		}else 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}