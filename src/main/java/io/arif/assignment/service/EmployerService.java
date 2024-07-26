package io.arif.assignment.service;

import io.arif.assignment.model.Employer;

import java.util.List;

public interface EmployerService {

    Employer createEmployer(Employer employer);

    List<Employer> getAllEmployers();

    Employer getEmployer(Long id);

    Employer updateEmployer(Long id, Employer employer);

    void deleteEmployer(Long id);

}
