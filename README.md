# Export Data as CSV using OpenCSV
Project used for exporting data as csv file using OpenCSV.


## Description

Employee list can be populated by using `employees_request.json` file and corresponding endpoint.

Spring Boot provides some properties with which we can add the exception message, exception class, or even a stack trace as part of the response payload:

```
server.error.include-message=always
server.error.include-binding-errors=always
server.error.include-stacktrace=on_param
server.error.include-exception=false
```

Using these Spring Boot server properties in our `application.properties` we can alter the error response to some extent.

Note that we’ve set the property `include-stacktracee` to `on_trace_param` which means that only if we include the trace param in the URL (`?trace=true`), we’ll get a stack trace in the response payload:

We might want to keep the value of `include-stacktrace` flag to `never`, at least in production, as it might reveal the internal workings of our application.


## Getting Started

### Dependencies

* Spring Web
* Spring Boot
* Spring Data JPA
* OpenCSV
* H2 Database

### Installing

* Database connection url for DBeaver, etc.

```
jdbc:h2:tcp://localhost:9092/mem:employee-db
```

### API Endpoints

All URIs are relative to *http://localhost:8080/api/v1*

Class | Method                                                        | HTTP request         | Description
------------ |---------------------------------------------------------------|----------------------| -------------
*EmployeeController* | [**create**](http://localhost:8080/api/v1/employees)          | **POST** /employees  | Create an employee
*EmployeeController* | [**createFromFile**](http://localhost:8080/api/v1/employees/{fileName:.+}) | **POST** /employees/{fileName:.+} | Create employees from given JSON file
*EmployeeController* | [**importFromCsv**](http://localhost:8080/api/v1/employees/import/{fileName:.+}) | **POST** /employees/import/{fileName:.+} | Import employees from given CSV file
*EmployeeController* | [**exportToCsv**](http://localhost:8080/api/v1/employees/export/{fileName:.+})   | **GET** /employees/export/{fileName:.+} | Export employees to CSV file
*EmployeeController* | [**findByEmail**](http://localhost:8080/api/v1/employees/{email}) | **GET** /employees/{email} | Get employee by email
*EmployeeController* | [**findAll**](http://localhost:8080/api/v1/employees)         | **GET** /employees   | Get all employees
*EmployeeController* | [**deleteById**](http://localhost:8080/api/v1/employees/{id}) | **DELETE** /employees/{id} | Delete employee by id
*EmployeeController* | [**deleteAll**](http://localhost:8080/api/v1/employees)       | **DELETE** /employees | Delete all employees


## Documentation
[Access a File from the Classpath in a Spring Application](https://www.baeldung.com/spring-classpath-file-access#3-using-resourceloader)<br/>


## Authors
Murat Yıldız


## Version History

* 0.2
  * Various bug fixes and optimizations
  * See [commit change]() or See [release history]()
* 0.1
  * Initial Release


## License

...


## Acknowledgements
...