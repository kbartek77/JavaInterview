package com.example.demo.Service;

import com.example.demo.Entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public class RewardService {
    public int calculateRewardPoints(Transaction transaction) {
        int totalPoints = 0;
        double amountOver100 = Math.max(0, transaction.getAmount() - 100);
        double amountOver50 = Math.max(0, transaction.getAmount() - 50);

        totalPoints += (int) (amountOver100 * 2);
        totalPoints += (int) (amountOver50);

        return totalPoints;
    }
}
