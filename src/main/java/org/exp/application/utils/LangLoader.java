package org.exp.application.utils;

import lombok.RequiredArgsConstructor;
import org.exp.application.config.DataLoader;
import org.exp.application.models.entity.message.Language;
import org.exp.application.models.entity.message.Translation;
import org.exp.application.models.entity.session.SessionMenu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LangLoader {

    Language lang = DataLoader.lang1;
    Language lang2 = DataLoader.lang2;
    Language lang3 = DataLoader.lang3;
    Language lang4 = DataLoader.lang4;

    public List<Translation> save2LangMsgs(){
        return List.of(
                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang)
                        .key(Constants.HOME_MSG)
                        .value("👤%s\nWelcome to the game! 🎮")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang)
                        .key(Constants.PLAY_WITH_BOT_BTN)
                        .value("🤖Play with Bot")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang)
                        .key(Constants.PLAY_WITH_FRIEND_BTN)
                        .value("👫Play with Friend")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang)
                        .key(Constants.LANGUAGE_BTN)
                        .value("🌐Language")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.LANGUAGE)
                        .language(lang)
                        .key(Constants.LANGUAGE_MSG)
                        .value("🌍Choose Language:")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang)
                        .key(Constants.SUPPORT_BTN)
                        .value("📩Support")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang)
                        .key(Constants.BOT_GAME_STATISTICS_MSG)
                        .value("""
                                <b>Statistics:</b>
                                <pre>%s</pre>""")
                        .build(),


                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOT_GAME)
                        .language(lang)
                        .key(Constants.PLAY)
                        .value("🤖Play")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOT_GAME)
                        .language(lang)
                        .key(Constants.DIFFICULTY_LEVEL_BTN)
                        .value("😺Difficulty💀")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang)
                        .key(Constants.DIFFICULTY_LEVEL_MSG)
                        .value("🎚️Choose difficulty level:")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang)
                        .key(Constants.LEVEL_EASY)
                        .value("😺Easy")
                        .build(),
                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang)
                        .key(Constants.LEVEL_MEDIUM)
                        .value("🧠Medium")
                        .build(),
                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang)
                        .key(Constants.LEVEL_HARD)
                        .value("😈Hard")
                        .build(),
                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang)
                        .key(Constants.LEVEL_EXTREME)
                        .value("💀Extreme")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.SIGN)
                        .language(lang)
                        .key(Constants.SIGN_MENU_MSG)
                        .value("❓Choose your sign:")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang)
                        .key(Constants.BOARD_MENU_MSG)
                        .value("""
                                🚀The game has started!
                                 You: %s
                                 Bot: %s
                                """)
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang)
                        .key(Constants.BOARD_MSG)
                        .value("Board:")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang)
                        .key(Constants.DRAW_MSG)
                        .value("🤝 It's a draw!")
                        .build(),
                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang)
                        .key(Constants.YOU_WON_MSG)
                        .value("🎉 You won!")
                        .build(),
                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang)
                        .key(Constants.YOU_LOST_MSG)
                        .value("💔 You lost!")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOT_GAME)
                        .language(lang)
                        .key(Constants.BACK_BTN)
                        .value("🔙Back")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.NONE)
                        .language(lang)
                        .key(Constants.WARNING_MSG)
                        .value("⚠️Invalid action. Try again!")
                        .build(),


                /// Ruskiy msgs
                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang2)
                        .key(Constants.HOME_MSG)
                        .value("👤%s\nДобро пожаловать в игру! 🎮")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang2)
                        .key(Constants.PLAY_WITH_BOT_BTN)
                        .value("🤖Играть с ботом")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang2)
                        .key(Constants.PLAY_WITH_FRIEND_BTN)
                        .value("👫Играть с другом")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang2)
                        .key(Constants.LANGUAGE_BTN)
                        .value("🌐Язык")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.LANGUAGE)
                        .language(lang2)
                        .key(Constants.LANGUAGE_MSG)
                        .value("🌍Выберите язык:")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang2)
                        .key(Constants.SUPPORT_BTN)
                        .value("📩Поддержка")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang2)
                        .key(Constants.BOT_GAME_STATISTICS_MSG)
                        .value("""
                    <b>Статистика:</b>
                    <pre>%s</pre>""")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOT_GAME)
                        .language(lang2)
                        .key(Constants.PLAY)
                        .value("🤖Играть")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOT_GAME)
                        .language(lang2)
                        .key(Constants.DIFFICULTY_LEVEL_BTN)
                        .value("😺Сложность💀")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang2)
                        .key(Constants.DIFFICULTY_LEVEL_MSG)
                        .value("🎚️Выберите уровень сложности:")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang2)
                        .key(Constants.LEVEL_EASY)
                        .value("😺Легко")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang2)
                        .key(Constants.LEVEL_MEDIUM)
                        .value("🧠Средне")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang2)
                        .key(Constants.LEVEL_HARD)
                        .value("😈Сложно")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang2)
                        .key(Constants.LEVEL_EXTREME)
                        .value("💀Экстремально")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.SIGN)
                        .language(lang2)
                        .key(Constants.SIGN_MENU_MSG)
                        .value("❓Выберите свой знак:")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang2)
                        .key(Constants.BOARD_MENU_MSG)
                        .value("""
                    🚀Игра началась!
                     Вы: %s
                     Бот: %s
                    """)
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang2)
                        .key(Constants.BOARD_MSG)
                        .value("Поле:")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang2)
                        .key(Constants.DRAW_MSG)
                        .value("🤝 Ничья!")
                        .build(),
                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang2)
                        .key(Constants.YOU_WON_MSG)
                        .value("🎉 Вы выиграли!")
                        .build(),
                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang2)
                        .key(Constants.YOU_LOST_MSG)
                        .value("💔 Вы проиграли!")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOT_GAME)
                        .language(lang2)
                        .key(Constants.BACK_BTN)
                        .value("🔙Назад")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.NONE)
                        .language(lang2)
                        .key(Constants.WARNING_MSG)
                        .value("⚠️Неверное действие. Попробуйте снова!")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.INFO)
                        .language(lang)
                        .key(Constants.INFO_MENU_MSG)
                        .value("""
                                ❗Play
                                You can only use the provided keyboard to play with the bot.
                                
                                🕹How to play
                                During the game, you will be given buttons corresponding to the cells. Press the ⬜ button to make a move.
                                
                                👥Multiplayer
                                If the game does not finish within 5 minutes, it will be canceled, and the messages will be replaced.
                                
                                📌About the bot
                                We do not save or respond to text messages!
                                
                                Bot channel: @HowdyBots
                                """
                        ).build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.INFO)
                        .language(lang2)
                        .key(Constants.INFO_MENU_MSG)
                        .value("""
                                ❗Игра
                                Вы можете использовать только предоставленную клавиатуру для игры с ботом.
                                
                                🕹Как играть
                                Во время игры вам будут предоставлены кнопки, соответствующие ячейкам. Нажимайте на кнопку ⬜, чтобы сделать ход.
                                
                                👥Мультиплеер
                                Если игра не будет завершена в течение 5 минут, она будет отменена, а сообщения заменены.
                                
                                📌О боте
                                Мы не сохраняем и не обрабатываем текстовые сообщения!
                                
                                Канал бота: @HowdyBots
                                """
                        ).build()
        );
    }

    public List<Translation> saveUA2LangMsgs() {
        return List.of(
                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.INFO)
                        .language(lang3)
                        .key(Constants.INFO_MENU_MSG)
                        .value("""
                ❗O'ynash
                Bot bilan o'ynash uchun faqat taqdim etilgan klaviaturadan foydalanishingiz mumkin.

                🕹Qanday o'ynash kerak
                O'yin davomida sizga katakchalarga mos keluvchi tugmalar beriladi. Harakat qilish uchun ⬜ tugmasini bosing.

                👥Ko'p o'yinchi
                Agar o'yin 5 daqiqa ichida tugamasa, u bekor qilinadi va xabarlar almashtiriladi.

                📌Bot haqida
                Biz matnli xabarlarni saqlamaymiz yoki ularga javob bermaymiz!

                Bot kanali: @HowdyBots
                """
                        ).build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.INFO)
                        .language(lang4)
                        .key(Constants.INFO_MENU_MSG)
                        .value("""
                ❗መጫወት
                ከቦት ጋር ለመጫወት የቀረበውን ቁልፍ ሰሌዳ ብቻ መጠቀም ይችላሉ።

                🕹እንዴት መጫወት እንደሚቻል
                በጨዋታው ወቅት፣ ከሴሎች ጋር የሚዛመዱ ቁልፎች ይሰጥዎታል። እርምጃ ለመፈጸም የ ⬜ ቁልፍን ይጫኑ።

                👥ባ� personallyብ ተጫዋች
                ጨዋታው በ5 ደቂቃዎች ውስጥ ካልተጠናቀቀ፣ ይሰረዛል፣ እና መልእክቶቹ ይተካሉ።

                📌ስለ ቦት
                የጽሑፍ መልእክቶችን አንቀበልም ወይም አንመልስም!

                የቦት ቻናል፡ @HowdyBots
                """
                        ).build(),

                /// Uzbek
                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang3)
                        .key(Constants.HOME_MSG)
                        .value("👤%s\nO'yinga xush kelibsiz! 🎮")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang3)
                        .key(Constants.PLAY_WITH_BOT_BTN)
                        .value("🤖Bot bilan o'ynash")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang3)
                        .key(Constants.PLAY_WITH_FRIEND_BTN)
                        .value("👫Do'st bilan o'ynash")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang3)
                        .key(Constants.LANGUAGE_BTN)
                        .value("🌐Til")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.LANGUAGE)
                        .language(lang3)
                        .key(Constants.LANGUAGE_MSG)
                        .value("🌍Tilni tanlang:")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang3)
                        .key(Constants.SUPPORT_BTN)
                        .value("📩Yordam")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang3)
                        .key(Constants.BOT_GAME_STATISTICS_MSG)
                        .value("""
                <b>Statistika:</b>
                <pre>%s</pre>""")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOT_GAME)
                        .language(lang3)
                        .key(Constants.PLAY)
                        .value("🤖O'ynash")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOT_GAME)
                        .language(lang3)
                        .key(Constants.DIFFICULTY_LEVEL_BTN)
                        .value("😺Qiyinlik darajasi💀")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang3)
                        .key(Constants.DIFFICULTY_LEVEL_MSG)
                        .value("🎚️Qiyinlik darajasini tanlang:")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang3)
                        .key(Constants.LEVEL_EASY)
                        .value("😺Oson")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang3)
                        .key(Constants.LEVEL_MEDIUM)
                        .value("🧠O'rta")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang3)
                        .key(Constants.LEVEL_HARD)
                        .value("😈Qiyin")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang3)
                        .key(Constants.LEVEL_EXTREME)
                        .value("💀Ekstremal")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.SIGN)
                        .language(lang3)
                        .key(Constants.SIGN_MENU_MSG)
                        .value("❓Belgingizni tanlang:")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang3)
                        .key(Constants.BOARD_MENU_MSG)
                        .value("""
                🚀O'yin boshlandi!
                Siz: %s
                Bot: %s
                """)
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang3)
                        .key(Constants.BOARD_MSG)
                        .value("Taxta:")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang3)
                        .key(Constants.DRAW_MSG)
                        .value("🤝Durrang!")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang3)
                        .key(Constants.YOU_WON_MSG)
                        .value("🎉Siz yutdingiz!")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang3)
                        .key(Constants.YOU_LOST_MSG)
                        .value("💔Siz yutqazdingiz!")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOT_GAME)
                        .language(lang3)
                        .key(Constants.BACK_BTN)
                        .value("🔙Orqaga")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.NONE)
                        .language(lang3)
                        .key(Constants.WARNING_MSG)
                        .value("⚠️Noto'g'ri harakat. Qayta urinib ko'ring!")
                        .build(),

                /// Amharic
                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang4)
                        .key(Constants.HOME_MSG)
                        .value("👤%s\nወደ ጨዋታው እንኳን በደህና መጡ! 🎮")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang4)
                        .key(Constants.PLAY_WITH_BOT_BTN)
                        .value("🤖ከቦት ጋር መጫወት")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang4)
                        .key(Constants.PLAY_WITH_FRIEND_BTN)
                        .value("👫ከጓደኛ ጋር መጫወት")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang4)
                        .key(Constants.LANGUAGE_BTN)
                        .value("🌐ቋንቋ")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.LANGUAGE)
                        .language(lang4)
                        .key(Constants.LANGUAGE_MSG)
                        .value("🌍ቋንቋ ይምረጡ፡")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang4)
                        .key(Constants.SUPPORT_BTN)
                        .value("📩ድጋፍ")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.HOME)
                        .language(lang4)
                        .key(Constants.BOT_GAME_STATISTICS_MSG)
                        .value("""
                <b>ስታቲስቲክስ፡</b>
                <pre>%s</pre>""")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOT_GAME)
                        .language(lang4)
                        .key(Constants.PLAY)
                        .value("🤖መጫወት")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOT_GAME)
                        .language(lang4)
                        .key(Constants.DIFFICULTY_LEVEL_BTN)
                        .value("😺የችግር ደረጃ💀")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang4)
                        .key(Constants.DIFFICULTY_LEVEL_MSG)
                        .value("🎚️የችግር ደረጃ ይምረጡ፡")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang4)
                        .key(Constants.LEVEL_EASY)
                        .value("😺ቀላል")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang4)
                        .key(Constants.LEVEL_MEDIUM)
                        .value("🧠መካከለኛ")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang4)
                        .key(Constants.LEVEL_HARD)
                        .value("😈ከባድ")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.DIFFICULTY)
                        .language(lang4)
                        .key(Constants.LEVEL_EXTREME)
                        .value("💀እጅግ ከባድ")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.SIGN)
                        .language(lang4)
                        .key(Constants.SIGN_MENU_MSG)
                        .value("❓የእርስዎን ምልክት ይምረጡ፡")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang4)
                        .key(Constants.BOARD_MENU_MSG)
                        .value("""
                🚀ጨዋታው ተጀምሯል!
                እርስዎ፡ %s
                ቦት፡ %s
                """)
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang4)
                        .key(Constants.BOARD_MSG)
                        .value("ቦርድ፡")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang4)
                        .key(Constants.DRAW_MSG)
                        .value("🤝እኩል ተጠናቋል!")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang4)
                        .key(Constants.YOU_WON_MSG)
                        .value("🎉እርስዎ አሸንፈዋል!")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOARD)
                        .language(lang4)
                        .key(Constants.YOU_LOST_MSG)
                        .value("💔እርስዎ ተሸንፈዋል!")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.BOT_GAME)
                        .language(lang4)
                        .key(Constants.BACK_BTN)
                        .value("🔙ወደ ኋላ")
                        .build(),

                Translation.builder()
                        ._active(true)
                        .menu(SessionMenu.NONE)
                        .language(lang4)
                        .key(Constants.WARNING_MSG)
                        .value("⚠️ተገቢ ያልሆነ እርምጃ። እንደገና ይሞክሩ!")
                        .build()
        );
    }
}
