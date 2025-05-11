package org.exp.application.services.main;

import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.TgUser;
import org.exp.application.repositories.TgUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TgUserService {

    private TgUserRepository tgUserRepository;

    public Optional<TgUser> getById(Long id) {
        return tgUserRepository.findById(id);
    }
}
