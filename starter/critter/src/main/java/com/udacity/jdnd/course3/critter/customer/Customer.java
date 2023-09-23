package com.udacity.jdnd.course3.critter.customer;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Owner")
public class Customer extends User {

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 200)
    private String address;
    /**
     * 1 customer can owner many pet;
     */
    @OneToMany(mappedBy="customer", fetch= FetchType.EAGER)
    List<Pet> pets = new ArrayList<>();

    public String getPhoneNumber() {
        return phoneNumber;
    }



    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        if(this.pets == null) {
            this.pets = pets;
        } else {
            for(Pet pet: pets){
                if(!this.pets.contains(pet)){
                    this.pets.add(pet);
                }
            }
        }
    }
}
