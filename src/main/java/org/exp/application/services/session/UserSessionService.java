package org.exp.application.services.session;

import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.session.SessionPage;
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
                        UserSession.builder().userId(userId).build()
                ));
    }

    public void updateMessageId(Long userId, Integer messageId) {
        UserSession session = getOrCreate(userId);
        session.setMessageId(messageId);
        repository.save(session);
    }

    public void updatePage(Long userId, SessionPage page) {
        UserSession session = getOrCreate(userId);
        session.setCurrentPage(page);
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
}
