package com.example.projekt.controller;

import com.example.projekt.model.Auth;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.MessageDigest;

import static com.example.projekt.util.crypt.getSHA256;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class LoginController {

    @GetMapping
    public @ResponseBody String index(){
        return "";
    }

    @PostMapping
    public String checkLogin(@RequestBody Auth auth){
        String newHash = getSHA256(auth.getPassword());

        //Todo: vglWert als Datenbankabfrage
        String vglWert = "7dfc0aa32bb5dda2a049f6bd0a3c3419e86b3d7622494c57030944d814233d03";

        if (newHash.equals(vglWert)){
            return "success ";
        }else {
            return "fail, du Hund";
        }

    }
}
