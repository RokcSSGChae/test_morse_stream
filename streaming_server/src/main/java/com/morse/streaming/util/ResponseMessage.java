package com.morse.streaming.util;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ResponseMessage {
    public final String ID_STREAMER_CONNECTION = "presenterResponse";
    public final String ID_VIEWER_CONNECTION = "viewerResponse";
    public final String ID_ICECANDIDATE = "iceCandidate";
    public final String ACCEPT = "accepted";
    public final String DENIED = "denied";
}
