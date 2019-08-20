package com.kk.springbootunittesting.service;

import com.kk.springbootunittesting.domain.Person;
import com.kk.springbootunittesting.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author Kuriakose V
 * @since 20/08/19.
 */
@RunWith(MockitoJUnitRunner.class) //Initialize all the mock objects
public class PersonServiceTest {

    private PersonService systemUnderTest;

    @Mock
    PersonRepository personRepository;

    @Before
    public void setUp(){
        systemUnderTest=new PersonServiceImpl(personRepository);
    }


    @Test(expected = Exception.class)
    public void when_aadhar_value_passed_as_empty_throws_exception() throws Exception {
        //when
        systemUnderTest.getPersonByAadhar(null);
        //then
        verify(personRepository, never()).findByAadharId(anyString());
    }

    @DisplayName("Get Person by an AadharId")
    @Test
    public void get_person_by_aadharId() throws Exception {
        String aadharId = UUID.randomUUID().toString();
        Person person = new Person("KK", "Vimalanathan", aadharId);
        //given
        given(personRepository.findByAadharId(anyString())).willReturn(person);

        //when
        systemUnderTest.getPersonByAadhar(aadharId);

        //then
        verify(personRepository, times(1)).findByAadharId(anyString());
    }

}
