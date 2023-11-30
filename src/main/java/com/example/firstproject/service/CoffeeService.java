package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    public Iterable<Coffee> index() {
        return coffeeRepository.findAll();
    }
    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }
    public Coffee create(CoffeeDto coffeeDto) {
        Coffee coffee = coffeeDto.toEntity();
        if (coffee.getId() != null) {
            return null;
        }
        return coffeeRepository.save(coffee);
    }
    public Coffee update(CoffeeDto dto, Long id) {
        Coffee coffee = dto.toEntity();
        log.info("id: {}, coffee: {}", id, coffee.toString());
        Coffee target = coffeeRepository.findById(id).orElse(null);
        log.info(target.toString());
        if (coffee.getId() != id || target == null) {
            log.info("id 값이 안맞거나 해당 엔티티가 없어유");
            return null;
        }
        target.patch(coffee);
        return coffeeRepository.save(coffee);
    }
    public Coffee delete(Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if (target == null) {
            return null;
        }
        coffeeRepository.delete(target);
        return target;
    }
}
