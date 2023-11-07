package com.example.demo.ServiceTests;

import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Transaction;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.TransactionRepository;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.RewardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @InjectMocks
    CustomerService customerService;
    @Mock
    RewardService rewardService;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    TransactionRepository transactionRepository;

    @Test
    public void createTransaction_ValidCustomerAndTransaction_Success() {
        Customer customer = new Customer(1, "John Doe", new ArrayList<>());
        Transaction transaction = new Transaction(1, 100, null, customer);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(transactionRepository.save(any())).thenReturn(transaction);

        assertDoesNotThrow(() -> customerService.createTransaction(1, transaction));
    }

    @Test
    public void updateTransaction_ValidCustomerAndTransaction_Success() {
        Customer customer = new Customer(1, "John Doe", new ArrayList<>());
        Transaction transactionToUpdate = new Transaction(1, 100, null, customer);
        Transaction updatedTransaction = new Transaction(1, 200, null, customer);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(transactionRepository.findById(1)).thenReturn(Optional.of(transactionToUpdate));
        when(transactionRepository.save(any())).thenReturn(updatedTransaction);

        assertDoesNotThrow(() -> customerService.updateTransaction(1, 1, updatedTransaction));
    }

    @Test
    public void calculateRewards_ValidCustomer_CorrectPoints() {
        List<Transaction> transactions = new ArrayList<>();
        Customer customer = new Customer(1, "Jane Doe", transactions);
        Transaction transaction1 = new Transaction(1, 50, LocalDateTime.now(), customer);
        Transaction transaction2 = new Transaction(2, 100, LocalDateTime.now(), customer);

        transactions.add(transaction1);
        transactions.add(transaction2);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(rewardService.calculateRewardPoints(transaction1)).thenReturn(25);
        when(rewardService.calculateRewardPoints(transaction2)).thenReturn(50);

        int totalPoints = customerService.calculateRewards(1);

        assertEquals(75, totalPoints);
    }

    @Test
    public void createCustomer_ValidCustomer_Success() {
        Customer customer = new Customer(1, "John Doe", null);

        assertDoesNotThrow(() -> customerService.createCustomer(customer));
    }

    @Test
    public void updateCustomer_ValidCustomer_Success() {
        Customer customer = new Customer(1, "John Doe", null);
        Customer updatedCustomer = new Customer(1, "Jane Doe", null);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any())).thenReturn(updatedCustomer);

        assertDoesNotThrow(() -> customerService.updateCustomer(1, updatedCustomer));
    }
}

