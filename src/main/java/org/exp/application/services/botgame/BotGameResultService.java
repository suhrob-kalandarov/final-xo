package org.exp.application.services.botgame;

import lombok.RequiredArgsConstructor;
import org.exp.application.models.entity.TgUser;
import org.exp.application.models.entity.result.BotGameResult;
import org.exp.application.models.enums.Difficulty;
import org.exp.application.repositories.botgame.BotGameResultRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BotGameResultService {

    private final BotGameResultRepository resultRepository;

    public void incrementWin(Long playerId, Difficulty difficultyLevel) {
        BotGameResult result = getResult(playerId, difficultyLevel);
        result.setWinCount(result.getWinCount() + 1);
        resultRepository.save(result);
    }

    public void incrementDraw(Long playerId, Difficulty difficultyLevel) {
        BotGameResult result = getResult(playerId, difficultyLevel);
        result.setDrawCount(result.getDrawCount() + 1);
        resultRepository.save(result);
    }

    public void incrementLose(Long playerId, Difficulty difficultyLevel) {
        BotGameResult result = getResult(playerId, difficultyLevel);
        result.setLoseCount(result.getLoseCount() + 1);
        resultRepository.save(result);
    }

    private BotGameResult getResult(Long playerId, Difficulty difficultyLevel) {
        return resultRepository.findByPlayerIdAndDifficultyLevel(playerId, difficultyLevel)
                .orElseThrow(() -> new IllegalStateException("No BotGameResult found for playerId: " + playerId + ", difficulty: " + difficultyLevel));
    }

    public String formatGameStatistics(List<BotGameResult> results) {
        // Har bir daraja bo‘yicha natijani olish (agar bo‘lmasa, 0 bo‘ladi)
        BotGameResult easy = findByDifficulty(results, Difficulty.EASY);
        BotGameResult medium = findByDifficulty(results, Difficulty.MEDIUM);
        BotGameResult hard = findByDifficulty(results, Difficulty.HARD);
        BotGameResult extreme = findByDifficulty(results, Difficulty.EXTREME);

        return """
            \s\s\s\s🏆   ⚖️   😭
            😺: %d    %d    %d
            🧠: %d    %d    %d
            😈: %d    %d    %d
            💀: %d    %d    %d
            """.formatted(
                easy.getWinCount(), easy.getDrawCount(), easy.getLoseCount(),
                medium.getWinCount(), medium.getDrawCount(), medium.getLoseCount(),
                hard.getWinCount(), hard.getDrawCount(), hard.getLoseCount(),
                extreme.getWinCount(), extreme.getDrawCount(), extreme.getLoseCount()
        );
    }

    private BotGameResult findByDifficulty(List<BotGameResult> results, Difficulty difficulty) {
        return results.stream()
                .filter(r -> r.getDifficultyLevel() == difficulty)
                .findFirst()
                .orElse(BotGameResult.builder()
                        .winCount(0)
                        .drawCount(0)
                        .loseCount(0)
                        .build());
    }

    public void insertDefaultGameStatus(TgUser user) {
        List<BotGameResult> defaultStatuses = Arrays.stream(Difficulty.values())
                .map(level -> BotGameResult.builder()
                        .player(user)
                        .difficultyLevel(level)
                        .winCount(0)
                        .drawCount(0)
                        .loseCount(0)
                        .build())
                .collect(Collectors.toList());

        resultRepository.saveAll(defaultStatuses);
        System.out.println("✅ Game status default values inserted for userId: " + user.getId());
    }

    public List<BotGameResult> getResultsByUser(Long userId) {
        return resultRepository.findAllByPlayerId(userId);
    }

}
