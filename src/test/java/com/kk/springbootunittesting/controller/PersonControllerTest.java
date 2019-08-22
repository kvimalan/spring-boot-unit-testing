package com.kk.springbootunittesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.springbootunittesting.domain.Person;
import com.kk.springbootunittesting.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Kuriakose V
 * @since 22/08/19.
 */
@RunWith(SpringRunner.class)
@WebMvcTest
@ActiveProfiles("test")
@Slf4j
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void get_person_by_id() throws Exception {
        //given
        String aadharId = UUID.randomUUID().toString();
        Person person = new Person("KK", "Vimal", aadharId);

       when(personService.getPersonByAadhar(anyString())).thenReturn(person);

        // when + then
        ResultActions resultActions = this.mockMvc.perform(get("/api/v1/persons/"+aadharId))
                .andExpect(status().isOk());

        //Get response object
        MockHttpServletResponse mockHttpServletResponse = resultActions.andReturn().getResponse();
       log.info(mockHttpServletResponse.getContentAsString());

       //Convert string to object
        Person personResponse = objectMapper.readValue(mockHttpServletResponse.getContentAsString(), Person.class);

        //Assert
        Assert.assertTrue(personResponse.getAadharId().equalsIgnoreCase(aadharId));
    }

    @Test
    public void get_aadhar_id_passed_null() throws Exception {
        //given
        String aadharId = UUID.randomUUID().toString();
        Person person = new Person("KK", "Vimal", aadharId);

        when(personService.getPersonByAadhar(any())).thenThrow(new Exception("Invalid AadharId"));

        // when + then
        ResultActions resultActions = this.mockMvc.perform(get("/api/v1/persons/{aadharId}", "aadharId", null))
                .andExpect(status().isBadRequest());

    }



}
