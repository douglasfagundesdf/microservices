package br.com.douglas.book.proxy;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.douglas.book.response.CambioDTO;

@FeignClient(name = "cambio-service", path = "/cambio-service")
public interface CambioProxy {
	
	@GetMapping("/{amount}/{from}/{to}")
	CambioDTO cambio(@PathVariable("amount") BigDecimal amount, @PathVariable("from") String from, @PathVariable("to") String to);
	
}
