package com.kk.springbootunittesting.controller;

import com.kk.springbootunittesting.domain.Person;
import com.kk.springbootunittesting.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kuriakose V
 * @since 20/08/19.
 */
@RestController
@RequestMapping("/api/v1/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/{aadharId}")
    public ResponseEntity<?> getPersonByAadharId(@PathVariable(value = "aadharId", required = false) String aadharId) {
        Person person =null;
        try {
            person = personService.getPersonByAadhar(aadharId);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(person);
    }
}
