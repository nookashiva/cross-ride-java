package com.crossover.techtrial.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.RideRepository;
import com.crossover.techtrial.validator.ValidationService;

@RunWith(SpringJUnit4ClassRunner.class)
public class RideServiceImplTest {

	  @Mock
	  RideRepository rideRepository;
	  @InjectMocks
	  RideServiceImpl classToTest;
	
	  @Mock
	  ValidationService validationService;
	  
	  @Test
	  public void shouldReturnRideIfIdFound(){
		  Optional<Ride> ride = Optional.of(new Ride());
		  when(rideRepository.findById(1l)).thenReturn(ride);
		  Ride p = classToTest.findById(1l);
		  Assert.assertNotNull(p);
	  }
	  
	  @Test
	  public void shouldSaveRide() throws Exception{
		  Ride ride = new Ride();
		  ride.setStartTime("2018-08-18T12:12:12");
		  ride.setEndTime("2018-08-09T12:12:12");
		  when(validationService.validateInputDate(ride)).thenReturn(true);
		  when(rideRepository.save(ride)).thenReturn(ride);
		  Ride p = classToTest.save(ride);
		  Assert.assertNotNull(p);
	  }
	  
	  @Test(expected=Exception.class)
	  public void shouldNotSaveRideIfErrorFound() throws Exception{
		  Ride ride = new Ride();
		  ride.setStartTime("2018-08-08T12:12:12");
		  ride.setEndTime("2018-08-07T12:12:12");
		  classToTest.save(ride);
	  }

	  
}
