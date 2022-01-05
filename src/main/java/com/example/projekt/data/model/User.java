package com.example.projekt.data.model;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import com.example.projekt.util.Role;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @NotNull(message = "Vorname darf nicht leer sein")
    private String firstName;

    @NotNull(message = "Nachname darf nicht leer sein")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "ungültiges Email Format")
    @NotNull(message = "Email darf nicht leer sein")
    private String email;

    @NotNull(message = "Passwort darf nicht leer sein")
    private String password;

    private Integer plz;

    private String address;

    private Boolean locked;

    private Role roles;

    private String profilepic;  //Evt zu URL machen, relative URL vom uploads oder Pics Ordner aus

    public User(String firstName, String lastName, String email, String password){
        this(firstName, lastName, email, password, null, null);
    }

    public User(String firstName, String lastName, String email, String password, Integer plz, String address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.plz = plz;
    }

    public User(){

        this.roles = Role.USER;
        this.profilepic = "avatar.png";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPlz() {
        return plz;
    }

    public void setPlz(Integer plz) {
        this.plz = plz;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Role getRole() {
        return roles;
    }

    public void setRole(Role role) {
        roles = role;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
