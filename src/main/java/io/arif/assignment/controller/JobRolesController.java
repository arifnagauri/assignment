package io.arif.assignment.controller;

import io.arif.assignment.model.JobRole;
import io.arif.assignment.service.JobRoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class JobRolesController extends RestControllerBase {

    @Autowired
    private JobRoleService jobRoleService;

    /**
     * Ideally, JobRole entity cannot exist without an employer.
     * So, avoid this endpoint unless sending employer payload (at least with employer_id) inside JobRole.
     */
    @PostMapping("/jobroles")
    public ResponseEntity<JobRole> createJobRole(@Valid @RequestBody JobRole jobRole) {
        try {
            JobRole savedJobRole = jobRoleService.createJobRole(jobRole);
            if (savedJobRole == null)
                return ResponseEntity.unprocessableEntity().build();

            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Location", "/api/jobroles/" + savedJobRole.getId())
                    .body(savedJobRole);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/jobroles")
    public ResponseEntity<List<JobRole>> getJobRoles() {
        List<JobRole> jobRoles = jobRoleService.getAllJobRoles();

        if (jobRoles.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(jobRoles);
    }

    @GetMapping("/jobroles/{id}")
    public ResponseEntity<JobRole> getJobRole(@PathVariable("id") Long id) {
        JobRole jobRole = jobRoleService.getJobRole(id);

        if (jobRole == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(jobRole);
    }

    @PutMapping("/jobroles/{id}")
    public ResponseEntity<JobRole> updateJobRole(@PathVariable("id") Long id, @Valid @RequestBody JobRole jobRole) {
        try {
            JobRole updatedJobRole = jobRoleService.updateJobRole(id, jobRole);
            if (updatedJobRole == null)
                return ResponseEntity.unprocessableEntity().build();

            return ResponseEntity.ok(updatedJobRole);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/jobroles/{id}")
    public ResponseEntity<Void> deletedJobRole(@PathVariable("id") Long id) {
        jobRoleService.deleteJobRole(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * This endpoint should be used for job-role creation for data consistency
     */
    @PostMapping("/employers/{id}/jobroles")
    public ResponseEntity<JobRole> createJobRoleForEmployer(@PathVariable("id") Long id, @RequestBody JobRole jobRole) {
        try {
            JobRole savedJobRole = jobRoleService.createJobRole(id, jobRole);
            if (savedJobRole == null)
                return ResponseEntity.notFound().build();

            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Location", "/api/employers/" + id +"/jobroles/" + savedJobRole.getId())
                    .body(savedJobRole);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/employers/{id}/jobroles")
    public ResponseEntity<List<JobRole>> getAllJobRolesOfEmployer(@PathVariable("id") Long id) {
        List<JobRole> jobRoles = jobRoleService.getAllJobRolesOfEmployer(id);

        if (jobRoles.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(jobRoles);
    }

    @DeleteMapping("/employers/{id}/jobroles")
    public ResponseEntity<Void> deleteAllJobRolesOfEmployer(@PathVariable("id") Long id) {
        jobRoleService.deleteAllJobRolesOfEmployer(id);
        return ResponseEntity.noContent().build();
    }
}
