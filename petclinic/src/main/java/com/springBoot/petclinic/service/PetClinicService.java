package com.springBoot.petclinic.service;

import java.util.List;

import com.springBoot.petclinic.exception.OwnerNotFoundException;
import com.springBoot.petclinic.model.Owner;
public interface PetClinicService {
	
List<Owner> findOwners();
List<Owner> findOwners(String lastName);
Owner findOwner(long id) throws OwnerNotFoundException;
void createOwner(Owner owner);
void update(Owner owner);
void Delete(Long id);
}
