package br.com.jessicabpetersen.client;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.jessicabpetersen.response.Cambio;



@FeignClient(name = "cambio-service")
public interface CambioClient {

	@GetMapping(value= "/cambio-service/{amount}/{from}/{to}")
	Cambio getCambioEnd(@PathVariable("amount") Double amount,
							 @PathVariable("from") String from,
							 @PathVariable("to") String to
							);
}
