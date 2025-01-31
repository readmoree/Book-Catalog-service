package com.readmoree.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readmoree.entities.Publisher;

public interface PublisherDao extends JpaRepository<Publisher, Long> {

}
