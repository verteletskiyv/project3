package com.spring.project3.services;

import com.spring.project3.models.Measurement;
import com.spring.project3.repositories.MeasurementsRepository;
import com.spring.project3.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsRepository sensorsRepository;

    @Autowired
    public MeasurementService(MeasurementsRepository measurementsRepository, SensorsRepository sensorsRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
    }

    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }

    public long getRainyDaysCount() {
        long count = 0;
        List<Measurement> measurements = measurementsRepository.findAll();
        for (Measurement m: measurements) {
            if (m.isRaining())
                count++;
        }
        return count;
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementsRepository.save(measurement);
    }

    public void enrichMeasurement(Measurement measurement) {
        measurement.setSensor(sensorsRepository.findByName(measurement.getSensor().getName()).get());
        measurement.setMeasuredAt(LocalDateTime.now());
    }
}
