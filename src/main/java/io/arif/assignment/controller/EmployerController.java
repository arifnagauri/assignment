package io.arif.assignment.controller;

import io.arif.assignment.model.Employer;
import io.arif.assignment.service.EmployerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employers")
public class EmployerController extends RestControllerBase {

    @Autowired
    private EmployerService employerService;

    @PostMapping
    public ResponseEntity<Employer> createEmployer(@Valid @RequestBody Employer employer) {
        try {
            Employer savedEmployer = employerService.createEmployer(employer);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Location", "/api/employers/" + savedEmployer.getId())
                    .body(savedEmployer);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Employer>> getAllEmployers() {
        List<Employer> employers = employerService.getAllEmployers();

        if (employers.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(employers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employer> getEmployer(@PathVariable("id") Long id) {
        Employer employer = employerService.getEmployer(id);

        if (employer == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(employer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employer> updateEmployer(@PathVariable("id") Long id, @Valid @RequestBody Employer employer) {
        try {
            Employer updatedEmployer = employerService.updateEmployer(id, employer);
            return ResponseEntity.ok(updatedEmployer);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletedEmployer(@PathVariable("id") Long id) {
        employerService.deleteEmployer(id);
        return ResponseEntity.noContent().build();
    }
}
