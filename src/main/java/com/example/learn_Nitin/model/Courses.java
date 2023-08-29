package com.example.learn_Nitin.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Getter
@Setter
@Entity
public class Courses extends BaseEntity{
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private int courseId;

    private String name;

    private String fees;

//    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "courses",cascade = CascadeType.PERSIST)
//    private Set<Person> persons;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "courses",cascade = CascadeType.MERGE)
    private Set<Person> persons;
}
