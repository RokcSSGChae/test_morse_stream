package com.morse.streaming.model;

import lombok.Builder;
import org.kurento.client.IceCandidate;

import java.util.Date;

public class OnAirRoom {
    int userNickname;
    String title;
    int userIdx;
    Date createAt;
    String thumbnail;
    IceCandidate candidate;

    @Builder
    OnAirRoom(int userNickname, String title, int userIdx, Date createAt, String thumbnail, IceCandidate candidate) {
        this.userNickname = userNickname;
        this.title = title;
        this.userIdx = userIdx;
        this.createAt = createAt;
        this.thumbnail = thumbnail;
        this.candidate = candidate;
    }
}