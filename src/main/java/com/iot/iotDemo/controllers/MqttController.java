package com.iot.iotDemo.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iot.iotDemo.MqttGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {

    private final MqttGateway mqttGateway;

    @Autowired
    public MqttController(MqttGateway mqttGateway) {
        this.mqttGateway = mqttGateway;
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<?> publish(@RequestBody String mqttMessage){
        try {
        JsonObject convertObject = new Gson().fromJson(mqttMessage,JsonObject.class);
//        mqttGateway.sendToMqtt(convertObject.get("message").toString(),convertObject.get("topic").getAsString());
        mqttGateway.sendToMqtt("1002",convertObject.get("topic").getAsString());
        return ResponseEntity.ok("Success");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("fail");
        }
    }

}
