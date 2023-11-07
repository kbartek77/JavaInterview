package com.example.demo.Service;

import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Transaction;
import com.example.demo.Exception.CustomerNotFoundException;
import com.example.demo.Exception.InternalServerErrorException;
import com.example.demo.Exception.TransactionNotFoundException;
import com.example.demo.Exception.UnprocessableEntityException;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerService {

    private final RewardService rewardService;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public void createTransaction(int customerId, Transaction transaction) {
        try {
            Customer customer = findCustomerById(customerId);

            transaction.setCustomer(customer);
            transactionRepository.save(transaction);
        } catch (CustomerNotFoundException e) {
            throw new UnprocessableEntityException("Error creating transaction: " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error: " + e.getMessage());
        }
    }

    public void updateTransaction(int customerId, int transactionId, Transaction updatedTransaction) {
        try {
            Customer customer = findCustomerById(customerId);
            Transaction transactionToUpdate = findTransactionById(transactionId);

            transactionToUpdate.setAmount(updatedTransaction.getAmount());
            transactionToUpdate.setTimestamp(updatedTransaction.getTimestamp());

            transactionRepository.save(transactionToUpdate);
        } catch (CustomerNotFoundException e) {
            throw new UnprocessableEntityException("Error updating transaction: " + e.getMessage());
        } catch (TransactionNotFoundException e) {
            throw new UnprocessableEntityException("Error updating transaction: " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error: " + e.getMessage());
        }
    }

    public int calculateRewards(int customerId) {
        try {
            Customer customer = findCustomerById(customerId);

            List<Transaction> transactions = customer.getTransactions();
            int totalPoints = transactions.stream()
                    .mapToInt(rewardService::calculateRewardPoints)
                    .sum();

            return totalPoints;
        } catch (CustomerNotFoundException e) {
            throw new UnprocessableEntityException("Error calculating rewards: " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error: " + e.getMessage());
        }
    }

    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void updateCustomer(int customerId, Customer updatedCustomer) {
        Customer customer = findCustomerById(customerId);
        customer.setName(updatedCustomer.getName());

        customerRepository.save(customer);
    }

    private Customer findCustomerById(int customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    private Transaction findTransactionById(int transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
    }
}
