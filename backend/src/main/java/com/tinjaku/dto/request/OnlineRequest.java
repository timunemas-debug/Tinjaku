package com.tinjaku.dto.request;

import com.tinjaku.model.StatusOnOff;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnlineRequest {
    private StatusOnOff statusOnOff;
}