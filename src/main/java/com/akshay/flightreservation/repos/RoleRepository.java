package com.akshay.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshay.flightreservation.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
