package com.example.projekt;

import com.example.projekt.exceptions.Exceptionhandler;
import com.example.projekt.data.model.Offer;
import com.example.projekt.data.repository.IOfferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/")
public class IndexController {

    private IOfferRepository offerRepository;
    private Exceptionhandler exHandler;

    public IndexController(IOfferRepository offerRepository){
        this.offerRepository = offerRepository;
    }

    @GetMapping()
    public @ResponseBody String index(){
        return "{\"Hint\": \"You can access offers with /offers , auctions with /auctions and Information about authentication with /auth \"}";
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody @Valid Offer offer){
        return "{\"Hint\": \"You can access offers with /offers , auctions with /auctions and Information about authentication with /auth \"}";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return exHandler.handleGeneralExceptions(ex);
    }
}