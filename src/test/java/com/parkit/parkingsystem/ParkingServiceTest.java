package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {


    @InjectMocks
    ParkingService parkingService;
    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;


    @BeforeEach
    private void setUpPerTest() {
       
    }

    @Test
    public void processIncomingVehicleTest() {
    	 try {
             when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");



             when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);


         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException("Failed to set up test mock objects");
         }
    	when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
        when(inputReaderUtil.readSelection()).thenReturn(1);
        parkingService.processIncomingVehicle();
        verify(ticketDAO, Mockito.times(1)).saveTicket(any());
    }

    @Test
    public void processExitingVehicleTest() {
    	 try {
             when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");



             when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);


         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException("Failed to set up test mock objects");
         }
    	when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        Ticket ticket = new Ticket();
        ticket.setInTime(Instant.now().minusSeconds(60 * 60));
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
        parkingService.processExitingVehicle();
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
    }

    
    @Test
    public void getNextParkingNumberIfAvailableIf() {
    	//Arrange
    	when(parkingSpotDAO.getNextAvailableSlot(Mockito.any())).thenReturn(1);
    	when(inputReaderUtil.readSelection()).thenReturn(1);
    	//Act
    	ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();
    	//Assert
    	verify(parkingSpotDAO, Mockito.times(1)).getNextAvailableSlot(any(ParkingType.class));
    	assertEquals(parkingSpot.getId(), 1);}
    
    @Test
    public void getNextParkingNumberIfAvailableElse() {
    	//Arrange
    	when(parkingSpotDAO.getNextAvailableSlot(Mockito.any())).thenReturn(-1);
    	when(inputReaderUtil.readSelection()).thenReturn(1);
    	//Act
    	ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();
    	//Assert
    	verify(parkingSpotDAO, Mockito.times(1)).getNextAvailableSlot(any(ParkingType.class));
    	assertNull(parkingSpot);}
    	
     @Test
     public void getNextParkingNumberIfAvailableIllegalException() {
    	  //Arrange
    	   when(inputReaderUtil.readSelection()).thenReturn(3);
    	   //Act
    	   ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();
    	   //Assert
    	    assertNull(parkingSpot);
    	   

}
}
