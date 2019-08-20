package com.kk.springbootunittesting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Kuriakose V
 * @since 20/08/19.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "aadhar_id")
    private String aadharId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public Person(String firstName, String lastName, String aadharId){
        this.firstName = firstName;
        this.lastName = lastName;
        this.aadharId = aadharId;
    }
}
