package br.com.javafood.application;

import br.com.javafood.domain.card.Card;
import br.com.javafood.domain.card.CardRepository;
import br.com.javafood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;


    @Transactional
    public void save(Card card){

        Card card1 = cardRepository.findByClienteId(SecurityUtils.loggedCliente().getId());
        card.setNumber(card.getNumber().replace(',', Character.MIN_VALUE));
        card.setData(card.getData().replace(',','/'));
        card.setName(card.getName().toUpperCase());

        try {
            String number = SecurityUtils.encrypt(card.getNumber());
            String cvv = SecurityUtils.encrypt(card.getCvv());
            String data = SecurityUtils.encrypt(card.getData());
            card.setNumber(number);
            card.setCvv(cvv);
            card.setData(data);

        } catch (Exception e) {
            e.printStackTrace();
        }

        card.setCliente(SecurityUtils.loggedCliente());
        if (card1 != null) {
            card.setId(card1.getId());
        }
        cardRepository.save(card);


    }
}
