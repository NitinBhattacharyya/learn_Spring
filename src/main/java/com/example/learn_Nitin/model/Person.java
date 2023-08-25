package com.example.learn_Nitin.model;

import com.example.learn_Nitin.annotation.FieldsValueMatch;
import com.example.learn_Nitin.annotation.PasswordValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@FieldsValueMatch.List({

        @FieldsValueMatch(
                field="email",
                fieldMatch = "confirmEmail",
                message="Email addresses do not match"
        ),
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message="Passwords do not match!"
        )
})
public class Person extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native",strategy = "native")
    private int personID;

    @NotBlank(message="Name must not be blank")
    @Size(min=3,message="Name must be atleast 3 characters")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message="Confirm Email must not be blank")
    @Email(message = "Please provide a valid confirm email address" )
    @Transient
    private String confirmEmail;

    @NotBlank(message="Password must not be blank")
    @Size(min=5, message="Password must be at least 5 characters long")
    @PasswordValidator
    private String pwd;

    @NotBlank(message="Confirm Password must not be blank")
    @Size(min=5, message="Confirm Password must be at least 5 characters long")
    @Transient
    private String confirmPwd;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST, targetEntity = Roles.class)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id",nullable = false)
    private Roles roles;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId",nullable = true)
    private Address address;
}
