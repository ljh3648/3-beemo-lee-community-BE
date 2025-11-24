package com.kakao_tech.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ImageDTO {

    @Getter
    @AllArgsConstructor
    public static class UploadRequest {
        private final String path;
        private final String fileName;
        private final String data;
    }

    @Getter
    @AllArgsConstructor
    public static class UploadResponse {
        private final String imageUrl;
    }
}
