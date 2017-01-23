import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {
    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (hour >= 15) {
            if (day == 7) {
                return 1;
            } else {
                return day + 1;
            }
        } else {
            return day;
        }
    }

    public static Calendar getDayCalender() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (hour >= 15) {
            calendar.add(Calendar.DATE, 1);
        }
        return calendar;

    }

    public static String getDayName(int day) {
        String dayName = "";
        switch (day) {
            case 7:
                dayName = "Saturday";
                break;
            case 1:
                dayName = "Sunday";
                break;
            case 2:
                dayName = "Monday";
                break;
            case 3:
                dayName = "Tuesday";
                break;
            case 4:
                dayName = "Wednesday";
                break;
        }
        return dayName;
    }

    public static int getDayNum(String day) {
        int dayNum = 0;
        switch (day) {
            case Constant.SATURDAY:
                dayNum = 7;
                break;
            case Constant.SUNDAY:
                dayNum = 1;
                break;
            case Constant.MONDAY:
                dayNum = 2;
                break;
            case Constant.TUESDAY:
                dayNum = 3;
                break;
            case Constant.WEDNESDAY:
                dayNum = 4;
                break;
        }
        return dayNum;
    }

    public static String dayList(int day) {
        String dayName = "";
        switch (day) {
            case 0:
                dayName = Constant.SATURDAY;
                break;
            case 1:
                dayName = Constant.SUNDAY;
                break;
            case 2:
                dayName = Constant.MONDAY;
                break;
            case 3:
                dayName = Constant.TUESDAY;
                break;
            case 4:
                dayName = Constant.WEDNESDAY;
                break;
        }
        return dayName;
    }

    public static String addDay(int dayNum) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar calendar = getDayCalender();
        int now = getDay();
        if (dayNum == now) {
            return sdf.format(calendar.getTime());
        } else {
            if (now == 7) {
                calendar.add(Calendar.DATE, dayNum);
            } else {
                calendar.add(Calendar.DATE, dayNum - now);
            }
        }
        return sdf.format(calendar.getTime());
    }

    public static InlineKeyboardMarkup weeklySchedule(int today) {
        InlineKeyboardMarkup inlineKeyboardMarkup = null;
        //TODO:
        switch (today) {
            case 7:
                InlineKeyboardButton[][] buttons = new InlineKeyboardButton[6][1];
                buttons[0][0] = new InlineKeyboardButton(Constant.ORDER_RECIPE_OF_THE_DAY).callbackData(Constant.ORDER_RECIPE_OF_THE_DAY);
                buttons[1][0] = new InlineKeyboardButton(Constant.SUNDAY).callbackData(Constant.SUNDAY);
                buttons[2][0] = new InlineKeyboardButton(Constant.MONDAY).callbackData(Constant.MONDAY);
                buttons[3][0] = new InlineKeyboardButton(Constant.TUESDAY).callbackData(Constant.TUESDAY);
                buttons[4][0] = new InlineKeyboardButton(Constant.WEDNESDAY).callbackData(Constant.WEDNESDAY);
                buttons[5][0] = new InlineKeyboardButton(Constant.DISSUASION).callbackData(Constant.DISSUASION);
                inlineKeyboardMarkup = new InlineKeyboardMarkup(buttons);
                break;
            case 1:
                InlineKeyboardButton[][] buttons1 = new InlineKeyboardButton[5][1];
                buttons1[0][0] = new InlineKeyboardButton(Constant.ORDER_RECIPE_OF_THE_DAY).callbackData(Constant.ORDER_RECIPE_OF_THE_DAY);
                buttons1[1][0] = new InlineKeyboardButton(Constant.MONDAY).callbackData(Constant.MONDAY);
                buttons1[2][0] = new InlineKeyboardButton(Constant.TUESDAY).callbackData(Constant.TUESDAY);
                buttons1[3][0] = new InlineKeyboardButton(Constant.WEDNESDAY).callbackData(Constant.WEDNESDAY);
                buttons1[4][0] = new InlineKeyboardButton(Constant.DISSUASION).callbackData(Constant.DISSUASION);
                inlineKeyboardMarkup = new InlineKeyboardMarkup(buttons1);
                break;
            case 2:
                InlineKeyboardButton[][] buttons2 = new InlineKeyboardButton[4][1];
                buttons2[0][0] = new InlineKeyboardButton(Constant.ORDER_RECIPE_OF_THE_DAY).callbackData(Constant.ORDER_RECIPE_OF_THE_DAY);
                buttons2[1][0] = new InlineKeyboardButton(Constant.TUESDAY).callbackData(Constant.TUESDAY);
                buttons2[2][0] = new InlineKeyboardButton(Constant.WEDNESDAY).callbackData(Constant.WEDNESDAY);
                buttons2[3][0] = new InlineKeyboardButton(Constant.DISSUASION).callbackData(Constant.DISSUASION);
                inlineKeyboardMarkup = new InlineKeyboardMarkup(buttons2);
                break;
            case 3:
                InlineKeyboardButton[][] buttons3 = new InlineKeyboardButton[3][1];
                buttons3[0][0] = new InlineKeyboardButton(Constant.ORDER_RECIPE_OF_THE_DAY).callbackData(Constant.ORDER_RECIPE_OF_THE_DAY);
                buttons3[1][0] = new InlineKeyboardButton(Constant.WEDNESDAY).callbackData(Constant.WEDNESDAY);
                buttons3[2][0] = new InlineKeyboardButton(Constant.DISSUASION).callbackData(Constant.DISSUASION);
                inlineKeyboardMarkup = new InlineKeyboardMarkup(buttons3);
                break;
            case 4:
                InlineKeyboardButton[][] buttons4 = new InlineKeyboardButton[2][1];
                buttons4[0][0] = new InlineKeyboardButton(Constant.ORDER_RECIPE_OF_THE_DAY).callbackData(Constant.ORDER_RECIPE_OF_THE_DAY);
                buttons4[1][0] = new InlineKeyboardButton(Constant.DISSUASION).callbackData(Constant.DISSUASION);
                inlineKeyboardMarkup = new InlineKeyboardMarkup(buttons4);
                break;
        }

        return inlineKeyboardMarkup;
    }

//    public static void checkContact(org.telegram.telegrambots.api.objects.Message message) {
//        if (Main.contacts != null) {
//            if (!Main.contacts.contains(String.valueOf(message.getChat().getId()))) {
//                Main.contacts.add(String.valueOf(message.getChat().getId()));
//                FilesService.writeObj(Main.contacts, "/var/www/www.yumbox.ir/YumBoxBotUsers.txt");
//
//            }
//        }
//    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        return strDate;
    }

}
