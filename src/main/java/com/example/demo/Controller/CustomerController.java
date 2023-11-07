package com.example.demo.Controller;
import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Transaction;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.TransactionRepository;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.RewardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Operation(summary = "Create transactions", tags = "Transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Transaction.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })

    @PostMapping("/{customerId}/transactions")
    public ResponseEntity<String> createTransaction(@PathVariable int customerId, @RequestBody Transaction transaction) {
        customerService.createTransaction(customerId, transaction);
        return ResponseEntity.ok("Transaction created successfully");
    }

    @Operation(summary = "Update transaction", tags = "Transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping("/{customerId}/transactions/{transactionId}")
    public ResponseEntity<String> updateTransaction(@PathVariable int customerId, @PathVariable int transactionId, @RequestBody Transaction updatedTransaction) {
        customerService.updateTransaction(customerId, transactionId, updatedTransaction);
        return ResponseEntity.ok("Transaction updated successfully");
    }

    @Operation(summary = "Calculate rewards for a customer", tags = "Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type = "integer"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/{customerId}/calculateRewards")
    public ResponseEntity<Integer> calculateRewards(@PathVariable int customerId) {
        int totalPoints = customerService.calculateRewards(customerId);
        return ResponseEntity.ok(totalPoints);
    }

    @PostMapping("/")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        customerService.createCustomer(customer);
        return ResponseEntity.ok("Customer created successfully");
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<String> updateCustomer(@PathVariable int customerId, @RequestBody Customer updatedCustomer) {
        customerService.updateCustomer(customerId, updatedCustomer);
        return ResponseEntity.ok("Customer updated successfully");
    }
}
