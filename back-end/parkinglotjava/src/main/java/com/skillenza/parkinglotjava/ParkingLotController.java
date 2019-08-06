package com.skillenza.parkinglotjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value="/api")

public class ParkingLotController {

    @Autowired
    ParkingLotRepository parkingLotRepository;
    
    @PersistenceContext
    EntityManager entityManager;
    
    double parkChargePerMin=0.333;
    
    @GetMapping("/parkings")
    public List<ParkingLot> getAllVehicles(){
    	
    	return parkingLotRepository.findAll();
    	
    }
    
    
    @PostMapping(value="/parkings")
    public ResponseEntity<ResponseMessage> parkingVehicle(@RequestBody ParkingLot parkingLot) {
    	ResponseMessage response;
    	if(!isDuplicateLotEntry(parkingLot.getLot())) {
        	if(isVehiclePresent(parkingLot.getVehicle_number())) {
        		
        		response=new ResponseMessage("Vehicle already parked",HttpStatus.BAD_REQUEST);
        	
        	}else{
        		// Create date has been set default by SQL
        		parkingLotRepository.save(parkingLot);
        		
        		response=new ResponseMessage("Vehicle parked",HttpStatus.OK);
        	}
    	}else {
     		response=new ResponseMessage("Lot already in Use",HttpStatus.OK);
    	}
    	return new ResponseEntity<ResponseMessage>(response,response.getCode()) ;

    
    }
    
    public boolean isVehiclePresent(int vehicleNumber){
    	
        Query query = entityManager.createNativeQuery("SELECT em.* FROM ParkingLot.parkinglots as em " +
                "WHERE em.vehicle_number=?", ParkingLot.class);
        query.setParameter(1, vehicleNumber);
        
        return query.getResultList().size()>0;
    	
    }
    
    public boolean isDuplicateLotEntry(int lot) {
    	   	
        Query query = entityManager.createNativeQuery("SELECT em.* FROM ParkingLot.parkinglots as em " +
                "WHERE em.lot=?", ParkingLot.class);
        query.setParameter(1, lot);
        
        return query.getResultList().size()>0;
        
    }
    
}




