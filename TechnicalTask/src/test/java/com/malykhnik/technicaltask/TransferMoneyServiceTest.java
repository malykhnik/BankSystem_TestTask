package com.malykhnik.technicaltask;

import com.malykhnik.technicaltask.model.BankAccount;
import com.malykhnik.technicaltask.model.User;
import com.malykhnik.technicaltask.repository.BankAccountRepository;
import com.malykhnik.technicaltask.repository.UserRepository;
import com.malykhnik.technicaltask.service.impls.TransferMoneyServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

public class TransferMoneyServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private TransferMoneyServiceImpl transferMoneyService;

    @Mock
    private User userFrom;
    @Mock
    private User userTo;
    @Mock
    private BankAccount bankAccountFrom;
    @Mock
    private BankAccount bankAccountTo;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        userFrom = new User();
        userTo = new User();
        bankAccountFrom = new BankAccount();
        bankAccountTo = new BankAccount();
    }

    @Test
    public void testTransferSuccessful() {
        bankAccountFrom.setBalance(BigDecimal.valueOf(200.0));
        bankAccountTo.setBalance(BigDecimal.valueOf(100.0));
        userFrom.setBankAccount(bankAccountFrom);
        userTo.setBankAccount(bankAccountTo);

        when(userRepository.findById(1L)).thenReturn(Optional.of(userFrom));
        when(userRepository.findById(2L)).thenReturn(Optional.of(userTo));

        transferMoneyService.transfer(1L, 2L, BigDecimal.valueOf(50.0));

        assertEquals(String.valueOf(150.0), userFrom.getBankAccount().getBalance(), 0.001);
        assertEquals(String.valueOf(150.0), userTo.getBankAccount().getBalance(), 0.001);
    }

    @Test
    public void testTransferFromUserWithInsufficientMoney() {
        bankAccountFrom.setBalance(BigDecimal.valueOf(50.0));
        userFrom.setBankAccount(bankAccountFrom);

        //настройка мокито таким образом, чтобы он вернул userFrom не empty
        when(userRepository.findById(1L)).thenReturn(Optional.of(userFrom));

        assertThrows(RuntimeException.class, () -> transferMoneyService.transfer(1L, 2L, BigDecimal.valueOf(100.0)));
    }

    @Test
    public void testTransferWithMoneyLessThen0() {
        bankAccountFrom.setBalance(BigDecimal.valueOf(50.0));
        userFrom.setBankAccount(bankAccountFrom);

        //настройка мокито таким образом, чтобы он вернул userFrom не empty
        when(userRepository.findById(1L)).thenReturn(Optional.of(userFrom));

        assertThrows(RuntimeException.class, () -> transferMoneyService.transfer(1L, 2L, BigDecimal.valueOf(-20.0)));
    }

    @Test
    public void testTransferWithUserFromNotFound() {
        //настройка мокито таким образом, чтобы он вернул userFrom - empty
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //попытка перевода от empty юзера ко второму юзеру -> выброс Runtime Exception
        assertThrows(RuntimeException.class, () -> transferMoneyService.transfer(1L, 2L, BigDecimal.valueOf(100.0)));
    }

    @Test
    public void testTransferWithUserToNotFound() {
        //настройка мокито таким образом, чтобы он вернул userFrom - empty
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        //попытка перевода от not empty юзера коempty юзеру -> выброс Runtime Exception
        assertThrows(RuntimeException.class, () -> transferMoneyService.transfer(1L, 2L, BigDecimal.valueOf(100.0)));
    }

    @Test
    public void testTransferWithNullParameters() {
        //выброс Runtime Exception при передаче нулевых параметров
        assertThrows(RuntimeException.class, () -> transferMoneyService.transfer(1L, 2L, null));
        assertThrows(RuntimeException.class, () -> transferMoneyService.transfer(1L, null, BigDecimal.valueOf(51.0)));
        assertThrows(RuntimeException.class, () -> transferMoneyService.transfer(null, 2L, BigDecimal.valueOf(51.0)));
    }
}
