package com.springBoot.petclinic.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.springBoot.petclinic.dao.OwnerRepository;
import com.springBoot.petclinic.dao.PetRepository;
import com.springBoot.petclinic.exception.OwnerNotFoundException;
import com.springBoot.petclinic.model.Owner;

@Service
@Transactional(rollbackFor=Exception.class)
public class PetClinicServiceImpl implements PetClinicService {

	private OwnerRepository ownerRepository;
	
    private PetRepository petRepository;

    @Autowired
	public void setPetRepository(PetRepository petRepository) {
		this.petRepository = petRepository;
	}


	@Autowired
	public void setOwnerRepository(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}
	

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public List<Owner> findOwners() {
		return ownerRepository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public List<Owner> findOwners(String lastName) {
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
	public Owner findOwner(long id) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(id);
		if(owner == null) throw new OwnerNotFoundException("Owner not dound with id : "+id);
		return owner;
	}

	@Override
	public void createOwner(Owner owner) {
		ownerRepository.create(owner);
		
	}

	@Override
	public void update(Owner owner) {
		ownerRepository.update(owner);
		
	}

	@Override
	public void Delete(Long id) {
		
	petRepository.deleteByOwnerId(id);
	ownerRepository.delete(id);
	
	
	}



}
