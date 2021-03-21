package ru.isu.i2kiselev.rxordermanager.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Employee;
import ru.isu.i2kiselev.rxordermanager.service.EmployeeService;

/**
 * RegistrationController. Allows to log in and register new employees
 * @author Ilya Kiselev
 */

@Controller
@Log4j2
public class RegistrationController {

    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(
            EmployeeService employeeService, PasswordEncoder passwordEncoder) {
        this.employeeService= employeeService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String redirectToLogin(){
        return "redirect:/login";
    }

    @GetMapping("")
    public String redirectToLogin2(){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("form", new Employee());
        log.debug("Returned registration form");
        return "registration";
    }

    @PostMapping("/register")
    public Mono<String> processRegistration(@ModelAttribute("form") Employee form, Model model) {
        return employeeService.saveEmployee(form.withEncodedPassword(passwordEncoder))
                .doOnNext(x->log.debug("Registration completed successful for user {} ", x::getId))
                .then(Mono.just("redirect:/login"));
    }
}
