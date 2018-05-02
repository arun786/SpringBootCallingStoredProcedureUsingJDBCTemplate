/**
 *
 */
package com.example.demo.dao;

import com.example.demo.model.Country;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.storedprocedure.CountryStoredProcedure;
import org.springframework.beans.factory.annotation.Autowired;
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
    JdbcTemplate jdbcTemplate;
    private CountryStoredProcedure countryStoredProcedure;

    @PostConstruct
    public void postConstruct() {
        countryStoredProcedure = new CountryStoredProcedure(jdbcTemplate);
    }

    @Override
    public List<Country> getAllCountry(String id) {

        Map<String, Object> outMaps = countryStoredProcedure.getCountry(id);
        return null;
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
}
