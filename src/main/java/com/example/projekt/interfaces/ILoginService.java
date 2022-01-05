package com.example.projekt.interfaces;

import com.example.projekt.data.model.Auth;
import org.springframework.web.bind.annotation.RequestBody;

public interface ILoginService {

    String checkToken(@RequestBody String token);
    String checkLogin(@RequestBody Auth auth);
}
