package org.imobprime.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.imobprime.model.RealEstate;
import org.imobprime.service.RealEstateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class RealEstateRestController {

	public static final Logger logger = LoggerFactory.getLogger(RealEstateRestController.class);

	@Autowired
	RealEstateService realEstateService;

	@GetMapping("real-estates")
	public ResponseEntity<?> listAll() {
		logger.info("Fetching all real estates.");
		List<RealEstate> realEstates = realEstateService.findAll();

		if (realEstates.isEmpty()) {
			logger.info("There is no real estates in database.");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(realEstates, HttpStatus.OK);
	}
	
	@GetMapping("real-estates/filter")
	public ResponseEntity<?> filter(
			@RequestParam String name, 
			@RequestParam String cnpj, 
			@RequestParam Integer stateId, 
			@RequestParam Integer cityId) {
		logger.info("Fetching real estates using filter parameters.");
		
		Map<String, String> parameters = new HashMap<>();
		parameters.put("name", name);
		parameters.put("cnpj", cnpj);
		
		if(stateId == null)
			parameters.put("stateId", "");
		else
			parameters.put("stateId", String.valueOf(stateId));
		
		if(cityId == null)
			parameters.put("cityId", "");
		else
			parameters.put("cityId", String.valueOf(cityId));
		
		List<RealEstate> realEstates = realEstateService.findAll(parameters);

		if (realEstates.isEmpty()) {
			logger.info("There is no real estates in database.");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(realEstates, HttpStatus.OK);
	}

	@GetMapping("real-estates/{id}")
	public ResponseEntity<RealEstate> getById(@PathVariable("id") Integer id) {
		logger.info("Fetching the real estate by id.");
		RealEstate realEstate = realEstateService.findById(id);
		logger.info("Real estate fetched with sucess.");

		return new ResponseEntity<>(realEstate, HttpStatus.OK);
	}
	
	@PostMapping("real-estates")
	public ResponseEntity<RealEstate> save(@RequestBody RealEstate realEstate) {
		logger.info("Saving the real estate.");
		realEstateService.save(realEstate);
		logger.info("Real estate saved with success.");
		
		realEstate.setAddressZipCode(null);
		return new ResponseEntity<>(realEstate, HttpStatus.OK);
	}

	@PutMapping("real-estates")
	public ResponseEntity<Void> update(@RequestBody RealEstate realEstate) {
		logger.info("Updating the real estate.");
		realEstateService.update(realEstate);
		
		logger.info("Real estate updated with success.");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("real-estates/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		logger.info("Deleting the real estate by id.");
		realEstateService.deleteById(id);
		logger.info("Real estate deleted with success.");
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
