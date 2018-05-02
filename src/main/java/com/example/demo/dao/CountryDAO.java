/**
 * 
 */
package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.Country;

/**
 * @author Adwiti
 *
 */
public interface CountryDAO {

	public List<Country> getAllCountry(String id);
	
	public Country getACountry(String id);

}
