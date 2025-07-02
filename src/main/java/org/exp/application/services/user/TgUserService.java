package org.exp.application.services.user;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.ChosenInlineResult;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.application.bot.handlers.ChosenInlineResultHandler;
import org.exp.application.models.entity.TgUser;
import org.exp.application.repositories.common.TgUserRepository;
import org.exp.application.repositories.common.UserSessionRepository;
import org.exp.application.services.TelegramSenderService;
import org.exp.application.services.botgame.BotGameResultService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TgUserService {

    private final TgUserRepository tgUserRepository;
    private final BotGameResultService botGameStatusService;
    private final TelegramSenderService telegramSenderService;
    private final UserSessionRepository userSessionRepository;

    public Optional<TgUser> getOptionalById(Long id) {
        Optional<TgUser> user = tgUserRepository.findById(id);
        if (user.isEmpty()) {
            log.debug("TgUser not found for ID: {}", id);
        }
        return user;
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

    @Transactional
    public TgUser getOrCreateTgUser(Message message) {
        Long userId = message.from().id();
        Optional<TgUser> optionalUser = getOptionalById(userId);
        if (optionalUser.isPresent()) {
            log.info("Found TgUser with ID: {}, Fullname: {}", userId, optionalUser.get().getFullname());
            return optionalUser.get();
        }

        log.info("Creating new TgUser for ID: {}", userId);
        TgUser newUser = createTgUser(message);
        botGameStatusService.insertDefaultGameStatus(newUser);
        log.info("Created TgUser with ID: {}, Fullname: {}", newUser.getId(), newUser.getFullname());

        return newUser;
    }

    @Async
    public void messageSender() {
        try {
            String message = """
                    <b>🎉 200 Users Reached!</b>
                    
                    Dear friends! Thanks to your support and interest, our <b>TicTacToe Bot</b> has now reached <b>200 users</b>!
                    
                    🤝 Huge thanks to each of you! More fun games, new features, and exciting updates are on the way!
                    
                    🔁 <b>Share the bot and challenge your friends:</b>
                    <a href="https://t.me/xoBrainBot">https://t.me/xoBrainBot</a>
                    
                    /start/start /start/start /start/start
                    """;
            tgUserRepository.findAll().forEach(user -> {
                telegramSenderService.execute(new SendMessage(user.getId(), message).parseMode(ParseMode.HTML));
            });
        } catch (Exception e) {
            log.error("Sending msg error!");
        }
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

        if (firstName == null && lastName == null) {
            return generateDefaultUsername();
        }
        if (firstName != null && firstName.length() == 1 && lastName == null) {
            return generateDefaultUsername();
        }
        if (firstName != null && firstName.length() == 1) {
            return lastName != null ? lastName : generateDefaultUsername();
        }
        if (lastName == null) {
            return firstName != null ? firstName : generateDefaultUsername();
        }
        return firstName + " " + lastName;
    }

    private String generateDefaultUsername() {
        return "User_" + UUID.randomUUID().toString().substring(0, 8);
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

    @Async
    public void getOrCreateTgUserAsync(InlineQuery inlineQuery) {
        getOptionalById(inlineQuery.from().id())
                .orElseGet(() -> {
                    TgUser newUser = createTgUser(inlineQuery);
                    botGameStatusService.insertDefaultGameStatus(newUser);
                    return newUser;
                });
    }

    public TgUser getOrCreateTgUser(InlineQuery inlineQuery) {
        return getOptionalById(inlineQuery.from().id())
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

    public TgUser getOrCreateTgUserAsync(ChosenInlineResult chosenInlineResult) {
        Long userId = chosenInlineResult.from().id();
        String fullname = buildFullNameFromUpdate(chosenInlineResult);
        String username = chosenInlineResult.from().username();

        TgUser newTgUser = TgUser.builder()
                .id(userId)
                .fullname(fullname)
                .username(username)
                .langCode(chosenInlineResult.from().languageCode())
                ._active(true)
                .build();

        return tgUserRepository.save(newTgUser);
    }

    public String buildFullNameFromUpdate(ChosenInlineResult chosenInlineResult) {
        String firstName = chosenInlineResult.from().firstName();
        String lastName = chosenInlineResult.from().lastName();

        if (firstName.length()==1 && lastName == null) {
            return generateDefaultUsername();
        }

        if (firstName.length()==1) return lastName;
        if (lastName == null) return firstName;

        return firstName + " " + lastName;
    }

    public Optional<TgUser> getFindById(Long id) {
        return tgUserRepository.findById(id);
    }
}


/*
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
 */