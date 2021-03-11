package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;

import static com.parkit.parkingsystem.constants.Fare.BIKE_RATE_PER_HOUR;
import static com.parkit.parkingsystem.constants.Fare.CAR_RATE_PER_HOUR;

public class FareCalculatorService {
    private final TicketDAO ticketDAO;

    public FareCalculatorService(TicketDAO ticketDAO){
    this.ticketDAO= ticketDAO;
}
    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().isBefore((ticket.getInTime())))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }


        //TODO: Some tests are failing here. Need to check if this logic is correct
        Duration duration = Duration.between(ticket.getInTime(), ticket.getOutTime());

        double duree = duration.getSeconds() / 3600.0;
        if (duree <= 0.5) {
            ticket.setPrice(0);
            return;
        }

        double ratePerHour;
        switch (ticket.getParkingSpot().getParkingType()) {

            case CAR: {
                ratePerHour = 1.5;


                break;
            }
            case BIKE: {
                ratePerHour = 1.0;

                break;
            }
            default:
                throw new IllegalArgumentException("Unknown Parking Type");
        }
        if (ticketDAO.isVehicleNumberAlreadyInDatabase(ticket.getVehicleRegNumber()) ) {
            ticket.setPrice(duree * ratePerHour * 0.95);
        } else {
            ticket.setPrice(duree * ratePerHour);
        }
    }
}