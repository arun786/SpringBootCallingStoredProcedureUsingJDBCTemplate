/**
 * 
 */
package com.example.demo.service;

import com.example.demo.model.Country;
import com.example.demo.model.Customer;

/**
 * @author Adwiti
 *
 */
public interface CountryService {

	public Customer getACustomer(String id);
	
	public Country getACountry(String id);

}
