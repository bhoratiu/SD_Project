package com.electrica.Electrica.Service.Impl;

import com.electrica.Electrica.DTO.EmployeeDTO;
import com.electrica.Electrica.DTO.LoginDTO;
import com.electrica.Electrica.Entity.Employee;
import com.electrica.Electrica.Exceptions.UserNotFoundException;
import com.electrica.Electrica.Repo.EmployeeRepo;
import com.electrica.Electrica.Service.EmployeeService;
import com.electrica.Electrica.response.LoginResoponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeIMPL implements EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee(
                employeeDTO.getEmployeeid(),
                employeeDTO.getEmployeename(),
                employeeDTO.getEmail(),
                this.passwordEncoder.encode(employeeDTO.getPassword()),
                "user",
                employeeDTO.getDeviceIds()
        );
        employeeRepo.save(employee);
        return employee.getEmployeename();
    }

    EmployeeDTO employeeDTO;

    @Override
    public LoginResoponse loginEmployee(LoginDTO loginDTO, HttpServletRequest request) {
        String msg = "";
        Employee employee1 = employeeRepo.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + loginDTO.getEmail()));
        ;
        if (employee1 != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = employee1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<Employee> employee = employeeRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (employee.isPresent()) {
                    return new LoginResoponse("Login Success", true, employee1.getType(), employee1.getEmployeeid());
                } else {
                    // request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, Integer.valueOf(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
                    return new LoginResoponse("Login Failed", false, "", 0);
                }
            } else {
                //request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, Integer.valueOf(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
                return new LoginResoponse("password Not Match", false, "", 0);
            }
        } else {
            // request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, Integer.valueOf(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
            return new LoginResoponse("Email not exits", false, "", 0);
        }
    }

    @Override
    public List<Employee> getAllUsers() {
        return employeeRepo.findAll();
    }

    @Override
    public void deleteUser(String username) {
        Employee employee = employeeRepo.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        employeeRepo.delete(employee);
    }
}