package com.example.projekt.data.repository;

import com.example.projekt.data.model.Auction;
import org.springframework.data.repository.CrudRepository;

public interface IAuctionRepository extends CrudRepository<Auction, Integer> {
}
