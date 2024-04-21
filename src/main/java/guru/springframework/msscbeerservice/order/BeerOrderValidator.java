package guru.springframework.msscbeerservice.order;

import guru.sfg.brewery.model.BeerOrderDto;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class BeerOrderValidator {

    private BeerRepository beerRepository;

    public Boolean validateOrder(BeerOrderDto beerOrderDto){

        // UPCが無効なオーダー数
        AtomicInteger beerNotFound = new AtomicInteger(0);

        beerOrderDto.getBeerOrderLines().forEach(beerOrder -> {
            if(beerRepository.findByUpc(beerOrder.getUpc()) == null){
                beerNotFound.incrementAndGet();
            }
        });

        return beerNotFound.get() == 0;
    }
}
