package com.iot.iotDemo.service;

import com.iot.iotDemo.MqttGateway;
import com.iot.iotDemo.singleton.BlindsState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BlindService {
    private final MqttGateway mqttGateway;

    public ResponseEntity<?> setBlindsStatus(Integer length){
        try {
            mqttGateway.sendToMqtt(Integer.toString(length * 150),"blinds");
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            return ResponseEntity.ok("fail");
        }
    }

    public void setIsAdaptive(Boolean isAdaptive){
        BlindsState.getInstance().setIsAdaptive(isAdaptive);
    }

    public Boolean getIsAdaptive(){
        return BlindsState.getInstance().getIsAdaptive();
    }

    public Integer getLength(){
        return roundToNearestTen(BlindsState.getInstance().getLength() / 150);
    }

    public void setBlindsLengthToSingleton(Integer length){
        Integer lastValue = BlindsState.getInstance().getLength();
        BlindsState.getInstance().setLength(length + lastValue);
    }
    private static int roundToNearestTen(int number) {
        return (int) (Math.round(number / 10.0) * 10);
    }

    public void setBlindsLengthIfIsAdaptive(int illuminate){
        if(BlindsState.getInstance().getIsAdaptive()){
            int percent = roundToNearestTen(numberOfPercentFromIlluminate(illuminate));
            int currentState = BlindsState.getInstance().getLength() / 150;
            if(percent > currentState){
                setBlindsStatus((percent -currentState) *150);
            }else if (percent < currentState){
                setBlindsStatus((currentState - percent) *150);
            }
        }
    }

    public int numberOfPercentFromIlluminate(int illuminate){
        int result = illuminate /250;
        return Math.min(result, 100);
    }
}
