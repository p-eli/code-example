package db.entity;

import auth.PasswordHasher;
import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Project: Airport
 * File: User.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    public User() {
        type = EmployeeType.Other;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull
    @Column(unique = true)
    @Size(min = 1, message = "Toto pole je povinné")
    private String login;

    @NotNull
    @Size(min = 1, message = "Toto pole je povinné")
    private String firstname;

    @NotNull
    @Size(min = 1, message = "Toto pole je povinné")
    private String lastname;

    private String phone;

    private EmployeeType type;

    private String email;

    @Nullable
    @Temporal(TemporalType.DATE)
    private Date birthday;


    private String address;

    //@NotNull
    //@Size(max = 25, min = 8, message = "Heslo musí mít délku od {min} do {max} znaků")
    private String password;

    @OneToMany()
    private Collection<PlaneModel> models;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Collection<PlaneModel> getModels() {
        return models;
    }

    public void setModels(Collection<PlaneModel> models) {
        this.models = models;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password
                = password == null || password.equals("")
                ? null
                : PasswordHasher.Hash(login, password);
    }

    public void setHash(String hash) {
        this.password = hash;
    }

    public String getFullName() {
        return this.firstname + " " + this.lastname;
    }

    @Override
    public String toString() {
        return getFullName();
    }


}
