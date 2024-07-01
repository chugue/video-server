package com.project.videodemo.video;

import com.project.videodemo._core.AwsProperties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
@Service
public class VideoService {
    private final S3Client s3;
    private final AwsProperties awsProperties;
    private static final String UPLOAD_DIR = "videolocation/";

    @Value("${CONTENT_KEY}")
    private String contentKey;

    @Value("${KEY_ID}")
    private String keyId;

    @Value("${SHAKA_PACKAGER_PATH}")
    private String shakaPackagerPath;



    @Transactional
    public void encodeMultipleResolutions(String inputFilePath, String baseFileName, Path directoryPath) throws IOException, InterruptedException {
        String[] resolutions = {"480p", "720p", "1080p"};
        String[] bitrates = {"800k", "1500k", "3000k"};
        String[] sizes = {"854x480", "1280x720", "1920x1080"};

        for (int i = 0; i < resolutions.length; i++) {
            String resolution = resolutions[i];
            String bitrate = bitrates[i];
            String size = sizes[i];

            String outputFilePath = directoryPath.resolve(baseFileName + "_" + resolution + ".mp4").toString();

            ProcessBuilder pb = new ProcessBuilder(
                    "ffmpeg",
                    "-i", inputFilePath,
                    "-map", "0:v", "-map", "0:a",
                    "-b:v", bitrate, "-s:v", size,
                    "-c:v", "libx264",
                    "-c:a", "aac",
                    "-video_track_timescale", "1000",
                    "-reset_timestamps", "1",
                    "-y", outputFilePath
            );

            pb.directory(directoryPath.toFile());
            pb.redirectErrorStream(true);
            Process process = pb.start();

            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("FFmpeg command failed with exit code " + exitCode);
            }
        }

        // 오디오만 추출
        String audioOutputFilePath = directoryPath.resolve(baseFileName + "_audio.mp4").toString();
        ProcessBuilder audioPb = new ProcessBuilder(
                "ffmpeg",
                "-i", inputFilePath,
                "-vn",
                "-c:a", "aac",
                "-ar", "44100",
                "-reset_timestamps", "1",
                "-y", audioOutputFilePath
        );

        audioPb.directory(directoryPath.toFile());
        audioPb.redirectErrorStream(true);
        Process audioProcess = audioPb.start();

        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(audioProcess.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        int audioExitCode = audioProcess.waitFor();
        if (audioExitCode != 0) {
            throw new RuntimeException("FFmpeg command failed with exit code " + audioExitCode);
        }
    }


    // 암호화 메소드
    @Transactional
    public void encryptAndPackage(String baseFileName, Path directoryPath) throws IOException, InterruptedException {
        String[] resolutions = {"480p", "720p", "1080p"};

        List<String> command = new ArrayList<>();
        command.add(shakaPackagerPath);

        for (String resolution : resolutions) {
            String mp4InputFile = directoryPath.resolve(baseFileName + "_" + resolution + ".mp4").toString();
            String videoInitSegmentPath = directoryPath.resolve(baseFileName + "_init_" + resolution + ".m4s").toString();
            String videoSegmentPath = directoryPath.resolve(baseFileName + "_chunk_" + resolution + "_$Number%05d$.m4s").toString();

            command.add("in=" + mp4InputFile + ",stream=video,init_segment=" + videoInitSegmentPath + ",segment_template=" + videoSegmentPath);
        }

        String audioInitSegmentPath = directoryPath.resolve(baseFileName + "_init_audio.m4s").toString();
        String audioSegmentPath = directoryPath.resolve(baseFileName + "_chunk_audio_$Number%05d$.m4s").toString();
        String audioInputFile = directoryPath.resolve(baseFileName + "_audio.mp4").toString();

        command.add("in=" + audioInputFile + ",stream=audio,init_segment=" + audioInitSegmentPath + ",segment_template=" + audioSegmentPath );

        String encryptedOutputMpd = directoryPath.resolve(baseFileName + ".mpd").toString();

        command.add("--enable_raw_key_encryption");
        command.add("--keys");
        command.add("label=:key_id=" + keyId + ":key=" + contentKey);
        command.add("--mpd_output");
        command.add(encryptedOutputMpd);
        command.add("--segment_duration");
        command.add("4");


        ProcessBuilder pb = new ProcessBuilder(command);

        pb.directory(directoryPath.toFile());
        pb.redirectErrorStream(true);
        Process process = pb.start();

        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("shaka-packager command failed with exit code " + exitCode);
        }

        // 필요 없는 파일 삭제
        for (String resolution : resolutions) {
            Files.deleteIfExists(directoryPath.resolve(baseFileName + "_" + resolution + ".mp4"));
            Files.deleteIfExists(directoryPath.resolve(baseFileName + ".mp4"));
        }
        Files.deleteIfExists(directoryPath.resolve(baseFileName + "_audio.mp4"));
    }

    ///////////////////////////////////////////////////////////////////////////////



    // 파일을 인코딩 하는 로직
    @Transactional
    public void encode(String inputFilePath, String baseFileName, Path directoryPath) throws IOException, InterruptedException {

        // 파일 이름을 소문자로 변환하고, 공백을 하이픈으로 대체
        String outputFilePath = baseFileName + ".mpd";
        String initSegmentPath = baseFileName + "_init_$RepresentationID$.m4s";
        String mediaSegmentPath = baseFileName + "_chunk_$RepresentationID$_$Number%05d$.m4s";

        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg",
                "-i", inputFilePath,                      // 입력 파일 경로
                "-map", "0:v", "-map", "0:a",             // 모든 비디오와 오디오 스트림을 맵핑
                "-b:v:0", "3000k", "-s:v:0", "1920x1080", // 첫 번째 비디오 스트림: 3000kbps, 1920x1080 (Full HD)
                "-b:v:1", "1500k", "-s:v:1", "1280x720",  // 두 번째 비디오 스트림: 1500kbps, 1280x720 (HD)
                "-b:v:2", "800k", "-s:v:2", "854x480",    // 세 번째 비디오 스트림: 800kbps, 854x480 (SD)
                "-c:v", "libx264",                        // 비디오 코덱: libx264 (H.264)
                "-c:a", "aac",                            // 오디오 코덱: AAC
                "-f", "dash",                             // 출력 포맷: DASH
                "-seg_duration", "4",                     // 세그먼트 길이: 4초
                "-use_template", "1",                     // 템플릿 사용
                "-use_timeline", "1",                     // 타임라인 사용
                "-init_seg_name", initSegmentPath,        // 초기화 세그먼트 파일 이름
                "-preset", "faster",
                "-media_seg_name", mediaSegmentPath,      // 미디어 세그먼트 파일 이름
                "-threads", "0",
                outputFilePath                            // 출력 파일 경로
        );

        pb.directory(directoryPath.toFile()); // Set the working directory to directoryPath
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // 별도의 스레드에서 FFmpeg 출력 읽기
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // 프로세스의 종료를 기다림
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("FFmpeg command failed with exit code " + exitCode);
        }
    }

    // S3로 업로드 하는 로직
    public void uploadToS3(Path directoryPath, String baseFileName) throws IOException {
        try (Stream<Path> paths = Files.walk(directoryPath)) {
            List<Path> filesToUpload = paths.filter(Files::isRegularFile).collect(Collectors.toList());

            for (Path fileToUpload : filesToUpload) {
                String s3Key = "videolocation/" + baseFileName + "/" + fileToUpload.getFileName().toString();
                s3.putObject(
                        PutObjectRequest.builder()
                                .bucket(awsProperties.getBucket())
                                .key(s3Key)
                                .build(),
                        software.amazon.awssdk.core.sync.RequestBody.fromFile(fileToUpload)
                );
            }
        }
    }

    // 파일을 파싱해서, 경로 지정해서 저장
    @Transactional
    public Path saveFileToLocal(MultipartFile file) throws IOException {
        // 파일이름을 확장자로부터 분리, 없다면 output 이름으로 지정
        String originalFileName = file.getOriginalFilename();
        String baseFileName = originalFileName != null ? originalFileName.substring(0, originalFileName.lastIndexOf('.')) : "output";
        String sanitizedBaseFileName = baseFileName.toLowerCase().replaceAll("\\s+", "_");
        String fileExtension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf('.')) : ".mp4";

        // 파일 명에 따라 디렉토리 생성
        Path videoLocation = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
        Path directoryPath = videoLocation.resolve(sanitizedBaseFileName);

        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // 파일을 서버에 저장
        Path targetLocation = directoryPath.resolve(sanitizedBaseFileName + fileExtension);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return targetLocation;
    }

    // 인코딩후 파일
    @Transactional
    public void deleteLocalFiles(Path directoryPath) {
        try (Stream<Path> paths = Files.walk(directoryPath)) {
            paths.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            // 로깅 또는 에러 처리 로직 추가 가능
            System.err.println("Failed to delete files: " + e.getMessage());
        }
    }
}
