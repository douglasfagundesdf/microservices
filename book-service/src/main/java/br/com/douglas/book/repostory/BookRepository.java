package br.com.douglas.book.repostory;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.douglas.book.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	
}
