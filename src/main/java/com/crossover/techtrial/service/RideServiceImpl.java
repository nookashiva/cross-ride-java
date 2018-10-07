/**
 * 
 */
package com.crossover.techtrial.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.RideRepository;
import com.crossover.techtrial.validator.ValidationService;

/**
 * @author crossover
 *
 */
@Service
public class RideServiceImpl implements RideService{

  @Autowired
  RideRepository rideRepository;
  @Autowired
  ValidationService validationService;
  
  public Ride save(Ride ride) throws Exception {
	  if(validationService.validateInputDate(ride)){
		  return rideRepository.save(ride);
    } else {
    	throw new Exception("End time cannot be less than or equal to start time.");
    }
  }
  
  public Ride findById(Long rideId) {
    Optional<Ride> optionalRide = rideRepository.findById(rideId);
    if (optionalRide.isPresent()) {
      return optionalRide.get();
    }else return null;
  }

@Override
public List<TopDriverDTO> getTopDriver(LocalDateTime startTime, LocalDateTime endTime) {

	Collection<Ride> rideList = (List<Ride>)rideRepository.findAll();
	List<Ride> filteredList = rideList.stream().filter(ride->(startTime.isAfter(LocalDateTime.parse(ride.getStartTime()))) && 
			(endTime.isBefore(LocalDateTime.parse(ride.getEndTime())))).collect(Collectors.toList());
	Collections.sort(filteredList,driverComparator);
	Collections.sort(filteredList,rideComparator);
	Map<String,TopDriverDTO > map = new HashMap<String,TopDriverDTO >();
	filteredList.forEach(ride->{
		do{
		if(!map.containsKey(ride.getDriver().getName())){
		TopDriverDTO dto = new TopDriverDTO();
		dto.setName(ride.getDriver().getName());
		dto.setAverageDistance(ride.getDistance().doubleValue());
		Integer sec = LocalDateTime.parse(ride.getEndTime()).getSecond()-(LocalDateTime.parse(ride.getStartTime()).getSecond());
		dto.setMaxRideDurationInSecods(sec.longValue());
		dto.setEmail(ride.getDriver().getEmail());
		dto.setTotalRideDurationInSeconds(sec.longValue());
		} else{
			TopDriverDTO dto = map.get(ride.getDriver().getName());
			calculateAvgDistance(dto,ride);
			calculateMaxRideDuration(dto,ride);
			calculateTotalRideDuration(dto,ride);
		}
		}while(map.size()<=5);
		
	});
	
	
	return (List<TopDriverDTO>) map.values();
}

private void calculateTotalRideDuration(TopDriverDTO dto, Ride ride) {
	
	Integer sec = LocalDateTime.parse(ride.getEndTime()).getSecond()-(LocalDateTime.parse(ride.getStartTime()).getSecond());
	dto.setTotalRideDurationInSeconds(sec.longValue()+dto.getTotalRideDurationInSeconds());
	
}

private void calculateMaxRideDuration(TopDriverDTO dto, Ride ride) {
	Integer sec = LocalDateTime.parse(ride.getEndTime()).getSecond()-(LocalDateTime.parse(ride.getStartTime()).getSecond());
	if(sec.doubleValue()>dto.getMaxRideDurationInSecods()){
		dto.setMaxRideDurationInSecods(sec.longValue());
	}
	
}

private void calculateAvgDistance(TopDriverDTO dto, Ride ride) {
	
	Double dist = dto.getAverageDistance();
	dto.setAverageDistance(dist+ride.getDistance().doubleValue()/2);
}

public static Comparator<Ride> rideComparator = new Comparator<Ride>() {

    @Override
    public int compare(Ride e1, Ride e2) {
    	LocalDateTime startTime = LocalDateTime.parse(e1.getStartTime());
    	LocalDateTime endTime = LocalDateTime.parse(e1.getEndTime());
    	LocalDateTime hours = endTime.minusHours(startTime.getHour());
    	
    	LocalDateTime startTimeThis = LocalDateTime.parse(e2.getStartTime());
    	LocalDateTime endTimeThis = LocalDateTime.parse(e2.getEndTime());
    	LocalDateTime hoursThis = endTimeThis.minusHours(startTimeThis.getHour());
    	return hours.getHour()-hoursThis.getHour();
    }
};

public static Comparator<Ride> driverComparator = new Comparator<Ride>() {

    @Override
    public int compare(Ride e1, Ride e2) {
    	return e1.getDriver().getName().compareTo(e2.getDriver().getName());
    }
};
}
