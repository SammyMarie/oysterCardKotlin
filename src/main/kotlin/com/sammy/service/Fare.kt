package com.sammy.service

import com.sammy.enums.StationsAndZones
import com.sammy.enums.TransportFare
import com.sammy.enums.TransportMode
import com.sammy.model.Card
import java.util.Arrays
import java.util.stream.Collectors

class Fare {
    fun maximumFare(card: Card, vararg transportModes: TransportMode?) {
        val transportType = Arrays.stream(transportModes)
            .map<String> { obj: TransportMode? -> obj?.transportMode }
            .collect(Collectors.joining(" & "))

        when (transportType) {
            "Bus" -> card.remainingBalance(TransportFare.BUS.transportFare)
            "Tube" -> card.remainingBalance(TransportFare.MAXIMUM.transportFare)
            else -> card.remainingBalance(TransportFare.MAXIMUM.transportFare)
        }
    }

    fun chargeTravel(card: Card, entryPoint: StationsAndZones, exitPoint: StationsAndZones,
                     vararg transportModes: TransportMode?) {

        val departingZone = Arrays.stream(entryPoint.stationZone.split(", ").toTypedArray())
            .mapToInt { s: String -> s.toInt() }
            .boxed()
            .collect(Collectors.toSet())

        maximumFare(card, *transportModes)

        val arrivingZone = Arrays.stream(exitPoint.stationZone.split(", ").toTypedArray())
            .mapToInt { s: String -> s.toInt() }
            .boxed()
            .collect(Collectors.toSet())

        val transportType = Arrays.stream(transportModes)
            .map<String> { obj: TransportMode? -> obj?.transportMode }
            .collect(Collectors.joining())

        if (transportType.equals("Tube", ignoreCase = true)) {
            val zonesCrossed = countZonesCrossed(departingZone, arrivingZone)
            val isZoneOneCrossed = isZoneOneCrossed(departingZone, arrivingZone)

            when {
                zonesCrossed == 0 && isZoneOneCrossed -> {
                    val refund = TransportFare.MAXIMUM.transportFare
                        .subtract(TransportFare.ZONE_ONE.transportFare)
                        .number.toDouble()
                    card.addAmount(refund)
                }

                zonesCrossed == 0 -> {
                    val refund = TransportFare.MAXIMUM.transportFare
                        .subtract(TransportFare.ONE_ZONE_OUTSIDE_ZONE_ONE.transportFare)
                        .number.toDouble()
                    card.addAmount(refund)
                }

                zonesCrossed == 1 && isZoneOneCrossed -> {
                    val refund = TransportFare.MAXIMUM.transportFare
                        .subtract(TransportFare.TWO_ZONES_INCLUDING_ZONE_ONE.transportFare)
                        .number.toDouble()
                    card.addAmount(refund)
                }

                zonesCrossed == 1 -> {
                    val refund = TransportFare.MAXIMUM.transportFare
                        .subtract(TransportFare.TWO_ZONES_EXCLUDING_ZONE_ONE.transportFare)
                        .number.toDouble()
                    card.addAmount(refund)
                }
            }
        }
    }

    private fun countZonesCrossed(departingZone: Set<Int>, arrivingZone: Set<Int>): Int {
        var minimumVisitedZones = Int.MAX_VALUE
        outerLoop@ for (departedZone in departingZone) {
            for (arrivedZone in arrivingZone) {
                val visitedZones = Math.abs(departedZone - arrivedZone)
                if (visitedZones < minimumVisitedZones) {
                    minimumVisitedZones = visitedZones
                }
                if (minimumVisitedZones == 1) {
                    break@outerLoop
                }
            }
        }
        return minimumVisitedZones
    }

    private fun isZoneOneCrossed(departingZone: Set<Int>, arrivingZone: Set<Int>): Boolean {
        return departingZone.isNotEmpty() && departingZone.contains(1) ||
               arrivingZone.isNotEmpty() && arrivingZone.contains(1)
    }
}