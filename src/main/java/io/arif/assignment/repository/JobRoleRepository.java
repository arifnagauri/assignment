package io.arif.assignment.repository;

import io.arif.assignment.model.JobRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobRoleRepository extends CrudRepository<JobRole, Long> {

    List<JobRole> findByEmployerId(Long id);
    void deleteByEmployerId(Long id);

}
