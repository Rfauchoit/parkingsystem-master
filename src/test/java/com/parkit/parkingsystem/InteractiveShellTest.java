package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.InteractiveShell;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InteractiveShellTest {
	@InjectMocks
	InteractiveShell interactiveShell;
	@Mock
	private static InputReaderUtil inputReaderUtil;
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static TicketDAO ticketDAO;
	@Mock
	private static ParkingService parkingService;

	@BeforeEach
	private void setUpPerTest() {
	}

	@Test
	public void loadInterfaceCase1() {
		when(inputReaderUtil.readSelection()).thenReturn(1).thenReturn(3);
		interactiveShell.loadInterface();
		verify(parkingService, Mockito.times(1)).processIncomingVehicle();
	}

	@Test
	public void loadInterfaceCase2() {
		when(inputReaderUtil.readSelection()).thenReturn(2).thenReturn(3);
		interactiveShell.loadInterface();
		verify(parkingService, Mockito.times(1)).processExitingVehicle();
	}
 
	@Test
	public void loadInterfaceCaseUnsupportedOption() {
		when(inputReaderUtil.readSelection()).thenReturn(5).thenReturn(3);
		interactiveShell.loadInterface();
		verify(parkingService, Mockito.times(0)).processExitingVehicle();
		verify(parkingService, Mockito.times(0)).processIncomingVehicle();
	}
}