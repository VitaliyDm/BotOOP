package BotInterfaces.telegramBot;

import constants.Constants;
import questions.QuestionsGenerator;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class server {
    public static Map<Long, telegramBotUserSession> users = new HashMap<>();
    private static ArrayDeque<Long> not_add_users = new ArrayDeque<>();

    server() throws IOException {
        Executor service = Executors.newCachedThreadPool();

        while(true)
        {
            if (!not_add_users.isEmpty())
                users.put(not_add_users.pop(), new telegramBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS)));
                service.execute(new UserThread());
        }
    }

    void add_user(Long chatId) throws IOException {
        not_add_users.add(chatId);
    }
}