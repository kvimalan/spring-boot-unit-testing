package com.kk.springbootunittesting.service;

import com.kk.springbootunittesting.domain.Person;

/**
 * @author Kuriakose V
 * @since 20/08/19.
 */
public interface PersonService {

    /**
     *
     *  GEt person by aadharId
     *
     * @param aadharId
     * @return
     */
    Person getPersonByAadhar(String aadharId) throws Exception;
}
