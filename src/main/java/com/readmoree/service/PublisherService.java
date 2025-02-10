package com.readmoree.service;

import com.readmoree.dtos.ApiResponse;
import com.readmoree.dtos.PublisherRequestDto;


public interface PublisherService {

	ApiResponse addPublisher(PublisherRequestDto publisherRequestDto);

}
