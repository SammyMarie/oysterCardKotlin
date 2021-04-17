package com.sammy.service

import com.sammy.enums.StationsAndZones
import com.sammy.enums.TransportMode
import com.sammy.model.Card
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.javamoney.moneta.Money
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FareTest {
    private var fare: Fare? = null
    private var card: Card? = null

    @BeforeEach
    fun setup() {
        fare = Fare()
        card = Card()
        card!!.addAmount(30.00)
    }

    @Test
    fun subtractMaximumBusFare_shouldSucceed() {
        fare!!.maximumFare(card!!, TransportMode.BUS)
        assertThat(card!!.getBalance()).isEqualTo(Money.of(28.20, "GBP"))
    }

    @Test
    fun subtractMaximumTubeFare_shouldSucceed() {
        fare!!.maximumFare(card!!, TransportMode.TUBE)
        assertThat(card!!.getBalance()).isEqualTo(Money.of(26.80, "GBP"))
    }

    @Test
    fun subtractMaximumFare_shouldSucceed() {
        fare!!.maximumFare(card!!, *TransportMode.values())
        assertThat(card!!.getBalance()).isEqualTo(Money.of(26.80, "GBP"))
    }

    @Test
    fun chargeZoneOneFare_shouldSucceed() {
        fare!!.chargeTravel(card!!, StationsAndZones.HOLBORN, StationsAndZones.EARLS_COURT, TransportMode.TUBE)
        assertThat(card!!.getBalance()).isEqualTo(Money.of(27.50, "GBP"))
    }

    @Test
    fun chargeOutsideZoneOneFare_shouldSucceed() {
        fare!!.chargeTravel(card!!, StationsAndZones.HAMMERSMITH, StationsAndZones.WIMBLEDON, TransportMode.TUBE)
        assertThat(card!!.getBalance()).isEqualTo(Money.of(27.75, "GBP"))
    }

    @Test
    fun chargeZoneOneCrossedFare_shouldSucceed() {
        fare!!.chargeTravel(card!!, StationsAndZones.HOLBORN, StationsAndZones.HAMMERSMITH, TransportMode.TUBE)
        assertThat(card!!.getBalance()).isEqualTo(Money.of(27.00, "GBP"))
    }

    @Test
    fun chargeCrossedTwoZonesAndZoneOneFare_shouldSucceed() {
        fare!!.chargeTravel(card!!, StationsAndZones.HOLBORN, StationsAndZones.WIMBLEDON, TransportMode.TUBE)
        assertThat(card!!.getBalance()).isEqualTo(Money.of(26.80, "GBP"))
    }

    @Test
    fun chargeBusFare_shouldSucceed() {
        fare!!.chargeTravel(card!!, StationsAndZones.EARLS_COURT, StationsAndZones.HAMMERSMITH, TransportMode.BUS)
        assertThat(card!!.getBalance()).isEqualTo(Money.of(28.20, "GBP"))
    }
}