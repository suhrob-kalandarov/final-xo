package org.exp.application.utils;

public interface Constants {
    Integer EMPTY = 0;

    // Xabar kalitlari
    String EMPTY_SIGN = "⬜";
    String X_SIGN = "❌";
    String O_SIGN = "⭕";

    String START_MSG = "start_msg";
    String BACK_BUTTON_MSG = "back_btn";
    String PLAY_WITH_BOT_BTN = "play_with_bot_btn";
    String PLAY_WITH_FRIEND_BTN = "play_with_friend_btn";
    String LANGUAGE_MSG = "language_msg";

    //String STATISTICS_MSG = "statistics_msg";
    String STATISTICS_BTN = "statistics_btn";

    String RESULT_MSG = "<b>%s\n\n%s\n\n%s</b>";
    String YOU_WON_MSG = "win_msg";
    String DRAW_MSG = "draw_msg";
    String YOU_LOST_MSG = "lose_msg";

    String BOARD_MSG = "board_msg";

    String USER_STATISTICS_MSG = """
            bot-game_statistics_msg
            <b>
                Statistics:
            </b>
            \\n
            <pre>
                %s
            </pre>
            """;
    String SUPPORT_BTN = "support_btn";

    String DIFFICULTY_LEVEL_MSG = "difficulty_level_msg";
    String DIFFICULTY_LEVEL_BTN = "difficulty_level_btn";

    String LEVEL = "bot-game-level_";
    String LEVEL_EASY = "level_easy";
    String LEVEL_AVERAGE = "level_medium";
    String LEVEL_DIFFICULT = "level_hard";
    String LEVEL_EXTREME = "level_extreme";

    String CHOOSE_DIFFICULTY_LEVEL = "choose_difficulty_level_msg";

    String CHOOSE_LANG = "choose_language";
    String CHOOSE_LANG_EN_MSG = "Choose language:";
    String LANG_SUCCESS_MSG = "language_success_msg";
    String CHOOSE_SYMBOL_MSG = "choose_symbol_msg";
    String GAME_MENU_MSG = "in_game_menu_msg";
    //String CHOOSE_AN_OPTION = "choose_an_option";
    String WARNING_MSG = "warning_msg";

    // cmd keys
    String START = "/start";
    String CELL = "cell_";
    String BACK = "back";
    String LANG = "lang_";
}
