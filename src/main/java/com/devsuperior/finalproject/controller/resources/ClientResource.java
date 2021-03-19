package com.devsuperior.finalproject.controller.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.finalproject.model.dto.ClientDTO;
import com.devsuperior.finalproject.model.entities.Client;
import com.devsuperior.finalproject.model.services.ClientService;
import com.devsuperior.finalproject.model.services.GenericService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource implements GenericResource<Client, ClientDTO, Long>{

	@Autowired
	private ClientService service;
	
	@Override
	public GenericService<Client, ClientDTO, Long> getService() {
		return service;
	}

	@Override
	public ResponseEntity<ClientDTO> insert(ClientDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

}
