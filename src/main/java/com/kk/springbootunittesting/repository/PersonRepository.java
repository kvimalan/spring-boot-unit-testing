package com.kk.springbootunittesting.repository;

import com.kk.springbootunittesting.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kuriakose V
 * @since 20/08/19.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByFirstName(String firstName);

    Person findByLastName(String lastName);

    Person findByAadharId(String aadharId);
}

