package com.kakao_tech.community.dto;

import lombok.Getter;

public class ImageDTO {

    @Getter
    public static class UploadRequest{
        private final String path;
        private final String fileName;
        private final String data;


        public UploadRequest(String path, String fileName, String data) {
            this.path = path;
            this.fileName = fileName;
            this.data = data;
        }
    }

    public static class UploadResponse{
    }

    public static class GetResponse{
    }

}
