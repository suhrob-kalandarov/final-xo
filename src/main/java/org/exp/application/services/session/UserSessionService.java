package org.exp.application.services.session;

import lombok.RequiredArgsConstructor;
import org.exp.application.config.DataLoader;
import org.exp.application.models.entity.message.Language;
import org.exp.application.models.entity.session.SessionMenu;
import org.exp.application.models.entity.session.UserSession;
import org.exp.application.repositories.UserSessionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSessionService {

    private final UserSessionRepository repository;

    public UserSession getOrCreate(Long userId) {
        return repository.findById(userId)
                .orElseGet(() -> repository.save(
                        UserSession.builder().userId(userId).language(DataLoader.lang1).build()
                ));
    }

    public void updateMessageId(Long userId, Integer messageId) {
        UserSession session = getOrCreate(userId);
        session.setMessageId(messageId);
        repository.save(session);
    }

    public void updatePage(Long userId, SessionMenu menu) {
        UserSession session = getOrCreate(userId);
        session.setCurrentMenu(menu);
        repository.save(session);
    }

    public void updateCallback(Long userId, String callback) {
        UserSession session = getOrCreate(userId);
        session.setLastCallbackData(callback);
        repository.save(session);
    }

    public void clear(Long userId) {
        repository.deleteById(userId);
    }

    public void updateLanguage(Long userId, Language botLanguage) {
        repository.updateLanguage(botLanguage, userId);
    }
}
