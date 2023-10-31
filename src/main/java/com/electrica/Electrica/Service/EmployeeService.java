package com.electrica.Electrica.Service;

import com.electrica.Electrica.DTO.EmployeeDTO;
import com.electrica.Electrica.DTO.LoginDTO;
import com.electrica.Electrica.Entity.Employee;
import com.electrica.Electrica.response.LoginResoponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface EmployeeService {
    String addEmployee(EmployeeDTO employeeDTO);

    LoginResoponse loginEmployee(LoginDTO loginDTO, HttpServletRequest request);

    List<Employee> getAllUsers();

    void deleteUser(String username);
}