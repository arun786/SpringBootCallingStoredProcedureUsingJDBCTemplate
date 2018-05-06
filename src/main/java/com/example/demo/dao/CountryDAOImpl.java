/**
 *
 */
package com.example.demo.dao;

import com.example.demo.exception.DataNotFoundException;
import com.example.demo.model.Country;
import com.example.demo.model.Customer;
import com.example.demo.storedprocedure.CountryStoredProcedure;
import com.example.demo.storedprocedure.CustomerStoredProcedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author Adwiti
 */
@Repository
public class CountryDAOImpl implements CountryDAO {

    @Autowired
    @Qualifier("db1JdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("db2JdbcTemplate")
    private JdbcTemplate db2JdbcTemplate;


    private CountryStoredProcedure countryStoredProcedure;
    private CustomerStoredProcedure customerStoredProcedure;

    @PostConstruct
    public void postConstruct() {
        countryStoredProcedure = new CountryStoredProcedure(jdbcTemplate);
        customerStoredProcedure = new CustomerStoredProcedure(db2JdbcTemplate);
    }


    @SuppressWarnings("unchecked")
    @Override
    public Country getACountry(String id) {
        Map<String, Object> data = countryStoredProcedure.getCountry(id);
        List result = (List) data.get("country");
        if (result.size() > 0) {
            Country country = (Country) result.get(0);
            return country;
        } else {
            throw new DataNotFoundException("Data Not Present for id " + id);
        }
    }

    @Override
    public Customer getACustomer(String id) {
        Map<String, Object> data = customerStoredProcedure.getCustomer(id);
        List<Customer> customers = (List<Customer>) data.get("customer");
        System.out.println(customers);
        return customers.get(0);
    }
}
