package com.deane.urlapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deane.urlapp.model.WebAddress;

public interface WebAddressRepo extends JpaRepository<WebAddress, Integer>{
	
	public WebAddress findByShortAddress(String shortAddress);

}
