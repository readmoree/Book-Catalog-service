package com.readmoree.service;

import com.readmoree.dtos.ApiResponse;
import com.readmoree.dtos.AuthorRequestDto;

public interface AuthorService {

	ApiResponse addAuthor(AuthorRequestDto authorRequestDto);

}
