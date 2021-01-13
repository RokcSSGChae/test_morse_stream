package com.morse.streaming.controller;

import com.morse.streaming.dto.CommonMessage;
import com.morse.streaming.dto.request.CreateRoomRequestDto;
import com.morse.streaming.dto.request.IceCandidateReqeustDto;
import com.morse.streaming.dto.request.ViewingRequestDto;
import com.morse.streaming.dto.response.CreateRoomResponseDto;
import com.morse.streaming.dto.response.IceCandidateResponseDto;
import com.morse.streaming.dto.response.ViewingResponseDto;
import com.morse.streaming.service.CreateRoomService;
import com.sun.istack.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("room")
@RequiredArgsConstructor
public class StreamingController {
    private final CreateRoomService createRoomService;
    @PostMapping("/create")
    public ResponseEntity<CreateRoomResponseDto> createRoomController(
                                                    @RequestBody CreateRoomRequestDto createRoomRequestDto) {
        CreateRoomResponseDto roomResponseDto = createRoomService.streamerConnection(createRoomRequestDto);
        return ResponseEntity.ok().body(roomResponseDto);
    }

    @PostMapping("/icecandidate")
    public ResponseEntity<IceCandidateResponseDto> onIceCandidateController(
            @RequestBody IceCandidateReqeustDto iceCandidateReqeustDto) {
        IceCandidateResponseDto iceCandidateResponseDto = createRoomService.onIceCandidate(iceCandidateReqeustDto);
        return ResponseEntity.ok().body(iceCandidateResponseDto);
    }

    @PostMapping("/viewer")
    public ResponseEntity<ViewingResponseDto> viewerController(
            @RequestBody ViewingRequestDto viewingRequestDto) {
        ViewingResponseDto viewingResponseDto = createRoomService.viewingConnection(viewingRequestDto);
        return ResponseEntity.ok().body(viewingResponseDto);
    }

}
