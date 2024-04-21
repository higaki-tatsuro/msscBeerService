package guru.springframework.msscbeerservice.order;

import guru.sfg.brewery.model.events.ValidateBeerOrderRequest;
import guru.sfg.brewery.model.events.ValidateOrderResult;
import guru.springframework.msscbeerservice.config.JmsConfig;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BeerOrderValidationListener {

    private final BeerOrderValidator beerOrderValidator;

    private final JmsTemplate jmsTemplate;

    // Beer Order Serviceから送られてくるバリデーション要求のリスナー
    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateBeerOrderRequest validateBeerOrderRequest){
        Boolean isValid = beerOrderValidator.validateOrder(validateBeerOrderRequest.getBeerOrderDto());

        // バリデーション結果をJMSメッセージとして返却
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE, ValidateOrderResult.builder()
                .orderId(validateBeerOrderRequest.getBeerOrderDto().getId())
                .isValid(isValid)
                .build());
    }
}
