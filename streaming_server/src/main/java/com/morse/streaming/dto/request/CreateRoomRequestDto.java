package com.morse.streaming.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateRoomRequestDto {
    private String roomTitle;
    private String offerSdp;
    private String id;
}
