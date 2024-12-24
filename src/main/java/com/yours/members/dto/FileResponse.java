package com.yours.members.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {
    private String contentType; // 그림인지, 오디오인지 비디오인지..
    private byte[] bytes;
}
