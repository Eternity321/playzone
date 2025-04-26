package com.playzone.service;

import com.playzone.api.dto.request.SportFacilityRequest;
import com.playzone.model.Photo;
import com.playzone.model.SportFacility;
import com.playzone.model.User;
import com.playzone.repository.PhotoRepository;
import com.playzone.repository.SportFacilityRepository;
import com.playzone.repository.SportTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SportFacilityService {

    private static final int MAX_PHOTOS_PER_FACILITY = 5;
    private final SportFacilityRepository facilityRepository;
    private final UserService userService;
    private final AuthService authService;
    private final MinioService minioService;
    private final PhotoRepository photoRepository;
    @Autowired
    private SportTypeRepository sportTypeRepository;

    public List<SportFacility> getAll() {
        return facilityRepository.findAll();
    }

    public SportFacility getById(Long id) {
        return facilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Локация не найдена по id: " + id));
    }

    @Transactional
    public SportFacility create(SportFacilityRequest request) {
        SportFacility facility = new SportFacility();
        facility.setName(request.getName());
        facility.setAddress(request.getAddress());
        facility.setLatitude(request.getLatitude());
        facility.setLongitude(request.getLongitude());
        facility.setDescription(request.getDescription());
        facility.setSportType(
                sportTypeRepository.findById(request.getSportTypeId())
                        .orElseThrow(() -> new IllegalArgumentException("Вид спорта не найден")));

        String username = authService.getCurrentUsername();
        User creator = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        facility.setCreatedBy(creator);
        return facilityRepository.save(facility);
    }

    @Transactional
    public SportFacility update(Long id, SportFacilityRequest request) {
        SportFacility facility = getById(id);
        facility.setName(request.getName());
        facility.setAddress(request.getAddress());
        facility.setLatitude(request.getLatitude());
        facility.setLongitude(request.getLongitude());
        facility.setDescription(request.getDescription());
        facility.setSportType(
                sportTypeRepository.findById(request.getSportTypeId())
                        .orElseThrow(() -> new IllegalArgumentException("Вид спорта не найден")));
        return facilityRepository.save(facility);
    }

    @Transactional
    public void delete(Long id) {
        facilityRepository.deleteById(id);
    }

    @Transactional
    public List<String> uploadFacilityPhotos(Long facilityId, List<MultipartFile> files) {
        SportFacility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException("Локация не найдена"));

        List<Photo> existingPhotos = photoRepository.findAllBySportFacilityId(facilityId);
        if (existingPhotos.size() + files.size() > MAX_PHOTOS_PER_FACILITY) {
            throw new IllegalArgumentException("Превышено максимальное количество фотографий для одной локации (" + MAX_PHOTOS_PER_FACILITY + ")");
        }

        List<String> uploadedFileKeys = new ArrayList<>();
        int startIndex = existingPhotos.size() + 1;

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String contentType = file.getContentType();
            if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
                throw new IllegalArgumentException("Недопустимый тип файла. Только JPEG и PNG.");
            }

            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);

            String objectName = "facility_photos/facility_photo_" + facilityId + "_" + (startIndex + i) + "." + fileExtension;

            try (InputStream inputStream = file.getInputStream()) {
                minioService.uploadFile(objectName, inputStream, contentType);

                Photo photo = new Photo();
                photo.setSportFacility(facility);
                photo.setFileKey(objectName);
                photoRepository.save(photo);

                uploadedFileKeys.add(objectName);
            } catch (IOException e) {
                throw new RuntimeException("Ошибка загрузки файла.", e);
            }
        }

        return uploadedFileKeys;
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}