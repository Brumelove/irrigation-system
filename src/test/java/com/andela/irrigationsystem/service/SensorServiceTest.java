package com.andela.irrigationsystem.service;

import com.andela.irrigationsystem.config.RabbitMQPropConfig;
import com.andela.irrigationsystem.dto.SensorDto;
import com.andela.irrigationsystem.enumerations.FrequencyType;
import com.andela.irrigationsystem.mapper.IrrigationMapper;
import com.andela.irrigationsystem.model.Sensor;
import com.andela.irrigationsystem.repositories.SensorRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.TaskScheduler;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @author Brume
 **/
@ExtendWith(MockitoExtension.class)
class SensorServiceTest {
    @Mock
    private RabbitTemplate template;
    @Mock
    private SensorRepository repository;
    @Mock
    private RabbitMQPropConfig config;
    @Mock
    private TaskScheduler taskScheduler;
    @Mock
    private IrrigationMapper mapper;
    @InjectMocks
    private SensorService sensorService;

    @Test
    void triggerSensor() {

    }


    @Test
    void getSensorAddress() {
        var sensorNumber = RandomStringUtils.randomAlphanumeric(7);
        var plotId = RandomUtils.nextLong();
        when(repository.getSensorEndpointBySensorNumberAndPlotId(sensorNumber, plotId)).thenReturn(Optional.of(RandomStringUtils.randomAlphanumeric(6)));

        var response = sensorService.getSensorAddress(sensorNumber, plotId);

        assertNotNull(response);
    }

    @Test
    void createSensor() {
        var sensor = new Sensor();
        sensor.setSensorNumber(RandomStringUtils.randomAlphanumeric(4));
        sensor.setId(2L);
        sensor.setPlotId(1L);
        sensor.setSensorsEndpoint(RandomStringUtils.randomAlphabetic(8));

        var sensorDto = new SensorDto();
        sensorDto.setSensorNumber(RandomStringUtils.randomAlphanumeric(4));
        sensorDto.setId(2L);
        sensorDto.setPlotId(1L);
        sensorDto.setSensorsEndpoint(RandomStringUtils.randomAlphabetic(8));

        when(mapper.mapSensorDtoToEntity(sensorDto)).thenReturn(sensor);
        when(mapper.mapSensorEntityToDto(sensor)).thenReturn(sensorDto);
        when(repository.save(sensor)).thenReturn(sensor);

        var response = sensorService.createSensor(sensorDto);

        assertNotNull(response);
        assertEquals(2, response.getId());

    }

    @Test
    void convertToCronExpression() {

        var cronExpression = sensorService.convertToCronExpression(FrequencyType.WEEKLY, 3, 4);
        System.out.println(cronExpression);
    }
}