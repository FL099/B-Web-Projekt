package com.example.projekt.controller;

import com.example.projekt.model.Auth;
import com.example.projekt.model.User;
import com.example.projekt.repository.UserRepository;
import com.example.projekt.util.JwtUtil;

import org.springframework.web.bind.annotation.*;
//import org.hibernate.search.jpa.FullTextEntityManager;
//import org.hibernate.search.jpa.FullTextQuery;
//import org.hibernate.search.jpa.Search;

import javax.persistence.NoResultException;

import java.util.List;

import static com.example.projekt.util.crypt.getSHA256;
import static com.example.projekt.util.crypt.isValid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class LoginController {

    private UserRepository userRepository;

    public LoginController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping
    public @ResponseBody String index(){
        return "";
    }

    @PostMapping("/check")
    public String checkToken(@RequestBody String token){
        return JwtUtil.verifyToken(token);
    }

    @PostMapping
    public String test2CheckLogin(@RequestBody Auth auth){
        String newHash = getSHA256(auth.getPassword());

        String token = JwtUtil.generateToken(auth);


        User user = null;

        try {
            for (User u : userRepository.findByEmailContaining(auth.getEmail())) {
                System.out.println(u.getPassword()+ ".....");
                user = u;

            }

            //zum testen statt DB Abfrage hashwert statt email
            String vglWert = user.getPassword();

            if (isValid(vglWert, newHash)){
                //return "Login erfolgreich \n eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ik1heCBNdXN0ZXJtYW5uIiwiaWF0IjoxNjM4MDM5MDIyfQ.tjA107F7gW21ImFN0XHTxPgruG2iNqr-8z99byBjji0";
                return token;
            }else {
                return "Login fehlgeschlagen: falsche Username/Passwort Kombination  \n" + newHash + "\n" + vglWert;
            }
            /*String hqlString = "SELECT us.email, us.password FROM user us WHERE us.email ='"+ auth.getEmail()+ "'";
            Query hqlQuery = session.createQuery(hqlString);
            List resultSet = hqlQuery.list();
            String PWquery = "SELECT us.password FROM Users us";
            user = (User) entityManager.createQuery("from User u where u.username = :username")
                    .setParameter("username", auth.getEmail())
                    .getSingleResult();*/
        } catch (NoResultException e)  {
            user = null;
        }

        return "Something went wrong";
    }

    @PostMapping("/test")
    public String checkLogin(@RequestBody Auth auth){
        String newHash = getSHA256(auth.getPassword());

        String token = JwtUtil.generateToken(auth);

        //Todo: vglWert als Datenbankabfrage
        String vglWert = "7dfc0aa32bb5dda2a049f6bd0a3c3419e86b3d7622494c57030944d814233d03";

        if (isValid(vglWert, newHash)){
            //return "Login erfolgreich \n eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ik1heCBNdXN0ZXJtYW5uIiwiaWF0IjoxNjM4MDM5MDIyfQ.tjA107F7gW21ImFN0XHTxPgruG2iNqr-8z99byBjji0";
            return token;
        }else {
            return "Login fehlgeschlagen: falsche Username/Passwort Kombination  ";
        }

    }


}
