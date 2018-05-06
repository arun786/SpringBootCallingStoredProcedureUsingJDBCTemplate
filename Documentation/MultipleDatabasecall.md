# Multiple database call 
## sql having two database

    1. tcrmd00
    2. projectdb
    
## Properties file 

    spring.d1.datasource.jdbc-url: jdbc:mysql://localhost:3306/tcrmd00
    spring.d1.datasource.username: root
    spring.d1.datasource.password: root
    spring.d1.datasource.driver-class-name: com.mysql.jdbc.Driver
    
    spring.d2.datasource.jdbc-url: jdbc:mysql://localhost:3306/projectdb
    spring.d2.datasource.username: root
    spring.d2.datasource.password: root
    spring.d2.datasource.driver-class-name: com.mysql.jdbc.Driver

## Database Config

    package com.example.demo.config;
    
    import org.springframework.beans.factory.annotation.Qualifier;
    import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.boot.jdbc.DataSourceBuilder;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.jdbc.core.JdbcTemplate;
    
    import javax.sql.DataSource;
    
    /**
     * Created by Adwiti on 5/5/2018.
     */
    @Configuration
    public class DatabaseConfig {
    
        @Bean(name = "db1DataSource")
        @Qualifier("db1DataSource")
        @ConfigurationProperties(prefix = "spring.d1.datasource")
        public DataSource database1DataSource() {
            return DataSourceBuilder.create().build();
        }
    
        @Bean(name = "db1JdbcTemplate")
        @Qualifier("db1DataSource")
        public JdbcTemplate db1JdbcTemplate(@Qualifier("db1DataSource") DataSource db1DataSource) {
            return new JdbcTemplate(db1DataSource);
    
        }
    
        @Bean(name = "db2DataSource")
        @Qualifier("db2DataSource")
        @ConfigurationProperties(prefix = "spring.d2.datasource")
        public DataSource database2DataSource() {
            return DataSourceBuilder.create().build();
        }
    
        @Bean(name = "db2JdbcTemplate")
        @Qualifier("db2DataSource")
        public JdbcTemplate db2JdbcTemplate(@Qualifier("db2DataSource") DataSource db1DataSource) {
            return new JdbcTemplate(db1DataSource);
    
        }
    }

## Stored Procedure 

    package com.example.demo.storedprocedure;
    
    import com.example.demo.rowmapper.CustomerRowMapper;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.core.SqlParameter;
    import org.springframework.jdbc.core.SqlReturnResultSet;
    import org.springframework.jdbc.object.StoredProcedure;
    
    import java.sql.Types;
    import java.util.HashMap;
    import java.util.Map;
    
    /**
     * Created by Adwiti on 5/5/2018.
     */
    public class CustomerStoredProcedure extends StoredProcedure {
        public CustomerStoredProcedure(JdbcTemplate jdbcTemplate){
            super(jdbcTemplate, "getCustomer");
            CustomerRowMapper customerRowMapper = new CustomerRowMapper();
            declareParameter(new SqlReturnResultSet("customer", customerRowMapper));
            declareParameter(new SqlParameter("id", Types.VARCHAR));
        }
    
        public Map<String,Object> getCustomer(String id){
            Map<String,Object> in = new HashMap<>();
            in.put("id",id);
            Map<String,Object> out = execute(in);
            return out;
        }
    }

    /**
     * 
     */
    package com.example.demo.storedprocedure;
    
    import com.example.demo.rowmapper.CountryRowMapper;
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


    /**
     * 
     */
    package com.example.demo.rowmapper;
    
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

    package com.example.demo.rowmapper;
    
    import com.example.demo.model.Customer;
    import org.springframework.jdbc.core.RowMapper;
    
    import java.sql.ResultSet;
    import java.sql.SQLException;
    
    /**
     * Created by Adwiti on 5/5/2018.
     */
    public class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new Customer();
            customer.setId(rs.getString("id"));
            customer.setName(rs.getString("name"));
            customer.setEmail(rs.getString("email"));
            return customer;
        }
    }

## DAO layer

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
