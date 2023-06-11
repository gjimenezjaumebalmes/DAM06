package com.guillemjimenez.repositories;

import com.guillemjimenez.model.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Departments, Integer> {
    boolean existsByDeptNo(int deptNo);
}