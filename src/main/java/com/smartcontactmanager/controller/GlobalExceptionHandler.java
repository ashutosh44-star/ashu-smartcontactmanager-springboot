package com.smartcontactmanager.controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle 404 Errors (Page Not Found)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(Exception ex, Model model) {
        model.addAttribute("message", "Page not found!");
        return "login";  // Returns an error.html page
    }

    // Handle Thymeleaf Template Errors
    @ExceptionHandler(org.thymeleaf.exceptions.TemplateInputException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleTemplateError(Exception ex, Model model) {
        model.addAttribute("message", "Template Not Found!");
        return "login";  // Returns an error.html page
    }

    // Handle Generic Errors
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralError(Exception ex, Model model) {
        model.addAttribute("message", "An unexpected error occurred!");
        return "login";  // Returns an error.html page
    }
}