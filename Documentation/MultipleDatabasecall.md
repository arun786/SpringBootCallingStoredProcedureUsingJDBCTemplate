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
