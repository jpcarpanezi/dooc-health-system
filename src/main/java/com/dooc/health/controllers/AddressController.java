package com.dooc.health.controllers;

import com.dooc.health.infrastructure.AddressRepository;
import com.dooc.health.models.Address;
import com.dooc.health.models.Medicines;
import com.dooc.health.models.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;


@RestController
@RequestMapping("/address")
public class AddressController {
	private final AddressRepository addressRepository;

	public AddressController(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	@PostMapping
	public @ResponseStatus ResponseEntity<Address> InsertAddress(@RequestBody Address address) throws RestClientResponseException{
		Address insertAddress = new Address();
		insertAddress.setStreet(address.getStreet());
		insertAddress.setComplement(address.getComplement());
		insertAddress.setCity(address.getCity());
		insertAddress.setState(address.getState());
		insertAddress.setZipCode(address.getZipCode());
		insertAddress.setIdPerson(address.getIdPerson());

		addressRepository.save(insertAddress);
		return new ResponseEntity<Address>(address, HttpStatus.CREATED);

	}
	@GetMapping("/infos")
	public @ResponseStatus ResponseEntity<Address> getAddress(@RequestParam(defaultValue = "zipCode")String getMethod, @RequestParam(defaultValue = "") String zipCode , @RequestParam(defaultValue = "0") int idPerson) throws RestClientResponseException {
		Address address = null;
		if(getMethod.equals("zipCode") && zipCode != null) {
			address = addressRepository.getAddressByZipCodeLike('%'+zipCode+'%');
		} else if(getMethod.equals("idPerson")) {
			address = addressRepository.getAddressByIdPerson(idPerson);
		}

		if (address == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}


	@PutMapping
	public @ResponseStatus ResponseEntity<Address> updateAddress(@RequestBody Address address, @RequestParam(defaultValue = "idAddress") String updateMethod, @RequestParam(defaultValue = "1") int idAddress) throws RestClientResponseException {
		Address updateAddress = null;
		 if(updateMethod.equals("idAddress")) {
			updateAddress = addressRepository.getAddressByID(idAddress);
		}

		if (updateAddress == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		updateAddress.setStreet(address.getStreet());
		updateAddress.setComplement(address.getComplement());
		updateAddress.setCity(address.getCity());
		updateAddress.setState(address.getState());
		updateAddress.setZipCode(address.getZipCode());
		updateAddress.setIdPerson(address.getIdPerson());


		addressRepository.save(updateAddress);
		return new ResponseEntity<Address>(updateAddress, HttpStatus.OK);
	}

	@DeleteMapping
	public @ResponseStatus ResponseEntity<HttpStatus> deleteAddress (@RequestParam int idAddress ) throws RestClientResponseException {

		addressRepository.deleteById(idAddress);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}







