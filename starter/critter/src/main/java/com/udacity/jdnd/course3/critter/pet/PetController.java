package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.exception.NotFound;
import com.udacity.jdnd.course3.critter.customer.CustomerService;
import com.udacity.jdnd.course3.critter.customer.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    static PetService petService;
    static CustomerService customerService;

    @Autowired
    public void setPetService(PetService petService) {
        PetController.petService = petService;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        PetController.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
//        throw new UnsupportedOperationException();
        Pet savedPet = petService.save(convertPetDTOToPet(petDTO));
        Long ownerId = savedPet.getCustomer().getId();
        if (ownerId != null) {
            setPetToCustomerIfPetNotExist(ownerId, savedPet);
        }
        return convertPetToPetDTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
//        throw new UnsupportedOperationException();
        try {
            return convertPetToPetDTO(petService.getPetById(petId));
        } catch (NotFound e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping
    public List<PetDTO> getPets() {
        //        throw new UnsupportedOperationException();
        List<Pet> pets = petService.getPets();
        return pets.stream().map(pet -> convertPetToPetDTO(pet)).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        //        throw new UnsupportedOperationException();
        try {
            List<Pet> pets = petService.getPetsByOwnerId(ownerId);
            return pets.stream().map(pet -> convertPetToPetDTO(pet)).collect(Collectors.toList());
        } catch (NotFound e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static PetDTO convertPetToPetDTO(Pet pet) {
        //        throw new UnsupportedOperationException();
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        try {
            petDTO.setOwnerId(pet.getCustomer().getId());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return petDTO;
    }

    private static Pet convertPetDTOToPet(PetDTO petDTO) {
        //        throw new UnsupportedOperationException();
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        try {
            Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
            pet.setCustomer(customer);
        } catch (NotFound e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return pet;
    }

    static void setPetToCustomerIfPetNotExist(Long ownerId, Pet pet) {
        //        throw new UnsupportedOperationException();
        Customer existingCustomer;
        try {
            existingCustomer = customerService.getCustomerById(ownerId);
            existingCustomer.setPets(Arrays.asList(pet));
            customerService.save(existingCustomer);
        } catch (NotFound e) {
            System.out.println(e.getMessage());
        }
    }
}
