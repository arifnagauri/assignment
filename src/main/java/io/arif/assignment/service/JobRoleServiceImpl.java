package io.arif.assignment.service;

import io.arif.assignment.model.Employer;
import io.arif.assignment.model.JobRole;
import io.arif.assignment.repository.JobRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobRoleServiceImpl implements JobRoleService {

    @Autowired
    private JobRoleRepository jobRoleRepository;
    @Autowired
    private EmployerService employerService;

    @Override
    public List<JobRole> getAllJobRoles() {
        List<JobRole> jobRoles = new ArrayList<>();
        jobRoleRepository.findAll().forEach(jobRoles::add);
        return jobRoles;
    }

    @Override
    @Cacheable(cacheNames = "jobRoles", key = "#id")
    public JobRole getJobRole(Long id) {
        return jobRoleRepository.findById(id).orElse(null);
    }

    @Override
    public JobRole createJobRole(JobRole jobRole) {
        if (jobRole.getEmployer() == null)
            return null;
        return createJobRole(jobRole.getEmployer().getId(), jobRole);
    }

    @Override
    public JobRole createJobRole(Long id, JobRole jobRole) {
        Employer employer = employerService.getEmployer(id);
        if (employer == null)
            return null;

        jobRole.setEmployer(employer);
        return jobRoleRepository.save(jobRole);
    }

    @Override
    @CachePut(cacheNames = "jobRoles", key = "#id")
    public JobRole updateJobRole(Long id, JobRole jobRole) {
        JobRole existingJobRole = getJobRole(id);
        if (existingJobRole == null)
            return createJobRole(jobRole);

        jobRole.setId(existingJobRole.getId());
        return jobRoleRepository.save(jobRole);
    }

    @Override
    @CacheEvict(cacheNames = "jobRoles", key = "#id")
    public void deleteJobRole(Long id) {
        jobRoleRepository.deleteById(id);
    }

    @Override
    public List<JobRole> getAllJobRolesOfEmployer(Long id) {
        return jobRoleRepository.findByEmployerId(id);
    }

    @Override
    public void deleteAllJobRolesOfEmployer(Long id) {
        jobRoleRepository.deleteByEmployerId(id);
    }
}
