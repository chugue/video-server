package com.project.videodemo.video;


import com.project.videodemo._core.ApiUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@RequiredArgsConstructor
@Controller
public class VideoController {
    private final VideoService videoService;
    private static final String UPLOAD_DIR = "videolocation/";
    private final Path videoLocation = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();


    @GetMapping("/")
    public String index() {
        return "index";
    }


    @PostMapping("/upload")
    public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file!", HttpStatus.BAD_REQUEST);
        }

        try {
            // 파일을 로컬에 저장
            Path targetLocation = videoService.saveFileToLocal(file);

            // FFmpeg 명령어 준비 - 진짜 파일 이름과, 어떤 이름으로 변환할 것인지에 대해서 명시해야 된다.
            String fileName = targetLocation.getFileName().toString();
            String sanitizedBaseFileName = fileName.substring(0, fileName.lastIndexOf('.'));
            Path directoryPath = targetLocation.getParent();
            videoService.encode(targetLocation.toString(), sanitizedBaseFileName, directoryPath);


            // 인코딩된 파일들을 S3에 업로드
            videoService.uploadToS3(directoryPath, sanitizedBaseFileName);

            // mpd파일에서 m4s호출 경로 수정 CORS 걸림
            Path mpdFilePath = directoryPath.resolve(sanitizedBaseFileName + ".mpd");
            RespDTO respDTO = new RespDTO(mpdFilePath);

            // 로컬 파일 및 디렉토리 삭제
            videoService.deleteLocalFiles(directoryPath);

            return ResponseEntity.ok(new ApiUtil<>(respDTO));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File processing failed: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Data
    public class RespDTO {
        private String filePath;

        public RespDTO(Path mpdFilePath) {
            this.filePath = mpdFilePath.toString();
        }
    }

//    @PostMapping("/upload2")
//    public ResponseEntity<?> singleFileUpload2(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return new ResponseEntity<>("Please select a file!", HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            // 파일을 로컬에 저장
//            Path targetLocation = videoService.saveFileToLocal(file);
//
//            // 파일 이름 및 경로 설정
//            String fileName = targetLocation.getFileName().toString();
//            String baseFileName = fileName.substring(0, fileName.lastIndexOf('.'));
//            Path directoryPath = targetLocation.getParent();
//
//            // 인코딩
//            videoService.encodeMultipleResolutions(targetLocation.toString(), baseFileName, directoryPath);
//
//            // 암호화 및 패키징
//            videoService.encryptAndPackage(baseFileName, directoryPath);
//
//
////            // 인코딩된 파일들을 S3에 업로드
////            videoService.uploadToS3(directoryPath, baseFileName);
//
//            // mpd파일에서 m4s호출 경로 수정 CORS 걸림
//            Path mpdFilePath = directoryPath.resolve(baseFileName + ".mpd");
//            RespDTO respDTO = new RespDTO(mpdFilePath);
//
////            // 로컬 파일 및 디렉토리 삭제
////            videoService.deleteLocalFiles(directoryPath);
//
//            return ResponseEntity.ok(new ApiUtil<>(respDTO));
//
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File processing failed: " + e.getMessage());
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }



    //    @GetMapping("/videos")
//    public ResponseEntity<?> getVideo(@RequestParam("filename") String filename) {
//        try {
//            // URL 인코딩된 디렉토리와 파일 이름을 디코딩
//            String decodedFilename = URLDecoder.decode(filename, StandardCharsets.UTF_8.toString());
//            // 확장자 위치를 찾음
//            int extensionIndex = decodedFilename.lastIndexOf('.');
//
//            // 전체 파일 경로 조합
//            String decodedDirectoryName = decodedFilename.substring(0, extensionIndex);
//            Path filePath = videoLocation.resolve(Paths.get(decodedDirectoryName, decodedFilename).normalize());
//            System.out.println("Requested file path: " + filePath.toString()); // 경로가 어떻게 찍히는 지 확인
//
//            Resource resource = new UrlResource(filePath.toUri()); // 여기서 videolocation/파일명/파일명.mpd 로 실행시키는 로직.
//
//            //
//            if (resource.exists() && resource.isReadable()) {
//                System.out.println("filename : " + resource.getFilename());
//                return ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
//                        .contentType(MediaType.parseMediaType("application/dash+xml"))
//                        .body(resource);
//            } else {
//                System.out.println("File not found or not readable: " + filePath.toString());
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).build();
//        }
//    }

}

