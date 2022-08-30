package com.spring.project3.repositories;

import com.spring.project3.models.Measurement;
import com.spring.project3.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
}
