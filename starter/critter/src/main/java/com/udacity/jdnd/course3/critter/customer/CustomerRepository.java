package com.udacity.jdnd.course3.critter.customer;

import com.udacity.jdnd.course3.critter.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("SELECT c FROM Customer c " +
            "JOIN c.pets pet " +
            "WHERE pet.id = :petId")
    Optional<Customer> findCustomerByPetId(Long petId);

}
