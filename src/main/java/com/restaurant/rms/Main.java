/*
Project:    Restaurant-management system (RMS)
Started:    22.04.25
Updated:    10.07.25

GitHub Repo
https://github.com/ammaar0x01/Project3_RMS
---

Responsibilities
-------------------------------------------------------------------------
Name and surname    | Classes 										   	|
-------------------------------------------------------------------------
Ammaar Swartland    | Employee, Order, OrderDetail     					|
Salaamah			| CustomerGroup, CustomerGroupPayment, Reservation 	|
Kyran 				| User, EmployeeSalary								|
Leslie				| Table, MenuItem									|
Esethu				| Expenses, EmployeeShift							|
-------------------------------------------------------------------------
*/

package com.restaurant.rms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
