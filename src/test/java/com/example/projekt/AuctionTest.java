package com.example.projekt;

import com.example.projekt.data.model.Auction;
import com.example.projekt.dto.AuctionDto;
import com.example.projekt.interfaces.IAuctionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuctionTest {

    @Autowired
    private IAuctionService auctionService;


    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testSaveAndGetAndDelete() {
        Auction auction = new Auction("Wieselburger",50000, 123);
        auction = auctionService.createAuction(auction);

        // clear the persistence context, so we don't return the previously cached object
        entityManager.clear();

        AuctionDto foundAuction = auctionService.getAuction(auction.getId());
        assertEquals("Wieselburger", foundAuction.getProduct());

        auctionService.deleteAuction(foundAuction.getId());
    }
}
