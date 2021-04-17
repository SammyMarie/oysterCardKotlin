package com.sammy.enums

import org.javamoney.moneta.Money
import javax.money.MonetaryAmount

enum class TransportFare(val transportFare: MonetaryAmount) {
    ZONE_ONE(Money.of(2.50, "GBP")),
    ONE_ZONE_OUTSIDE_ZONE_ONE(Money.of(2.00, "GBP")),
    TWO_ZONES_INCLUDING_ZONE_ONE(Money.of(3.00, "GBP")),
    TWO_ZONES_EXCLUDING_ZONE_ONE(Money.of(2.25, "GBP")),
    THREE_ZONES(Money.of(3.20, "GBP")),
    MAXIMUM(Money.of(3.20, "GBP")),
    BUS(Money.of(1.80, "GBP"))
}