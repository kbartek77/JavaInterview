package com.example.demo.ControllerTests;

import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Transaction;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.TransactionRepository;
import com.example.demo.Service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CustomerController {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @BeforeEach
    public void setUp() {
        Customer customer = new Customer();
        customer.setName("John Doe");
        customerRepository.save(customer);

        Transaction transaction = new Transaction();
        transaction.setAmount(100.0);
        transaction.setCustomer(customer);
        transactionRepository.save(transaction);
    }

    @Test
    public void createTransaction_ValidData_ReturnsCreated() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(100.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers/1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Transaction created successfully"));
    }

    @Test
    public void updateTransaction_ValidData_ReturnsOk() throws Exception {
        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setAmount(200.0);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/1/transactions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTransaction)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Transaction updated successfully"));
    }

    @Test
    public void updateCustomer_ValidData_ReturnsOk() throws Exception {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Jane Doe");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Customer updated successfully"));
    }
}
