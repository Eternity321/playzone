package com.playzone.service;

import com.playzone.api.dto.request.SportFacilityRequest;
import com.playzone.model.SportFacility;
import com.playzone.model.User;
import com.playzone.repository.SportFacilityRepository;
import com.playzone.repository.SportTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportFacilityService {

    private final SportFacilityRepository facilityRepository;
    private final UserService userService;
    private final AuthService authService;
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
}
