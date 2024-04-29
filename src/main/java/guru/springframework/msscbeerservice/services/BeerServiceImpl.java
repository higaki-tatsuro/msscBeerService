package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.mappers.BeerMapper;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerPagedList;
import guru.sfg.brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    @Override
    public BeerDto getById(UUID beerId, boolean showInventoryOnHand) {

        if(showInventoryOnHand){
            return this.beerMapper.beerToBeerDtoWithInventory(
                    this.beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
            );
        }else{
            return this.beerMapper.beerToBeerDto(
                    this.beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
            );
        }


    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return this.beerMapper.beerToBeerDto(
                this.beerRepository.save(this.beerMapper.beerDtoToBeer(beerDto))
        );
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = this.beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return this.beerMapper.beerToBeerDto(this.beerRepository.save(beer));
    }

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, boolean showInventoryOnHand) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if(!StringUtils.isEmpty(beerName) && !Objects.isNull(beerStyle)){
            beerPage = this.beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        }else if(!StringUtils.isEmpty(beerName) && Objects.isNull((beerStyle))){
            beerPage = this.beerRepository.findAllByBeerName(beerName, pageRequest);
        }else if(StringUtils.isEmpty(beerName) && !Objects.isNull((beerStyle))){
            beerPage = this.beerRepository.findAllByBeerStyle(beerName, pageRequest);
        }else{
            beerPage = this.beerRepository.findAll(pageRequest);
        }

        if(showInventoryOnHand){
            beerPagedList = new BeerPagedList(
                    beerPage.getContent()
                            .stream()
                            .map(this.beerMapper::beerToBeerDtoWithInventory)
                            .collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements()
            );
        }else{
            beerPagedList = new BeerPagedList(
                    beerPage.getContent()
                            .stream()
                            .map(this.beerMapper::beerToBeerDto)
                            .collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements()
            );
        }

        return  beerPagedList;
    }

    @Override
    public BeerDto getByUpc(String upc, Boolean showInventoryOnHand) {

        if(showInventoryOnHand){
            return this.beerMapper.beerToBeerDtoWithInventory(this.beerRepository.findByUpc(upc));
        }else{
            return this.beerMapper.beerToBeerDto(this.beerRepository.findByUpc(upc));
        }

    }
}
