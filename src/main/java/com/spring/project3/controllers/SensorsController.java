package com.spring.project3.controllers;

import com.spring.project3.dto.SensorDTO;
import com.spring.project3.models.Sensor;
import com.spring.project3.services.SensorsService;
import com.spring.project3.util.MeasurementErrorResponse;
import com.spring.project3.util.MeasurementException;
import com.spring.project3.util.SensorsValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.spring.project3.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorsValidator validator;
    @Autowired
    public SensorsController(SensorsService sensorsService, ModelMapper modelMapper, SensorsValidator validator) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO,
                                               BindingResult bindingResult) {
        validator.validate(convertToSensor(sensorDTO), bindingResult);
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        sensorsService.save(convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
