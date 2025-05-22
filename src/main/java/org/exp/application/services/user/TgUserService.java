package org.exp.application.services.user;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;
import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.TgUser;
import org.exp.application.repositories.common.TgUserRepository;
import org.exp.application.services.botgame.BotGameResultService;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    public void getOrCreateTgUser(Message message) {
        getOptionalById(message.from().id())
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

    public TgUser getOrCreateTgUser(CallbackQuery callbackQuery) {
        return getOptionalById(callbackQuery.from().id())
                .orElseGet(() -> {
                    TgUser newUser = createTgUser(callbackQuery);
                    botGameStatusService.insertDefaultGameStatus(newUser);
                    return newUser;
                });
    }

    private TgUser createTgUser(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.from().id();
        String fullname = buildFullNameFromUpdate(callbackQuery);
        String username = callbackQuery.from().username();

        TgUser newTgUser = TgUser.builder()
                .id(userId)
                .fullname(fullname)
                .username(username)
                .langCode(callbackQuery.from().languageCode())
                ._active(true)
                .build();

        return tgUserRepository.save(newTgUser);
    }

    public String buildFullNameFromUpdate(CallbackQuery callbackQuery) {
        String firstName = callbackQuery.from().firstName();
        String lastName = callbackQuery.from().lastName();

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

    @Async
    public void getOrCreateTgUserAsync(InlineQuery inlineQuery) {
        getOptionalById(inlineQuery.from().id())
                .orElseGet(() -> {
                    TgUser newUser = createTgUser(inlineQuery);
                    botGameStatusService.insertDefaultGameStatus(newUser);
                    return newUser;
                });
    }

    public void getOrCreateTgUser(InlineQuery inlineQuery) {
        getOptionalById(inlineQuery.from().id())
                .orElseGet(() -> {
                    TgUser newUser = createTgUser(inlineQuery);
                    botGameStatusService.insertDefaultGameStatus(newUser);
                    return newUser;
                });
    }


    private TgUser createTgUser(InlineQuery inlineQuery) {
        Long userId = inlineQuery.from().id();
        String fullname = buildFullNameFromUpdate(inlineQuery);
        String username = inlineQuery.from().username();

        TgUser newTgUser = TgUser.builder()
                .id(userId)
                .fullname(fullname)
                .username(username)
                .langCode(inlineQuery.from().languageCode())
                ._active(true)
                .build();

        return tgUserRepository.save(newTgUser);
    }


    public String buildFullNameFromUpdate(InlineQuery inlineQuery) {
        String firstName = inlineQuery.from().firstName();
        String lastName = inlineQuery.from().lastName();

        if (firstName.length()==1 && lastName == null) {
            return generateDefaultUsername();
        }

        if (firstName.length()==1) return lastName;
        if (lastName == null) return firstName;

        return firstName + " " + lastName;
    }
}
