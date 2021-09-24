package com.bringacademy.fabio.BankDemo.controller;

import com.bringacademy.fabio.BankDemo.model.Bank;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    private final String APIURL = "https://api.openbankproject.com/obp/v4.0.0/banks";

    @InjectMocks
    private MainController mainController;

    @Mock
    private RestTemplate restTemplate;


    @Test
    void getBanks_all() throws URISyntaxException {
        Bank bank1 = new Bank();
        bank1.setId("bank_one_id");
        bank1.setShortName("bank_one_shor_name");
        bank1.setFullName("bank_one_full_name");

        Bank bank2 = new Bank();
        bank2.setId("bank_two_id");
        bank2.setShortName("bank_two_shor_name");
        bank2.setFullName("bank_two_full_name");

        Bank bank3 = new Bank();
        bank3.setId("bank_three_id");
        bank3.setShortName("bank_three_shor_name");
        bank3.setFullName("bank_three_full_name");

        //Create mock list
        List<Bank> bankList = Arrays.asList(bank1, bank2, bank3);

        Bank.BankGroup bg = new Bank.BankGroup(bankList);


        //Bind mock list with mockmvc for test
        when(restTemplate.getForObject(eq(APIURL),
                    eq(Bank.BankGroup.class)))
                .thenReturn(bg);

        assertThat(mainController.getBanks("all"))
                .isEqualTo(bankList);

    }

    @Test
    void getBanks_byName() {
        Bank bank1 = new Bank();
        bank1.setId("bank_one_id");
        bank1.setShortName("bank_one_short_name");
        bank1.setFullName("bank_one_full_name");

        Bank bank2 = new Bank();
        bank2.setId("bank_two_id");
        bank2.setShortName("bank_two_short_name");
        bank2.setFullName("bank_two_full_name");

        List<Bank> bankList1 = Arrays.asList(bank1);
        List<Bank> bankList2 = Arrays.asList(bank2, bank1);

        Bank.BankGroup bg = new Bank.BankGroup(bankList2);

        when(restTemplate.getForObject(eq(APIURL), eq(Bank.BankGroup.class)))
                .thenReturn(bg);

        assertThat(mainController.getBanks("one"))
                .isEqualTo(bankList1);
    }

    @Test
    void getBank() {
        Bank bank1 = new Bank();
        bank1.setId("bank_one_id");
        bank1.setShortName("bank_one_shor_name");
        bank1.setFullName("bank_one_full_name");

        Bank bank2 = new Bank();
        bank2.setId("bank_two_id");
        bank2.setShortName("bank_two_shor_name");
        bank2.setFullName("bank_two_full_name");

        Bank.BankGroup bg = new Bank.BankGroup(Arrays.asList(bank1, bank2));

        when(restTemplate.getForObject(eq(APIURL), eq(Bank.BankGroup.class)))
                .thenReturn(bg);

        assertThat(mainController.getBank("bank_one_id"))
                .isEqualTo(bank1);
    }
}