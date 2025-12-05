package com.focados.foca.modules.users.domain.services;

import com.focados.foca.modules.users.database.repository.UserRepository;
import com.focados.foca.modules.users.domain.dtos.response.UserResponseDto;
import com.focados.foca.modules.users.domain.dtos.mappers.UserMapper;
import com.focados.foca.shared.common.utils.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetCurrentUserService {

    private final UserRepository userRepository;

    public UserResponseDto execute() {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário autenticado com ID " + userId + " não encontrado"));
        return UserMapper.mappingToUserListResponse(user);
    }
}

