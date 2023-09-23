package com.udacity.jdnd.course3.critter.customer;

import com.udacity.jdnd.course3.critter.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer findCustomerByPetId(Long petId) throws NotFound {
        return (petId!=null)?customerRepository.findCustomerByPetId(petId)
                .orElseThrow(
                        ()-> new NotFound(
                                String.format("ownerless pet: %s", petId)
                        )
                ):null;
    }

    public Customer getCustomerById(Long ownerId) throws NotFound{
        return (ownerId!=null)?customerRepository.findById(ownerId).orElseThrow(
                ()-> new NotFound(
                        String.format("Owner not found with id %s", ownerId)
                )
        ):null;
    }


}
