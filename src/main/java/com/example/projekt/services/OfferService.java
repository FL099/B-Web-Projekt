package com.example.projekt.services;

import com.example.projekt.dto.OfferDto;
import com.example.projekt.interfaces.IOfferService;
import com.example.projekt.model.Offer;
import com.example.projekt.repository.OfferRepository;
import com.example.projekt.util.State;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OfferService implements IOfferService {

    private final OfferRepository offerRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public OfferService(OfferRepository offerRepository){
        this.offerRepository = offerRepository;
    }

    @Override
    public Iterable<OfferDto> getOffers() {
        return modelMapper.map(offerRepository.findAll(),
                new TypeToken<List<OfferDto>>(){}.getType());
    }

    @Override
    public OfferDto getOffer(int id) {
        return modelMapper.map(getOfferFromRepoOrThrow(id), OfferDto.class);
    }

    @Override
    public List<Offer> getAAssociatedOffers(String aId) {
        Integer Id = Integer.parseInt(aId);
        List<Offer> associatedOffers ;//= Arrays.asList();
        associatedOffers = offerRepository.findOffersByAuctionId(Id);//findByPriceContaining(aId);
        if (associatedOffers != null){
            return associatedOffers;
        }
        return null;    //TODO: testen
    }

    @Override
    public List<Offer> getUAssociatedOffers(String uId) {
        Integer Id = Integer.parseInt(uId);
        List<Offer> associatedOffers ;//= Arrays.asList();
        associatedOffers = offerRepository.findOffersByCreatorId(Id);
        if (associatedOffers != null){
            return associatedOffers;
        }
        return null;    //TODO: testen
    }

    @Override
    public State changeAccepted(int offerId, int state) {
        Offer offer = offerRepository.findById(offerId).orElse(new Offer());
        offer.setState(State.values()[state]);
        offerRepository.save(offer);
        //TODO: eleganter als mit Params lösen
        return State.values()[state];
    }

    @Override
    public OfferDto updateOffer(OfferDto offerDto, int id) {
        Offer existingOffer = getOfferFromRepoOrThrow(id);

        Offer offer = modelMapper.map(offerDto, Offer.class);

        // check ranges (amount, price, ...)
        //existingOffer.getAuctionId().throwIfOutsideRanges(offer);

        // the following properties must not be updated
        offer.setId(existingOffer.getId());
        offer.setCreatorId(existingOffer.getCreatorId());
        offer.setAuctionId(existingOffer.getAuctionId());
        offerRepository.save(offer);

        return modelMapper.map(offer, OfferDto.class);
    }

    @Override
    public Offer createOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public OfferDto deleteOffer(int id) {
        OfferDto existingOfferDto = getOffer(id);
        offerRepository.deleteById(id);
        return existingOfferDto;
    }

    private Offer getOfferFromRepoOrThrow(Integer id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
