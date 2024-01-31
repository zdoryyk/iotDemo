package com.iot.iotDemo.controllers;

import com.iot.iotDemo.model.AdaptiveRequest;
import com.iot.iotDemo.model.BlindStatusRequest;
import com.iot.iotDemo.service.BlindService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class BlindController {
    private final BlindService blindService;

    @GetMapping("/blindsState")
    public Integer getBlindState(){
        return blindService.getLength();
    }

    @PostMapping("/blindsState")
    public ResponseEntity<?> setBlindState(@RequestBody BlindStatusRequest blindStatusRequest){
        return blindService.setBlindsStatus(blindStatusRequest.length());
    }

    @PostMapping("/adaptive")
    public ResponseEntity<?> setAdaptive(@RequestBody AdaptiveRequest adaptiveRequest){
        blindService.setIsAdaptive(adaptiveRequest.isAdaptive());
        return ResponseEntity.ok(blindService.getIsAdaptive());
    }
    @GetMapping("/adaptive")
    public ResponseEntity<Boolean> getAdaptive(){
        return ResponseEntity.ok(blindService.getIsAdaptive());
    }
}
