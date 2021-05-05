package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseConfig dataBaseConfig = new DataBaseConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
       dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown(){

    }

    @Test
    public void testParkingACar() throws Exception{
    	// Act
    	dataBasePrepareService.clearDataBaseEntries();
    	ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        Ticket ticket = ticketDAO.getTicket(inputReaderUtil.readVehicleRegistrationNumber());
        // TODO : check that a ticket is actually saved in DB and Parking table is updated
        // with availability
        //
        // Assert
        assertEquals("ABCDEF", ticket.getVehicleRegNumber());
        assertNotNull(ticket.getInTime());
        assertNull(ticket.getOutTime());
        assertEquals(1,parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
       
     
   
 }

    @Test
    public void testParkingLotExit() throws Exception{
    	//TODO: check that the fare generated and out time are populated correctly in 
    	// the database
    	// Act

        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        //parkingService.processIncomingVehicle();
        //Thread.sleep(100);
        parkingService.processExitingVehicle();
        Ticket ticket = ticketDAO.getTicket(inputReaderUtil.readVehicleRegistrationNumber());
        // Assert//
        assertEquals(0, ticket.getPrice());
        //assertTrue(ticket.getOutTime())>ticket.getInTime();
    }

}
