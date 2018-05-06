/**
 * 
 */
package com.example.demo.service;

import com.example.demo.dao.CountryDAO;
import com.example.demo.model.Country;
import com.example.demo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Adwiti
 *
 */
@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryDAO countryDAO;

	@Override
	public Customer getACustomer(String id) {
		return countryDAO.getACustomer(id);
	}

	@Override
	public Country getACountry(String id) {
		return countryDAO.getACountry(id);
	}

}
