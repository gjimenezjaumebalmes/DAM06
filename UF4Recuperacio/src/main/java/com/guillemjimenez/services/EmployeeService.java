package com.guillemjimenez.services;

import com.guillemjimenez.exceptions.ResourceNotFoundException;
import com.guillemjimenez.model.Employees;
import com.guillemjimenez.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employees> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employees getEmployeeById(int emp_no) throws ResourceNotFoundException {
        Optional<Employees> employees = employeeRepository.findById(emp_no);
        if (employees.isPresent()) {
            return employees.get();
        } else {
            throw new ResourceNotFoundException("Employee not found with id: " + emp_no);
        }
    }

    public Employees saveEmployee(Employees employees) {
        return employeeRepository.save(employees);
    }

    public void deleteEmployeeById(long id) throws ResourceNotFoundException {
        Optional<Employees> employee = employeeRepository.findById((int) id);
        if (employee.isPresent()) {
            employeeRepository.delete(employee.get());
        } else {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
    }
}
