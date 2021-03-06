package com.devsuperior.finalproject.controller.resources;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.finalproject.model.services.GenericService;
import com.devsuperior.finalproject.util.Convertible;

@RestController
public interface GenericResource<T extends Convertible<DTO>, DTO, ID> {

	GenericService<T, DTO, ID> getService();

	@GetMapping
	default ResponseEntity<Page<DTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return ResponseEntity.ok().body(getService().findAllPaged(pageRequest));
	}

	@GetMapping(value = "/{id}")
	default ResponseEntity<DTO> findById(@PathVariable ID id) {
		return ResponseEntity.ok().body(getService().findById(id));
	}

	@PostMapping
	ResponseEntity<DTO> insert(@RequestBody DTO dto);

	@PutMapping(value = "/{id}")
	default ResponseEntity<DTO> update(@PathVariable ID id, @RequestBody DTO dto) {
		return ResponseEntity.ok().body(getService().update(id, dto));
	}

	@DeleteMapping(value = "/{id}")
	default ResponseEntity<DTO> delete(@PathVariable ID id) {
		getService().delete(id);
		return ResponseEntity.noContent().build();
	}
}
