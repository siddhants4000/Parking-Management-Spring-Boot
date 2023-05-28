package com.example.demo.repo;

import com.example.demo.entity.Booking;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer>, JpaSpecificationExecutor<Booking> {
    Booking findByVehicleNumber(String vehicleNumber);
}
