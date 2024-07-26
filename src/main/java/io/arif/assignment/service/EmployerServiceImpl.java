package io.arif.assignment.service;

import io.arif.assignment.model.Employer;
import io.arif.assignment.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployerServiceImpl implements EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    @Override
    public Employer createEmployer(Employer employer) {
        return employerRepository.save(employer);
    }

    @Override
    public List<Employer> getAllEmployers() {
        List<Employer> employers = new ArrayList<>();
        employerRepository.findAll().forEach(employers::add);
        return employers;
    }

    @Override
    @Cacheable(cacheNames = "employers", key = "#id")
    public Employer getEmployer(Long id) {
        if (id == null)
            return null;

        return employerRepository.findById(id).orElse(null);
    }

    @Override
    @CachePut(cacheNames = "employers", key = "#id")
    public Employer updateEmployer(Long id, Employer employer) {
        Employer existingEmployer = getEmployer(id);
        if (existingEmployer == null)
            return createEmployer(employer);

        employer.setId(existingEmployer.getId());
        return employerRepository.save(employer);
    }

    @Override
    @CacheEvict(cacheNames = "employers", key = "#id")
    public void deleteEmployer(Long id) {
        employerRepository.deleteById(id);
    }
}
