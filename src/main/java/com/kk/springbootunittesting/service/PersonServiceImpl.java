package com.kk.springbootunittesting.service;

import com.kk.springbootunittesting.domain.Person;
import com.kk.springbootunittesting.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kuriakose V
 * @since 20/08/19.
 */
@Service
@AllArgsConstructor
@NoArgsConstructor
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person getPersonByAadhar(String aadharId) throws Exception {
        if(aadharId == null)
            throw new Exception("Invalid AadharId");

        return personRepository.findByAadharId(aadharId);
    }
}
