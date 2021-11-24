package com.example.projekt.controller;

import com.example.projekt.model.Auth;
import com.example.projekt.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import static com.example.projekt.util.crypt.getSHA256;
import static com.example.projekt.util.crypt.isValid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class LoginController {

    @GetMapping
    public @ResponseBody String index(){
        return "";
    }

    @PostMapping("/check")
    public String checkToken(@RequestBody String token){
        return JwtUtil.verifyToken(token);
    }

    @PostMapping
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
