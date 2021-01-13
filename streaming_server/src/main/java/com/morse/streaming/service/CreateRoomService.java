package com.morse.streaming.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morse.streaming.dto.request.CreateRoomRequestDto;
import com.morse.streaming.dto.request.IceCandidateReqeustDto;
import com.morse.streaming.dto.request.ViewingRequestDto;
import com.morse.streaming.dto.response.CreateRoomResponseDto;
import com.morse.streaming.dto.response.IceCandidateResponseDto;
import com.morse.streaming.dto.response.ViewingResponseDto;
import com.morse.streaming.model.Room;
import com.morse.streaming.model.TestUser;
import com.morse.streaming.util.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.kurento.client.*;
import org.kurento.jsonrpc.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CreateRoomService {
    private final KurentoClient kurento;
    private final RedisTemplate redisTemplate;
    List<MediaPipeline> pipelineList = new ArrayList<>();
    private MediaPipeline pipeline;
    private final ResponseMessage responseMessage;
    private final static Logger log = LoggerFactory.getLogger(Room.class);
    private static TestUser testUser = new TestUser();
/*
    public MediaPipeline getPipeline() {
        return pipeline;
    }

    public UserSession getPresenter() {
        return presenter;
    }
*/
    public CreateRoomResponseDto streamerConnection(CreateRoomRequestDto createRoomRequestDto) {
        JsonObject jsonMessage = new JsonObject();
        WebRtcEndpoint webRtcEndpoint;
        if(pipeline==null){
            pipeline = kurento.createMediaPipeline();
        }
        if(testUser.getWebRtcEndpoint()==null) {
            webRtcEndpoint = new WebRtcEndpoint.Builder(pipeline).build();
            testUser.setWebRtcEndpoint(webRtcEndpoint);
            testUser.setPipeline(pipeline);
            //webRtcEndpoint.addIceCandidate();
        }else{
            webRtcEndpoint=testUser.getWebRtcEndpoint();
        }
        webRtcEndpoint.addIceCandidateFoundListener(event -> {
            JsonObject response = new JsonObject();
            response.addProperty("id", "iceCandidate");
            response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
        });

        String sdp = createRoomRequestDto.getOfferSdp();
        String test = webRtcEndpoint.processOffer(sdp);
        //String sdpAnswer = webRtcEndpoint.processAnswer(test);
        webRtcEndpoint.gatherCandidates();

        return CreateRoomResponseDto
                .builder()
                .id(responseMessage.ID_STREAMER_CONNECTION)
                .message(responseMessage.ACCEPT)
                .sdpAnswer(test)
                .build();
    }

    public IceCandidateResponseDto onIceCandidate(IceCandidateReqeustDto iceCandidateReqeustDto) {
        //if(testUser.getWebRtcEndpoint()==null){
        //    MediaPipeline pipeline = kurento.createMediaPipeline();
            //   testUser.setWebRtcEndpoint(new WebRtcEndpoint.Builder(pipeline).build());
        //}
        JsonParser jsonParser = new JsonParser();
        log.info(iceCandidateReqeustDto.getCandidate());
        Object object= jsonParser.parse(iceCandidateReqeustDto.getCandidate());
        JsonObject candidate = (JsonObject)object;

        IceCandidate iceCandidate = new IceCandidate(
                candidate.get("candidate").getAsString(),
                candidate.get("sdpMid").getAsString(),
                candidate.get("sdpMLineIndex").getAsInt());

        //testUser.addCandidate(iceCandidate);

        return IceCandidateResponseDto
                .builder()
                .id(responseMessage.ID_ICECANDIDATE)
                .message(responseMessage.ACCEPT)
                .candidate(candidate.toString())
                .build();
    }

    public ViewingResponseDto viewingConnection(ViewingRequestDto viewingRequestDto) {
        //MediaPipeline pipeline = kurento.createMediaPipeline();
        WebRtcEndpoint viewEndpoint = new WebRtcEndpoint.Builder(pipeline).build();
        viewEndpoint.addIceCandidateFoundListener(event -> {
            JsonObject response = new JsonObject();
            response.addProperty("id", "iceCandidate");
            response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
        });

        testUser.getWebRtcEndpoint().connect(viewEndpoint);
        String sdpOffer = viewingRequestDto.getOfferSdp();
        String sdpAnswer = viewEndpoint.processOffer(sdpOffer);

        return ViewingResponseDto
                .builder()
                .id(responseMessage.ID_VIEWER_CONNECTION)
                .message(responseMessage.ACCEPT)
                .sdpAnswer(sdpAnswer)
                .build();
    }


/*
    public void closePipeline() {
        if (pipeline == null) {
            return;
        }
        pipeline.release(new Continuation<Void>() {
            @Override
            public void onSuccess(Void result) throws Exception {
                log.debug("ROOM {}: Released Pipeline");
            }

            @Override
            public void onError(Throwable cause) throws Exception {
                log.warn("ROOM {}: Could not successfully release Pipeline");
            }
        });
    }

    public void close() throws IOException {

    }

    public void joinByViewer(UserSession user) {

    }

    public void joinByPresenter(UserSession user) {
        if(presenter != null) {
            return;
        }
        createPipeline();
        System.out.println("pipeline : " + (pipeline == null));
        System.out.println("user : " + (user == null));
        presenter = user;
        presenter.setWebRtcEndpoint(new WebRtcEndpoint.Builder(pipeline).build());
    }

    public void removeUser(UserSession user) {

    }
*/
}
