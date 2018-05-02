/**
 * 
 */
package com.example.demo.service;

import com.example.demo.model.Country;

import java.util.List;

/**
 * @author Adwiti
 *
 */
public interface CountryService {

	public List<Country> getAllCountry(String id);
	
	public Country getACountry(String id);

}
