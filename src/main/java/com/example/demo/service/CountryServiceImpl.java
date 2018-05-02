/**
 * 
 */
package com.example.demo.service;

import com.example.demo.model.Country;
import com.example.demo.dao.CountryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Adwiti
 *
 */
@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryDAO countryDAO;

	@Override
	public List<Country> getAllCountry(String id) {
		return countryDAO.getAllCountry(id);
	}

	@Override
	public Country getACountry(String id) {
		return countryDAO.getACountry(id);
	}

}
