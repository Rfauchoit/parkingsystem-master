package com.parkit.parkingsystem.util;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.util.InputReaderUtil;
@ExtendWith(MockitoExtension.class)
public class InputReaderUtilTest {
	  @InjectMocks
	    InputReaderUtil InputreaderUtil;
	 
	  @Mock
	  private static Scanner scan = new Scanner(System.in);;
	  
	 private static final Logger logger = LogManager.getLogger("InputReaderUtil");
	  
	  @BeforeEach
	    private void setUpPerTest() {
	
}
	  public void readVehicleRegistrationNumberTestTry() {
		
		  
	  }
	  }
