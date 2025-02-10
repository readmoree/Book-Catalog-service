package com.readmoree.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readmoree.dtos.PublisherRequestDto;
import com.readmoree.service.PublisherService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/publisher")
@AllArgsConstructor
public class PublisherController {
	
	private PublisherService publisherService;

	@PostMapping("/admin/add")
	public ResponseEntity<?> addAuthor(@RequestBody @Valid PublisherRequestDto publisherRequestDto){
		return ResponseEntity.ok(publisherService.addPublisher(publisherRequestDto));
	}

}
