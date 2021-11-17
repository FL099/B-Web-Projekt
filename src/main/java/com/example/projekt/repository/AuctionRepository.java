package com.example.projekt.repository;

import com.example.projekt.model.Auction;
import org.springframework.data.repository.CrudRepository;

public interface AuctionRepository extends CrudRepository<Auction, Integer> {
}
