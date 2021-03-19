package com.devsuperior.finalproject.model.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.devsuperior.finalproject.model.dto.ClientDTO;
import com.devsuperior.finalproject.model.entities.Client;
import com.devsuperior.finalproject.model.repositories.ClientRepository;
import com.devsuperior.finalproject.model.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService implements GenericService<Client, ClientDTO, Long> {

	@Autowired
	private ClientRepository repository;
	
	@Override
	public JpaRepository<Client, Long> getRepository() {
		return repository;
	}

	@Override
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		copyDtoToEntity(dto, entity);
		return repository.save(entity).convert();
	}

	@Override
	public Client updateData(Long id, ClientDTO dto) {
		try {
			Client entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			return entity;
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	private void copyDtoToEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
	}

}
