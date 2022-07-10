package com.github.yildizmy;

import com.github.yildizmy.dto.request.EmployeeRequest;
import com.github.yildizmy.service.EmployeeService;
import org.h2.tools.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Start internal H2 server so we can query the DB from IDE
	 *
	 * @return H2 Server instance
	 * @throws SQLException
	 */
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
	}

	@Bean
	public CommandLineRunner populateDatabase(EmployeeService employeeService) {
		return (args -> {
			List<EmployeeRequest> employees = new ArrayList<>();

			//create employees
			employees.add(new EmployeeRequest("Johan Thurbon", "jthurbon@gmail.com", "United States", 29));
			employees.add(new EmployeeRequest("Margaretta Daintrey", "mdaintrey@gmail.com", "Sweden", 32));
			employees.add(new EmployeeRequest("Jude Sapseed", "jsapseedb@gmail.com", "Netherlands", 26));
			employees.add(new EmployeeRequest("Parrnell Wrintmore", "pwrintmore@gmail.com", "Germany", 33));
			employees.add(new EmployeeRequest("Jeniffer Doherty", "jdoherty@gmail.com", "Colombia", 24));
			employees.add(new EmployeeRequest("Susana Burgoin", "sburgoin@gmail.com", "UK", 27));
			employees.add(new EmployeeRequest("Dacia Mellmoth", "dmellmoth@gmail.com", "Estonia", 36));
			employees.add(new EmployeeRequest("Lucas Egdale", "legdale@gmail.com", "Italy", 25));
			employees.add(new EmployeeRequest("Kristien Mingotti", "kmingotti@gmail.com", "Finland", 34));
			employees.add(new EmployeeRequest("Jaclyn Chismon", "jchismon@gmail.com", "Spain", 37));
			employeeService.create(employees);
		});
	}
}
