package br.com.jessicabpetersen.controller;

import java.util.Date;
import java.util.HashMap;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.jessicabpetersen.client.CambioClient;
import br.com.jessicabpetersen.model.Book;
import br.com.jessicabpetersen.repository.BookRepository;
import br.com.jessicabpetersen.response.Cambio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping(value="/book-service")
public class BookController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CambioClient client;
	
	@Operation(summary = "Find a specific book by your id")
	@GetMapping(value = "/{id}/{currency}")
	public Book findBook(@PathVariable("id") Long id,
						@PathVariable("currency") String currency) {
		
		var book = repository.getById(id);
		if(book == null) throw new RuntimeException("Book not found");
		
		Cambio cambio = client.getCambioEnd(book.getPrice(),"USD", currency);
		
		var port = environment.getProperty("local.server.port");
		book.setEnvironment("Book port:" +port+ " cambio port "+cambio.getEnvironment());
		book.setPrice(cambio.getConvertedValue());
		return book;
	}
	
	
	/**
	 * 
	old version
	 
	@GetMapping(value = "/{id}/{currency}")
	public Book findBook(@PathVariable("id") Long id,
						@PathVariable("currency") String currency) {
		
		var book = repository.getById(id);
		if(book == null) throw new RuntimeException("Book not found");
		
		HashMap<String, String> params = new HashMap<>();
		params.put("amount", book.getPrice().toString());
		params.put("from", "USD");
		params.put("to", currency);
		var cambio = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/"
				+"{amount}/{from}/{to}", 
				Cambio.class, 
				params);
		
		var port = environment.getProperty("local.server.port");
		book.setEnvironment(port);
		book.setPrice(cambio.getBody().getConvertedValue());
		return book;
	}
	*/

}
