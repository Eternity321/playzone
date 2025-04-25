package com.playzone.service;

import com.playzone.api.dto.request.SportTypeRequest;
import com.playzone.model.SportType;
import com.playzone.repository.SportTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportTypeService {

    private final SportTypeRepository repository;

    public List<SportType> getAll() {
        return repository.findAll();
    }

    public SportType getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Тип спорта не найден: " + id));
    }

    @Transactional
    public SportType create(SportTypeRequest request) {
        if (repository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Тип спорта уже существует: " + request.getName());
        }
        return repository.save(new SportType(null, request.getName()));
    }

    @Transactional
    public SportType update(Integer id, SportTypeRequest request) {
        SportType existing = getById(id);
        existing.setName(request.getName());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
