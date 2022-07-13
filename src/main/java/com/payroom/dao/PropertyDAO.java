package com.payroom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payroom.model.Property;

@Repository
public interface PropertyDAO extends JpaRepository<Property, String> {

}
