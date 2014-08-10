package me.predatorray.jdbc.integration;

import me.predatorray.jdbc.JdbcTemplate;
import me.predatorray.jdbc.datasource.DriverManagerDataSource;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

public class JdbcTemplateSimpleIntegrationTest {

    private static final String H2_DRIVER = "org.h2.Driver";
    private static final String EMPLOYEE_DB_URL = "jdbc:h2:mem:employee;DATABASE_TO_UPPER=false;" +
            "INIT=RUNSCRIPT FROM 'classpath:employees-schema.sql'";

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws ClassNotFoundException {
        DataSource employeeDs = new DriverManagerDataSource(H2_DRIVER, EMPLOYEE_DB_URL);
        jdbcTemplate = new JdbcTemplate(employeeDs);
    }

    @Test
    public void testQuery1() {
        jdbcTemplate.query("SELECT * FROM employees WHERE emp_no=1", new EmployeeMapper());
    }
}
