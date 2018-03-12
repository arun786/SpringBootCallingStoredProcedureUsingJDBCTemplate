/**
 * 
 */
package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.country.Country;
import com.example.demo.dao.ICountryDAO;

/**
 * @author Adwiti
 *
 */
@Service
public class CountryService implements ICountryService {

	@Autowired
	private ICountryDAO iCountryDAO;

	@Override
	public List<Country> getAllCountry(String id) {
		return iCountryDAO.getAllCountry(id);
	}

	@Override
	public Country getACountry(String id) {
		return iCountryDAO.getACountry(id);
	}

}
