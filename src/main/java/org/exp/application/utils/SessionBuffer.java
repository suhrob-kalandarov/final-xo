package org.exp.application.utils;

import org.exp.application.models.entity.BotGame;
import org.exp.application.models.entity.MultiGame;
import org.exp.application.models.entity.TgUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionBuffer {

    public static Map<Long, TgUser> sessionTgUsers = new HashMap<>();

    public static Map<Long, BotGame> sessionBotGames = new HashMap<>();

    public static Map<Long, MultiGame> sessionMultiGames = new HashMap<>();


}
