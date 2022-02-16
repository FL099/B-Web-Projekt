package com.example.projekt.services;

import com.example.projekt.interfaces.ILoginService;
import com.example.projekt.data.model.Auth;
import com.example.projekt.data.model.User;
import com.example.projekt.data.repository.IUserRepository;
import com.example.projekt.util.JwtUtil;
import com.example.projekt.util.Role;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

import static com.example.projekt.util.crypt.getSHA256;
import static com.example.projekt.util.crypt.isValid;

@Service
public class LoginService implements ILoginService {

    private final IUserRepository userRepository;

    public LoginService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public String checkToken(String token) {
        return JwtUtil.verifyToken(token);
    }

    @Override
    public String checkLogin(Auth auth) {
        String newHash = getSHA256(auth.getPassword());

        if(auth.getRole()==null){
            auth.setRole(Role.USER);
        }

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
            return "Bitte um Entschuldigung, es scheint interne Datenbank-Probleme zu geben";
        }
    }
}
