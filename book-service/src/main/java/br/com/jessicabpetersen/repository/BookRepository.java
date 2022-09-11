package br.com.jessicabpetersen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jessicabpetersen.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
