package com.readmoree.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readmoree.dtos.AuthorRequestDto;
import com.readmoree.service.AuthorService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/author")
@AllArgsConstructor
public class AuthorController {

	private AuthorService authorService;

	//add author
	@PostMapping("/admin/add")
	public ResponseEntity<?> addAuthor(@RequestBody @Valid AuthorRequestDto authorRequestDto){
		return ResponseEntity.ok(authorService.addAuthor(authorRequestDto));
	}
}
