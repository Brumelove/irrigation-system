package com.andela.irrigationsystem.delegate;

import com.andela.irrigationsystem.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * @author Brume
 **/
@Slf4j
@Component("SensorDelegate")
@RequiredArgsConstructor
public class SensorDelegate implements JavaDelegate {
    private final SensorService sensorService;

    public static String getValueFromInstance(DelegateExecution execution, String variableName) {
        try {
            return execution.getProcessInstance().getVariable(variableName).toString().trim();
        } catch (Exception ex) {
            throw new BpmnError("Error while retrieving Value for {} from Instance", variableName);
        }
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String currentActivity = delegateExecution.getCurrentActivityId().toLowerCase().trim();
        log.info("Current activity is: {}", currentActivity);
//sensorService.triggerSensor()

    }
}
