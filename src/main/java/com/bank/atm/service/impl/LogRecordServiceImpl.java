package com.bank.atm.service.impl;

import com.bank.atm.DTO.ImageDTO;
import com.bank.atm.DTO.LogRecordDTO;
import com.bank.atm.DTO.VideoDTO;
import com.bank.atm.Exception.ResourceNotFoundException;
import com.bank.atm.entity.LogRecord;
import com.bank.atm.repository.LogRecordRepository;
import com.bank.atm.service.LogRecordService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LogRecordServiceImpl implements LogRecordService {

    private final LogRecordRepository logRecordRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public LogRecordServiceImpl(LogRecordRepository logRecordRepository, ObjectMapper objectMapper) {
        this.logRecordRepository = logRecordRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public LogRecord createLogRecord(LogRecordDTO logRecordDTO) {
        LogRecord logRecord = LogRecordDTO.toEntity(logRecordDTO);
        return logRecordRepository.save(logRecord);
    }

    @Override
    public LogRecord updateEndTime(String id) {
        LogRecord logRecord = logRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record"));
        logRecord.setEndTime(LocalDateTime.now());
        return logRecordRepository.save(logRecord);
    }

    @Override
    public List<LogRecordDTO> getAllRecords() {
        return logRecordRepository.findAll().stream()
                .sorted(Comparator.comparing(LogRecord::getStartTime).reversed())
                .map(LogRecordDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Resource> downloadRecord() throws IOException {
            InputStream inputStream = getClass().getResourceAsStream("/static/videos.json");
            if (inputStream == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            List<VideoDTO> videos = objectMapper.readValue(inputStream, new TypeReference<List<VideoDTO>>() {});

            Random random = new Random();
            VideoDTO selectedVideo = videos.get(random.nextInt(videos.size()));

            String videoUrl = selectedVideo.getVideoUrl();

            URL url = new URL(videoUrl);
            InputStreamResource videoResource = new InputStreamResource(url.openStream());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + selectedVideo.getTitle().replace(" ", "_") + ".mp4");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
                    .body(videoResource);
    }

    @Override
    public ResponseEntity<Resource> downloadImage() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/static/Images.json");
        if (inputStream == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<ImageDTO> images = objectMapper.readValue(inputStream, new TypeReference<List<ImageDTO>>() {});

        Random random = new Random();
        ImageDTO selectedImage = images.get(random.nextInt(images.size()));

        String imageUrl = selectedImage.getImageUrl();

        URL url = new URL(imageUrl);
        InputStreamResource imageResource = new InputStreamResource(url.openStream());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + selectedImage.getId() + ".jpg");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                .body(imageResource);
    }
}
