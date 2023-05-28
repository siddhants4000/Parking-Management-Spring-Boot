package com.example.demo.repo;

import com.example.demo.entity.Registration;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends CrudRepository<Registration, Integer>, JpaSpecificationExecutor<Registration> {
    Registration findByVehicleNumber(String vehicleNumber);

    Registration findByEmail(String email);

    Registration findByMobileNumber(String mobileNumber);

}
