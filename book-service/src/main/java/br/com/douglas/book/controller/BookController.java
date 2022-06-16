package br.com.douglas.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.douglas.book.model.Book;
import br.com.douglas.book.proxy.CambioProxy;
import br.com.douglas.book.repostory.BookRepository;
import br.com.douglas.book.response.CambioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping("book-service")
public class BookController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CambioProxy cambioProxy;
	
	@Operation(summary = "Find a specific book by your ID")
	@GetMapping("/{id}/{currency}")
	public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {
		
		Book book = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Book not found at id " + id));
		
		String port = environment.getProperty("local.server.port");
		
		CambioDTO cambioDTO = cambioProxy.cambio(book.getPrice(), "USD", currency);
		
		book.setPrice(cambioDTO.getConvertedValue());
		book.setEnvironment("book: " + port + " cambio: " + cambioDTO.getEnvironment());
		
		return book;
	}
	
}
