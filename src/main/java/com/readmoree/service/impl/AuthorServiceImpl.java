package com.readmoree.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmoree.dao.AuthorDao;
import com.readmoree.dtos.ApiResponse;
import com.readmoree.dtos.AuthorRequestDto;
import com.readmoree.entities.Author;
import com.readmoree.service.AuthorService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
	
	private ModelMapper modelMapper;
	private AuthorDao authorDao;
	

	@Override
	public ApiResponse addAuthor(AuthorRequestDto authorRequestDto) {
		
		Author author = modelMapper.map(authorRequestDto, Author.class);
		Author savedAuthor = authorDao.save(author);
		
		return new ApiResponse("Author with id: "+savedAuthor.getId()+" saved successfully!");
	}

}
