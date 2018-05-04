# StoredProcedure With Single Row Mapper


## Call a Stored Procedure when out parameter is a Result set.

### 1 Create a Stored Procedure as below
 
     CREATE DEFINER=`root`@`localhost` PROCEDURE `getCountry`(in `country_id` varchar(10))
     BEGIN
     select id, name, capital, currency from country where `id` = country_id;
     END
 
### 2 Create a Rowmapper

    /**
     * 
     */
    package com.example.demo.config;
    
    import com.example.demo.model.Country;
    import org.springframework.jdbc.core.RowMapper;
    
    import java.sql.ResultSet;
    import java.sql.SQLException;
    
    /**
     * @author Adwiti
     *
     */
    public class CountryRowMapper implements RowMapper<Country> {
    
    	@Override
    	public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
    		Country country = new Country();
    		country.setId(rs.getString(1));
    		country.setName(rs.getString(2));
    		country.setCapital(rs.getString(3));
    		country.setCurrency(rs.getString(4));
    		return country;
    	}
    }

### 3 Create a Stored Procedure 

    /**
     * 
     */
    package com.example.demo.storedprocedure;
    
    import com.example.demo.config.CountryRowMapper;
    import com.example.demo.model.Country;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.core.RowMapper;
    import org.springframework.jdbc.core.SqlParameter;
    import org.springframework.jdbc.core.SqlReturnResultSet;
    import org.springframework.jdbc.object.StoredProcedure;
    
    import java.sql.Types;
    import java.util.HashMap;
    import java.util.Map;
    
    /**
     * @author Adwiti
     *
     */
    public class CountryStoredProcedure extends StoredProcedure {
    
    	public CountryStoredProcedure(JdbcTemplate jdbcTemplate) {
    		/**
    		 * This will be the name of the stored procedure, getCountry
    		 */
    		super(jdbcTemplate, "getCountry");
    		RowMapper<Country> rowMapper = new CountryRowMapper();
    		declareParameter(new SqlReturnResultSet("country", rowMapper));
    		declareParameter(new SqlParameter("id", Types.VARCHAR));
    		compile();
    	}
    
    	@SuppressWarnings({ "unchecked", "rawtypes" })
    	public Map getCountry(String id) {
    		Map in = new HashMap<>();
    		in.put("id", id);
    		Map out = execute(in);
    		return out;
    	}
    }
    
### 4 call the stored procedure from DAO layer

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
