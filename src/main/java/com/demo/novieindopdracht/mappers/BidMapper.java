package com.demo.novieindopdracht.mappers;

import com.demo.novieindopdracht.dtos.BidOutputDto;
import com.demo.novieindopdracht.models.Bid;

import java.util.List;
import java.util.stream.Collectors;

public class BidMapper {
    public static BidOutputDto toDto(Bid bid) {
        BidOutputDto bidOutputDto = new BidOutputDto();
        bidOutputDto.bidId = bid.getBidId();
        bidOutputDto.price = bid.getPrice();
        bidOutputDto.username = bid.getUser().getUsername();

        return bidOutputDto;
    }

    public static List<BidOutputDto> toDtoList(List<Bid> bids) {
        return bids.stream().map((bid -> {
             BidOutputDto dto = BidMapper.toDto(bid);
            return dto;
        })).collect(Collectors.toList());
    }
}
