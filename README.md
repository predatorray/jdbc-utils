jdbc-utils
==========

jdbc-utils is a simple collection of utilities for JDBC API, which simplifies the use of native JDBC.

## Examples

### JdbcTemplate

When using native JDBC API, we have to get a connection from a data source firstly, construct a `PreparedStatement` instance with placeholders, fill in the missing arguments, and finally execute a query or an update. Moreover, handling the `SQLException` thrown by a JDBC method invocation makes the code hard to read and maintain. It is redundant and not straightforward.

A `JdbcTemplate` instance, which is constructed with a data source instance, is a simple utility used to interact with the data source to retrieve or update some entity objects. You don't need to use JDBC directly. Instead, an instance of `JdbcTemplate` and a `DataMapper<T>` instance of the entity type is all you need.

```
...
JdbcTemplate jdbcTemplate = new JdbcTemplate(employeeDataSource); // construct a jdbcTemplate with a data source
...
```

A `DataMapper<T>` maps a `ExtendedResultSet` instance (which extends the function of the JDBC `ResultSet`) to your entity (say, Employee).

```
DataMapper employeeDataMapper = new DataMapper<Employee>() {
    @Override
    public Employee map(ExtendedResultSet rs) throws SQLException {
        Integer empNo = rs.getNullableInt("emp_no"); // get an integer from the result set. It returns null when it is SQL NULL.
        Date birthDate = rs.getDate("birth_date");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        Gender gender = Gender.toGender(rs.getString("gender"));
        Date hireDate = rs.getDate("hire_date");
        return new Employee(empNo, birthDate, firstName, lastName, gender,
                hireDate);
    }
}
```

The following snippet shows how to use a `JdbcTemplate` to retrieve a list of employees from the database.

```
List<Employee> tenEmployees = jdbcTemplate.query("SELECT * FROM employees LIMIT ?, ?;", Arrays.asList(0, 10),
    employeeDataMapper);
```

And this snippet show how to update an employee entity to the database.

```
Employee bob = jdbcTemplate.updateOne("UPDATE employees SET firstName=? WHERE empNo=?;", Arrays.asList("Bob", 1001),
    employeeDataMapper);
```

After the `UPDATE` is successfully executed, the updated entity will be returned.
