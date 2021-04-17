package com.sammy.model

import org.javamoney.moneta.Money
import javax.money.MonetaryAmount

class Card(private var balance: MonetaryAmount = Money.of(0.00, "GBP")){

    fun addAmount(amount: Double) {
        val amountToBeAdded: MonetaryAmount = Money.of(amount, "GBP")
        balance = balance.add(amountToBeAdded)
    }

    fun remainingBalance(amount: MonetaryAmount) {
        balance = if (amount.number.toDouble() > balance.number.toDouble()) {
            throw IllegalArgumentException("Amount is greater than current balance")
        } else {
            balance.subtract(amount)
        }
    }

    fun getBalance(): MonetaryAmount{
        return balance
    }
}