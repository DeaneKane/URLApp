package com.deane.urlapp.controller;

import java.net.URI;
import java.time.LocalDateTime;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deane.urlapp.dao.WebAddressRepo;
import com.deane.urlapp.exception.ErrorResponseDetails;
import com.deane.urlapp.exception.InvalidWebAddressExpception;
import com.deane.urlapp.model.WebAddress;
import com.deane.urlapp.utils.WebAddressUtilities;

@RestController
@RequestMapping("/")
public class WebAddressController {

	@Autowired
	private WebAddressRepo webAddressRepo;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("address")
	public WebAddress createUrl(@RequestBody WebAddress webAddress) {

		String originalAddress = webAddress.getOriginalAddress();
		if (originalAddress.startsWith("www.")) {
			originalAddress = WebAddressUtilities.prependHttp(originalAddress);
		}

		UrlValidator urlValidator = new UrlValidator(new String[] { "http", "https" });

		if (!urlValidator.isValid(originalAddress)) {
			throw new InvalidWebAddressExpception("URL Provided is Invalid - " + webAddress.getOriginalAddress());
		}
		webAddress.setOriginalAddress(originalAddress);
		webAddress.setCreatedDate(LocalDateTime.now());
		webAddress.setShortAddress(WebAddressUtilities.transformToShortUrl(originalAddress));

		return this.webAddressRepo.save(webAddress);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("{shortAddress}")
	public ResponseEntity<WebAddress> getOriginalFromShortAddress(
			@PathVariable(value = "shortAddress") String shortAddress) {

		WebAddress webAddress = webAddressRepo.findByShortAddress(shortAddress);
		
		if(webAddress == null) {
			throw new InvalidWebAddressExpception("Short Address Provided is Invalid - " + shortAddress);
		}

		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(webAddress.getOriginalAddress())).build();
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponseDetails> handleInvalidWebAddException(InvalidWebAddressExpception iwae) {

		ErrorResponseDetails erd = new ErrorResponseDetails(LocalDateTime.now(), iwae.getMessage(),
				HttpStatus.I_AM_A_TEAPOT.value());

		return new ResponseEntity<>(erd, HttpStatus.I_AM_A_TEAPOT);
	}

}
