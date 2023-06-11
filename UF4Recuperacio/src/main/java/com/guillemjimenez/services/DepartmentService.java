package com.guillemjimenez.services;

import com.guillemjimenez.exceptions.ResourceNotFoundException;
import com.guillemjimenez.model.Departments;
import com.guillemjimenez.repositories.DepartmentRepository;
import com.guillemjimenez.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Departments> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Departments getDepartmentById(int dept_no) throws ResourceNotFoundException {
        Optional<Departments> departments = departmentRepository.findById(dept_no);
        if (departments.isPresent()) {
            return departments.get();
        } else {
            throw new ResourceNotFoundException("Department not found with id: " + dept_no);
        }
    }

    public Departments saveDepartment(Departments departments) {
        return departmentRepository.save(departments);
    }

    public String validateDepartmentExists(int dept_no) {
        boolean departmentExists = departmentRepository.existsById(dept_no);
        if (departmentExists) {
            return "Department with id " + dept_no + " exists.";
        } else {
            return "Department with id " + dept_no + " does not exist.";
        }
    }

    public void deleteDepartmentById(long id) throws ResourceNotFoundException {
        Optional<Departments> department = departmentRepository.findById((int) id);
        if (department.isPresent()) {
            departmentRepository.delete(department.get());
        } else {
            throw new ResourceNotFoundException("Department not found with id: " + id);
        }
    }
}
