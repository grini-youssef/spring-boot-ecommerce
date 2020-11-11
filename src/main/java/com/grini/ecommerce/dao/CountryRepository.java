package com.grini.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.grini.ecommerce.entity.Country;

@CrossOrigin(origins = {"http://localhost:4200","https://ecommerce33.herokuapp.com"})
@RepositoryRestResource(collectionResourceRel = "countries", path = "countries")
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
