jdbc-utils [![Build Status](https://travis-ci.org/predatorray/jdbc-utils.png?branch=master)](https://travis-ci.org/predatorray/jdbc-utils) [![Coverage Status](https://coveralls.io/repos/predatorray/jdbc-utils/badge.png)](https://coveralls.io/r/predatorray/jdbc-utils)
==========

**jdbc-utils** is a simple collection of utilities for JDBC API,
which simplifies the use of native JDBC.

## Introduction

### JdbcTemplate

When using native JDBC API, we have to get a connection from a data source
firstly, construct a `PreparedStatement` instance with placeholders, fill in
the missing arguments, and finally execute a query or an update. Moreover,
handling the `SQLException` thrown by a JDBC method invocation makes the code
hard to read and maintain. It is redundant and not straightforward.

A `JdbcTemplate` instance, which is constructed with a data source instance, is
a simple utility used to interact with the data source to retrieve or update
some entity objects. You don't need to use JDBC directly. Instead, an instance
of `JdbcTemplate` and a `DataMapper<T>` instance of the entity type is all you
need.

```
// construct a jdbcTemplate with a data source
JdbcTemplate jdbcTemplate = new JdbcTemplate(employeeDataSource);
```

A `DataMapper<T>` maps a `ExtendedResultSet` instance (which extends the
function of the JDBC `ResultSet`) to your entity (say, Employee).

```
DataMapper employeeDataMapper = new DataMapper<Employee>() {
    @Override
    public Employee map(ExtendedResultSet rs) throws SQLException {
        Integer empNo = rs.getNullableInt("emp_no"); // Get an integer from the result set.
                                                     // It returns null when it is SQL NULL.
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

The following snippet shows how to use a `JdbcTemplate` to retrieve a list of
employees from the database.

```
List<Employee> tenEmployees = jdbcTemplate.query(
    "SELECT * FROM employees LIMIT ?, ?;", Arrays.<Object>asList(0, 10),
    employeeDataMapper);
```

And this snippet show how to update an employee entity to the database.

```
Employee bob = jdbcTemplate.updateOne(
    "UPDATE employees SET firstName=? WHERE emp_no=?;",
    Arrays.<Object>asList("Bob", 1001),
    employeeDataMapper);
```

After the `UPDATE` is successfully executed, the updated entity will be
returned.

### A collection of data sources

There're many useful data source classes under the
`me.predatorray.jdbc.datasource` package. All those data sources follow the
Decorator pattern, which means that they share the same interface `DataSource`
and are able to combine with each other.

#### CloseOnCompletionDataSource

All connections constructed by a `CloseOnCompletionDataSource` instance will be
closed sequentially as soon as the data source has been closed. Therefore, you
don't need to close a `Statement`, `PreparedStatement` or other kinds of
statement instances manually after their creation. All these dirty work will be
done by the `DataSource` instance.

#### LoadBalancingDataSource

A `LoadBalancingDataSource` instance balances the traffic coming from the upper
layer, redirecting them to a data source randomly or with any other strategies
like round-robin according to the `LoadBalancingStrategy`.

#### ReplicationDataSource & MasterSlaveDataSource

The master-slave is a simple and common database topology, which is used when
we have few writes and many reads in our applications. A
`ReplicationDataSource` instance returns a connection to the master on
`connection.setReadOnly(false);` or one to the slave on
`connection.setReadOnly(true)`. When we have one master and multiple slaves, it is a good choice to join those slaves into a `LoadBalancingDataSource` with a `RoundRobin` strategy or just use `MasterSlaveDataSource` instead.
