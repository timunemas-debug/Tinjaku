package com.tinjaku.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfileResponse {
    
    private String namaDepan;
    private String namaBelakang;
    private String email;
}
