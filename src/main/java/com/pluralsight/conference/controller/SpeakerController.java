package com.pluralsight.conference.controller;

import com.pluralsight.conference.model.Speaker;
import com.pluralsight.conference.service.SpeakerService;
import com.pluralsight.conference.util.ServiceError;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class SpeakerController {

    private SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @PostMapping("speaker")
    public Speaker create(@RequestBody Speaker speaker){
        System.out.println("Name : " + speaker.getName());
        return speakerService.create(speaker);
    }

    @GetMapping("speakers")
    public List<Speaker> getSpeakers() {
        return speakerService.findAll();
    }


    @GetMapping("/speaker/{id}")
    public Speaker getSpeaker(@PathVariable("id") int id){
        return speakerService.getSpeaker(id);
    }

    @PutMapping("/speaker")
    public Speaker updateSpeaker(@RequestBody Speaker speaker){
        return speakerService.updateSpeaker(speaker);
    }

    @GetMapping("/speaker/batch")
    public Object batch(){
        speakerService.batch();
        return null;
    }

    @DeleteMapping("/speaker/{id}")
    public Object deleteSpeaker(@PathVariable int id){
        speakerService.delete(id);
        return null;
    }

    @GetMapping("/speaker/exception-test")
    public Object test(){
        throw new DataAccessException("Testing Exception Thrown") { };
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ServiceError> handle(RuntimeException exception){
        ServiceError serviceError = new ServiceError(HttpStatus.OK.value(), exception.getMessage());
        return  new ResponseEntity<>(serviceError, HttpStatus.OK);
    }


}
