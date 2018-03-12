/**
 * 
 */
package com.example.demo.service;

import java.util.List;

import com.example.demo.country.Country;

/**
 * @author Adwiti
 *
 */
public interface ICountryService {

	public List<Country> getAllCountry(String id);
	
	public Country getACountry(String id);

}
