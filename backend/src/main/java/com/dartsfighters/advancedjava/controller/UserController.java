package com.dartsfighters.advancedjava.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dartsfighters.advancedjava.controller.dto.OptionDto;
import com.dartsfighters.advancedjava.controller.dto.UserDto;
import com.dartsfighters.advancedjava.domain.User;
import com.dartsfighters.advancedjava.domain.UserRole;
import com.dartsfighters.advancedjava.repository.UserRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @GetMapping("/options")
    public List<OptionDto> getOptions() {
        return userRepository.findAll().stream()
            .map(user -> new OptionDto(user.getId().toString(), user.getUsername()))
            .collect(Collectors.toList());
    }

    @Secured(UserRole.ROLE_ADMIN)
    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> list(
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "id,asc") String sort
    ) {
        Pageable pageable = createPageable(limit, page, sort);
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedUserFilter");
        Page<User> itemsPage = this.userRepository.findAll(pageable);

        return ResponseEntity.ok(itemsPage.getContent().stream()
            .map(elem -> new UserDto(elem.getId(), elem.getEmail(), elem.getUsername(), elem.getRole()))
            .collect(Collectors.toList()));
    }

    private Pageable createPageable(int limit, int page, String sort) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
        return PageRequest.of(page-1, limit, Sort.by(direction, sortParams[0]));
    }

    @Secured(UserRole.ROLE_ADMIN)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("userId") Integer userId) {
        Map<String, String> response = new HashMap<>();
        User user = this.userRepository.findById(userId).get();
        this.userRepository.delete(user);
        response.put("message", "Deletion successful");
        return ResponseEntity.ok(response);
    }

    @Secured(UserRole.ROLE_ADMIN)
    @PostMapping("/{userId}/promote")
    public ResponseEntity<Map<String, String>> promote(@PathVariable("userId") Integer userId) {
        Map<String, String> response = new HashMap<>();
        User user = this.userRepository.findById(userId).get();
        user.setRole(UserRole.ROLE_ADMIN);
        this.userRepository.save(user);
        response.put("message", "Promotion successful");
        return ResponseEntity.ok(response);
    }
}
