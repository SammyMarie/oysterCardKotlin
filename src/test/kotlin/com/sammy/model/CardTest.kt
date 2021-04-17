package com.sammy.model

import com.sammy.enums.TransportFare
import org.assertj.core.api.AssertionsForClassTypes.catchThrowable
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.javamoney.moneta.Money
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CardTest {
    private var card: Card? = null

    @BeforeEach
    fun setup() {
        card = Card()
    }

    @Test
    fun addBalance_shouldUpdateAvailableBalance() {
        card!!.addAmount(30.00)
        assertThat(card!!.getBalance()).isEqualTo(Money.of(30.00, "GBP"))
    }

    @Test
    fun subtractUnavailableAmount_shouldThrowException() {
        val thrown = catchThrowable {
            card!!.remainingBalance(
                TransportFare.MAXIMUM.transportFare
                                   )
        }
        assertThat(thrown).hasMessage("Amount is greater than current balance")
    }

    @Test
    fun subtractFare_shouldUpdateAvailableBalance() {
        card!!.addAmount(30.00)
        card!!.remainingBalance(TransportFare.MAXIMUM.transportFare)
        assertThat(card!!.getBalance()).isEqualTo(Money.of(26.80, "GBP"))
    }
}