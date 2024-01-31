package com.iot.iotDemo.service;

import com.iot.iotDemo.MqttGateway;
import com.iot.iotDemo.singleton.BlindsState;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BlindService {
    private final MqttGateway mqttGateway;

    public ResponseEntity<?> setBlindsStatus(Integer length){
        try {
            mqttGateway.sendToMqtt(Integer.toString(length * 270),"blinds");
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
        return roundToNearestTen(BlindsState.getInstance().getLength() / 270);
    }

    public void setBlindsLengthToSingleton(Integer length){
        Integer lastValue = BlindsState.getInstance().getLength();
        BlindsState.getInstance().setLength(length + lastValue);
    }
    private static int roundToNearestTen(int number) {
        return (int) (Math.round(number / 10.0) * 10);
    }

    public void setBlindsLengthIfIsAdaptive(int length){
        if(BlindsState.getInstance().getIsAdaptive()){
            if(length < 8000){
                int currentState = BlindsState.getInstance().getLength();
                int steps = 13500 - currentState;
                setBlindsStatus(steps/270);
            } else if (length < 12000 && length > 8000) {
                int currentState = BlindsState.getInstance().getLength();
                int steps = 18900 - currentState;
                setBlindsStatus(steps/270);
            } else if(length > 12000){
                int currentState = BlindsState.getInstance().getLength();
                int steps = 27000 - currentState;
                setBlindsStatus(steps/270);
            }
        }
    }
}
