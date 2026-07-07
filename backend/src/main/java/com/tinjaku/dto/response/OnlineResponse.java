package com.tinjaku.dto.response;

import com.tinjaku.model.StatusOnOff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OnlineResponse {
    private StatusOnOff statusOnOff;
}