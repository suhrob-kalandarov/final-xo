package org.exp.application.services.main;

import com.pengrad.telegrambot.model.Message;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.TgUser;
import org.exp.application.repositories.TgUserRepository;
import org.exp.application.services.BotGameResultService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TgUserService {

    private final TgUserRepository tgUserRepository;
    private final BotGameResultService botGameStatusService;

    public Optional<TgUser> getOptionalById(Long id) {
        return tgUserRepository.findById(id);
    }

    public TgUser getById(Long id) {
        return tgUserRepository.findById(id).get();
    }

    public void save(TgUser tgUser) {
        tgUserRepository.save(tgUser);
    }

    public TgUser saveReturn(TgUser tgUser) {
        return tgUserRepository.save(tgUser);
    }

    public TgUser getOrCreateTgUser(Message message) {
        return getOptionalById(message.from().id())
                .orElseGet(() -> {
                    TgUser newUser = createTgUser(message);
                    botGameStatusService.insertDefaultGameStatus(newUser);
                    return newUser;
                });
    }


    private TgUser createTgUser(Message message) {
        Long userId = message.from().id();
        String fullname = buildFullNameFromUpdate(message);
        String username = message.from().username();

        TgUser newTgUser = TgUser.builder()
                .id(userId)
                .fullname(fullname)
                .username(username)
                .langCode(message.from().languageCode())
                ._active(true)
                .build();

        return tgUserRepository.save(newTgUser);
    }

    public String buildFullNameFromUpdate(Message message) {
        String firstName = message.chat().firstName();
        String lastName = message.chat().lastName();

        if (firstName.length()==1 && lastName == null) {
            return generateDefaultUsername();
        }

        if (firstName.length()==1) return lastName;
        if (lastName == null) return firstName;

        return firstName + " " + lastName;
    }

    public String generateDefaultUsername() {
        long i = 1;
        String username;

        do {
            username = "user" + i;
            i++;
        } while (tgUserRepository.existsByUsername(username));

        return username;
    }
}
