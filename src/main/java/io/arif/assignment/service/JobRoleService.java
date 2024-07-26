package io.arif.assignment.service;

import io.arif.assignment.model.JobRole;

import java.util.List;

public interface JobRoleService {

    List<JobRole> getAllJobRoles();

    JobRole getJobRole(Long id);

    /**
     * Ideally, JobRole entity cannot exist without an employer.
     * So, avoid this method unless JobRole entity contains {@link JobRole#employer} field initialized.
     * @param jobRole jobRole with employer field set, <b>null</b> if employer is not set
     */
    JobRole createJobRole(JobRole jobRole);

    /**
     * JobRole should not be an independent entity, it should contain an employer
     * So, job roles should be created only using this method.
     * @param id employerId
     * @param jobRole jobRole
     * @return persisted JobRole entity if employer with given id exist, <b>null</b> otherwise
     */
    JobRole createJobRole(Long id, JobRole jobRole);

    /**
     * @param id if of the job-role
     * @param jobRole jobe-role
     * @return updated JobRole if job-role with given id exist otherwise newly created job-role for employer,
     * may return <b>null</b> if employer with given id does not exist.
     */
    JobRole updateJobRole(Long id, JobRole jobRole);

    void deleteJobRole(Long id);

    List<JobRole> getAllJobRolesOfEmployer(Long id);

    void deleteAllJobRolesOfEmployer(Long id);

}
