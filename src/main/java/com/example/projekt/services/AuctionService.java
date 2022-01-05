package com.example.projekt.services;

import com.example.projekt.dto.AuctionDto;
import com.example.projekt.dto.OfferDto;
import com.example.projekt.interfaces.IAuctionService;
import com.example.projekt.model.Auction;
import com.example.projekt.model.Offer;
import com.example.projekt.repository.AuctionRepository;
import com.example.projekt.repository.OfferRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AuctionService implements IAuctionService {

    private final ModelMapper modelMapper = new ModelMapper();
    private final AuctionRepository auctionRepository;
    private final OfferRepository offerRepository;

    public AuctionService(AuctionRepository auctionRepository, OfferRepository offerRepository){
        this.auctionRepository = auctionRepository;
        this.offerRepository = offerRepository;
    }

    @Override
    public Iterable<AuctionDto> getAuctions() {
        return modelMapper.map(auctionRepository.findAll(),
                new TypeToken<List<AuctionDto>>(){}.getType());
    }

    @Override
    public AuctionDto getAuction(int id) {
        return modelMapper.map(getAuctionFromRepoOrThrow(id), AuctionDto.class);
    }

    @Override
    public Auction createAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    @Override
    public OfferDto addOfferForAuction(int id, OfferDto offerDto) {
        Auction existingAuction = getAuctionFromRepoOrThrow(id);

        Offer offer = modelMapper.map(offerDto, Offer.class);

        // check ranges (amount, price, ...)
        //existingAuction.throwIfOutsideRanges(offer);

        //TODO: ändern (wenn creatorId auf User geändert wurde
        offer.setCreatorId(0); //userRepository.findByEmailContaining("erika@musterfrau.at").get(0)); // TODO: get user via authentication
        offer.setAuctionId(0);//existingAuction);
        offerRepository.save(offer);

        return modelMapper.map(offer, OfferDto.class);
    }

    @Override
    public AuctionDto updateAuction(AuctionDto auctionDto, Integer id) {
        Auction existingAuction = getAuctionFromRepoOrThrow(id);

        Auction auction = modelMapper.map(auctionDto, Auction.class);

        auction.setId(existingAuction.getId());
        auction.setCreatorId(existingAuction.getCreatorId());
        auction.setProduct(existingAuction.getProduct());

        return modelMapper.map(auctionRepository.save(auction), AuctionDto.class);
    }

    @Override
    public AuctionDto deleteAuction(int id) {
        AuctionDto existingAuctionDto = getAuction(id);
        auctionRepository.deleteById(id);
        return existingAuctionDto;
    }

    @Override
    public Auction getAuctionFromRepoOrThrow(int id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
