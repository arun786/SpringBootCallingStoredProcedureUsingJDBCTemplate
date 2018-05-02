/**
 * 
 */
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Country;
import com.example.demo.service.CountryService;

/**
 * @author Adwiti
 *
 */
@RestController
public class CountryRestController {
	@Autowired
	private CountryService countryService;

	@GetMapping("/country/{id}")
	public ResponseEntity<Country> getAllCountry(@PathVariable String id) {
		Country countries = countryService.getACountry(id);
		return new ResponseEntity<>(countries, HttpStatus.OK);
	}

}
