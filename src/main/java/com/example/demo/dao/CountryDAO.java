/**
 *
 */
package com.example.demo.dao;

import com.example.demo.model.Country;
import com.example.demo.model.Customer;

/**
 * @author Adwiti
 */
public interface CountryDAO {

    public Country getACountry(String id);

    public Customer getACustomer(String id);

}
