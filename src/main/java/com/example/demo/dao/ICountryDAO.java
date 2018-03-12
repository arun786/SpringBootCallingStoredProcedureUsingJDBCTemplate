/**
 * 
 */
package com.example.demo.dao;

import java.util.List;

import com.example.demo.country.Country;

/**
 * @author Adwiti
 *
 */
public interface ICountryDAO {

	public List<Country> getAllCountry(String id);
	
	public Country getACountry(String id);

}
