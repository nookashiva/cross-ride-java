/**
 * 
 */
package com.crossover.techtrial.service;

import java.time.LocalDateTime;
import java.util.List;

import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Ride;

/**
 * RideService for rides.
 * @author crossover
 *
 */
public interface RideService {
  
  public Ride save(Ride ride) throws Exception;
  
  public Ride findById(Long rideId);

public List<TopDriverDTO> getTopDriver(LocalDateTime startTime, LocalDateTime endTime);
  

}
