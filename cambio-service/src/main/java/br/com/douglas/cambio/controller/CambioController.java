package br.com.douglas.cambio.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.douglas.cambio.model.Cambio;
import br.com.douglas.cambio.repository.CambioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cambio Service API")
@RestController
@RequestMapping("/cambio-service")
public class CambioController {
	
	private Logger logger = LoggerFactory.getLogger(CambioController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CambioRepository cambioRepository;
	
	@Operation(summary = "Get cambio from currency")
	@GetMapping("/{amount}/{from}/{to}")
	public Cambio cambio(@PathVariable("amount") BigDecimal amount, @PathVariable("from") String from, @PathVariable("to") String to) {
		
		logger.info("cambio is called with ->> {}, {} and {}", amount, from, to);
		
		Cambio cambio = cambioRepository.findByFromAndTo(from, to);
		
		if (cambio == null) {
			throw new RuntimeException("Currency unsupported");
		}
		
		String port = environment.getProperty("local.server.port");
		cambio.setEnvironment(port);
		
		BigDecimal convertedValue = cambio.getConversionFactor().multiply(amount);
		cambio.setConvertedValue(convertedValue);
		
		return cambio;
	}
	
}
