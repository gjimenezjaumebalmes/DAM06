package com.guillemjimenez.controllers;


import com.guillemjimenez.exceptions.ResourceNotFoundException;
import com.guillemjimenez.model.Departments;
import com.guillemjimenez.model.Employees;
import com.guillemjimenez.repositories.DepartmentRepository;
import com.guillemjimenez.services.DepartmentService;
import com.guillemjimenez.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class mainController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getIndexPage(@ModelAttribute Employees employee) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public ModelAndView getEmployees(@ModelAttribute Employees employee) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Employees");
        modelAndView.addObject("employees", employeeService.getAllEmployees());
        return modelAndView;
    }

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    public ModelAndView getDepartments(@ModelAttribute Departments department) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Departments");
        modelAndView.addObject("departments", departmentService.getAllDepartments());
        return modelAndView;
    }

    @RequestMapping("/addEmployee")
    public ModelAndView showAddEmployeeForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addEmployee");
        modelAndView.addObject("employee", new Employees()); // Agrega el objeto employee al modelo
        return modelAndView;
    }

    @RequestMapping("/addDepartment")
    public ModelAndView showAddDepartmentForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addDepartment");
        modelAndView.addObject("department", new Departments()); // Agrega el objeto department al modelo
        return modelAndView;
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employees employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            return "addEmployee";
        } else {
            // Verificar si el departamento existe
            int departmentId = employee.getDepartment().getDeptNo();
            if (!departmentRepository.existsByDeptNo(departmentId)) {
                // Departamento no encontrado, agregar mensaje de error y redirigir al formulario
                bindingResult.rejectValue("department.deptNo", "error.department", "El departamento especificado no existe");
                return "addEmployee";
            }

            // El departamento existe, guardar el empleado
            employeeService.saveEmployee(employee);
            return "redirect:/employees";
        }
    }

    @PostMapping("/saveDepartment")
    public String saveDepartment(@ModelAttribute("department") Departments department, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            return "addDepartment";
        } else {
            departmentService.saveDepartment(department);
            return "redirect:/departments";
        }
    }

    @RequestMapping(value = "/deleteEmployee/{empNo}", method = RequestMethod.GET)
    public String deleteEmployee(@PathVariable("empNo") int empNo) throws ResourceNotFoundException {
        employeeService.deleteEmployeeById(empNo);
        return "redirect:/employees";
    }

    @RequestMapping(value = "/deleteDepartment/{deptNo}", method = RequestMethod.GET)
    public String deleteDepartment(@PathVariable("deptNo") int deptNo) throws ResourceNotFoundException {
        departmentService.deleteDepartmentById(deptNo);
        return "redirect:/departments";
    }

}