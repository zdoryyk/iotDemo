package com.iot.iotDemo.singleton;

import com.iot.iotDemo.service.BlindService;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class BlindsState {

    private static BlindsState instance;
    private Integer length;
    private Boolean isAdaptive;

    private BlindsState (){
        this.length = 0;
        isAdaptive = false;
    }

    public static synchronized BlindsState getInstance(){
        if(instance == null){
            instance = new BlindsState();
        }
        return instance;
    }
}
