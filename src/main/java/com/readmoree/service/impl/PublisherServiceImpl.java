package com.readmoree.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmoree.dao.PublisherDao;
import com.readmoree.dtos.ApiResponse;
import com.readmoree.dtos.PublisherRequestDto;
import com.readmoree.entities.Publisher;
import com.readmoree.service.PublisherService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class PublisherServiceImpl implements PublisherService {
	
	private ModelMapper modelMapper;
	
	private PublisherDao publisherDao;

	@Override
	public ApiResponse addPublisher(PublisherRequestDto publisherRequestDto) {
		
		Publisher publisher = modelMapper.map(publisherRequestDto, Publisher.class);
		
		Publisher savedPublisher = publisherDao.save(publisher);
		
		return new ApiResponse("Publisher with id: "+savedPublisher.getId()+" added successfully!");
	}

}
