package com.kk.springbootunittesting.controller;

import com.kk.springbootunittesting.domain.Person;
import com.kk.springbootunittesting.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kuriakose V
 * @since 20/08/19.
 */
@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/aadhar")
    public ResponseEntity getPersonByAadharId(@Param("aadharId") String aadharId) {
        Person person =null;
        try {
            person = personService.getPersonByAadhar(aadharId);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(person, HttpStatus.OK);
    }
}
