package com.crossover.techtrial.validator;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.crossover.techtrial.model.Ride;

@Service
public class ValidationService {

	public Boolean validateInputDate(Ride ride){
		
		LocalDateTime startTime = LocalDateTime.parse(ride.getStartTime());
		LocalDateTime endTime = LocalDateTime.parse(ride.getEndTime());
		if(endTime.isEqual(startTime) || endTime.isBefore(startTime))
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}
}
