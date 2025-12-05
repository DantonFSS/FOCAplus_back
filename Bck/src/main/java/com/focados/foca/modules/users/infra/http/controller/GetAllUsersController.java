package com.focados.foca.modules.users.infra.http.controller;

import com.focados.foca.modules.users.domain.dtos.response.UserResponseDto;
import com.focados.foca.modules.users.domain.services.GetAllUsersService;
import com.focados.foca.modules.users.domain.services.GetCurrentUserService;
import com.focados.foca.modules.users.domain.services.GetUserByIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller para operações de usuários
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class GetAllUsersController {

    private final GetAllUsersService getAllUsersService;
    private final GetCurrentUserService getCurrentUserService;
    private final GetUserByIdService getUserByIdService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(getAllUsersService.execute());
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        return ResponseEntity.ok(getCurrentUserService.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(getUserByIdService.execute(id));
    }
}
