package com.electrica.Electrica.RegisterControler;

import com.electrica.Electrica.DTO.EmployeeDTO;
import com.electrica.Electrica.DTO.LoginDTO;
import com.electrica.Electrica.Entity.Employee;
import com.electrica.Electrica.Exceptions.InvalidCredentialsException;
import com.electrica.Electrica.Service.EmployeeService;
import com.electrica.Electrica.response.LoginResoponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping(path = "/save")
    public ResponseEntity<Void> saveEmployee(@RequestBody EmployeeDTO employeeDTO)
    {
        employeeService.addEmployee(employeeDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginEmployee(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        try {
            LoginResoponse loginResponse = employeeService.loginEmployee(loginDTO, request);
            return ResponseEntity.ok(loginResponse);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<Employee>> getAllUsers() {
        List<Employee> users = employeeService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        employeeService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }
}