import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;


public class Main extends TimerTask {
    public static TelegramBot bot;
    public static int photoDay;
    public static HashMap<Long, Boolean> userPlaceInBot;
    public static HashMap<Long, String> userSelectedDay;
    public static HashMap<Long, Integer> selectedDay;
    public static ArrayList<String> contacts;

    public static void main(String[] args) {
        DBHelper.initialize();
        Keyboards.initialize();
        //TODO
//        saveFileInServer();
        contacts = new ArrayList<>();
        //TODO
//        Util.addCustomersToFile();
        userPlaceInBot = new HashMap<Long, Boolean>();
        userSelectedDay = new HashMap<>();
        selectedDay = new HashMap<>();
        bot = TelegramBotAdapter.build(BotKey.BOT_KEY_TEST);
        Main task = new Main();
        Timer timer = new Timer();
        timer.schedule(task, startTime(), 1000 * 60 * 60 * 24);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new ChannelHandlers());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_WEEK) <= 4 || calendar.get(Calendar.DAY_OF_WEEK) == 7 && contacts.size() != 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    contacts = DBHelper.getAllUsers();
                    if (contacts != null && contacts.size() > 0) {
                        File file = new File(Constant.BASE_URL + String.valueOf(Util.getDay()) + Constant.IMAGE_FORMAT);
                        for (int i = 0; i < contacts.size(); i++) {
                            Main.userPlaceInBot.put(Long.valueOf(contacts.get(i)), true);
                            bot.execute(new SendPhoto(contacts.get(i), file));
                            Main.bot.execute(new SendMessage(contacts.get(i), Constant.ORDER_SUBMIT)
                                    .replyMarkup(Keyboards.replyKeyboardMarkupDissuasionInline));
                        }
                    }
                }
            }).start();
        }
    }

    private static Date startTime() {

        Date date = new Date();
        date.setHours(12);
        date.setMinutes(00);

        return date;
    }
}

