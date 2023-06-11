package com.guillemjimenez.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.guillemjimenez.model.Employees;

public interface EmployeeRepository extends JpaRepository<Employees, Integer> {

}