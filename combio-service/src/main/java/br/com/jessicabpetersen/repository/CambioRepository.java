package br.com.jessicabpetersen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jessicabpetersen.model.Cambio;

public interface CambioRepository extends JpaRepository<Cambio, Long>{

	Cambio findByFromAndTo(String from, String to);
	
}
