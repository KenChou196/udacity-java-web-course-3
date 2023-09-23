package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.exception.NotFound;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    public Pet save(Pet pet){
        try {
            Pet existingPet = getPetById(pet.getId());
            pet.setId(existingPet.getId());
            return petRepository.save(pet);
        } catch(NotFound e){
            return petRepository.save(pet);
        }

    }

    public Pet getPetById(Long petId) throws NotFound {
        return (petId!=null)?petRepository.findById(petId).orElseThrow(
                ()-> new NotFound(
                        String.format("Pet id: %s cannot be found", petId)
                )
        ):null;
    }

    public List<Pet> getPets(){
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwnerId(Long ownerId) throws NotFound{
        return (ownerId!=null)?petRepository.getPetsByOwnerId(ownerId).orElseThrow(
                ()-> new NotFound(
                        String.format("No pets are not on this owner id: %s", ownerId)
                )
        ):null;
    }

    public List<Pet> getAllPetsByIds(List<Long> petIds){
        return (petIds != null) ? petRepository.findAllById(petIds) : null;
    }
}
