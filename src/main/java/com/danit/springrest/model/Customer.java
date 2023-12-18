package com.danit.springrest.model;

import com.danit.springrest.domain.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor //TODO: 14:14 17.12
@Setter
@Getter
@NamedEntityGraph(name = "customerWithAccountsAndEmployers",
        attributeNodes = {@NamedAttributeNode("accounts"), @NamedAttributeNode("employers")})
public class Customer extends AbstractEntity {
    @Id
    @Column(name = "customer_id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String passwordEncoded;

    @Column(name = "phone_numbers", nullable = false)
    private String phoneNumbers;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "age", nullable = false)
    private int age;

    @OneToMany
    @JoinColumn(name = "customer_id")
    private List<Account> accounts;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})


    @JoinTable(name = "customeremployment",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "employer_id"))
    private List<Employer> employers;


    @ManyToOne
    //@JoinColumn(name = "employer_id")
    private Employer employer;

    public Customer(String name, String email, int age) {
        this.id = getId();
        this.name = name;
        this.email = email;
        this.age = age;
    }
    public Customer(String name, String email, Integer age, List<Account> accounts, List<Employer> employers) {
        this.id = getId();
        this.name = name;
        this.email = email;
        this.age = age;
        this.accounts = accounts;
        this.employers = employers;
    }

}
