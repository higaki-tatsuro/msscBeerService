package guru.springframework.msscbeerservice.bootstrap;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.sfg.brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234100036";
    public static final String BEER_2_UPC = "0631234100019";
    public static final String BEER_3_UPC = "0631234175213";

    private final BeerRepository beerRepository;

//    public BeerLoader(BeerRepository beerRepository) {
//        this.beerRepository = beerRepository;
//    }

    @Override
    public void run(String... args) throws Exception {
        if(this.beerRepository.count() == 0){
            loadBeerObject();
        }
    }

    private void loadBeerObject(){

           Beer b1 = Beer.builder()
                .beerName("Mongo Bobs")
                .beerStyle(BeerStyleEnum.IPA.name())
                .minOnHand(12)
                .quantityToBrew(200)
                .upc(BEER_1_UPC)
                .price(new BigDecimal(12.95))
                .build();

           Beer b2 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyleEnum.LAGER.name())
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal(11.95))
                    .build();

           Beer b3 = Beer.builder()
                    .beerName("No Hammers On The Bar")
                    .beerStyle(BeerStyleEnum.GOSE.name())
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal(11.93))
                    .build();

           this.beerRepository.save(b1);
           this.beerRepository.save(b2);
           this.beerRepository.save(b3);
    }
}
