package com.morse.streaming.dto.request;

import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewingRequestDto {
    private String roomTitle;
    private String offerSdp;
    private String id;
}
