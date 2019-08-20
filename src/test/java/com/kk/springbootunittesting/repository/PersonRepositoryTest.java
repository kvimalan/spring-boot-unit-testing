package com.kk.springbootunittesting.repository;

import com.kk.springbootunittesting.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Kuriakose V
 * @since 20/08/19.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void find_by_first_name() throws Exception {
        this.entityManager.persist(new Person("Kuriakose", "Vimalanathan", UUID.randomUUID().toString()));
        Person person = this.personRepository.findByFirstName("Kuriakose");
        assertThat(person.getFirstName()).isEqualTo("Kuriakose");
    }

    @Test
    public void find_by_last_name() throws Exception {
        this.entityManager.persist(new Person("Kuriakose", "Vimalanathan", UUID.randomUUID().toString()));
        Person person = this.personRepository.findByLastName("Vimalanathan");
        assertThat(person.getLastName()).isEqualTo("Vimalanathan");
    }

    @Test
    public void find_by_aadhar_id() {
        String aadharId = UUID.randomUUID().toString();
        this.entityManager.persist(new Person("Kuriakose", "Vimalanathan",aadharId ));
        Person person = this.personRepository.findByAadharId(aadharId);
        assertNotNull(person);
    }

    @Test
    public void find_by_invalid_aadhar_id() {
        String aadharId = UUID.randomUUID().toString();
        this.entityManager.persist(new Person("Kuriakose", "Vimalanathan",aadharId ));
        Person person = this.personRepository.findByAadharId(UUID.randomUUID().toString());
        assertNull(person);
    }
}
