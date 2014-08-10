package me.predatorray.jdbc.integration;

import me.predatorray.jdbc.DataMapper;
import me.predatorray.jdbc.ExtendedResultSet;

import java.sql.SQLException;
import java.util.Date;

public class EmployeeMapper implements DataMapper<Employee> {

    @Override
    public Employee map(ExtendedResultSet rs) throws SQLException {
        int empNo = rs.getInt("emp_no");
        Date birthDate = rs.getDate("birth_date");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        Employee.Gender gender = rs.getEnum("gender", Employee.Gender.class);
        Date hireDate = rs.getDate("hire_date");
        return new Employee(empNo, birthDate, firstName, lastName, gender, hireDate);
    }
}
