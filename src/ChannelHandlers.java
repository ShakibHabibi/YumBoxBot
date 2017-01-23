import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.apache.http.util.TextUtils;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.File;
import java.util.regex.Pattern;

public class ChannelHandlers extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        org.telegram.telegrambots.api.objects.Message message = update.getMessage();
        if (message != null) {
//            Util.checkContact(message);
            messageHandler(message);
        }
        if (update.getCallbackQuery() != null) {
            DBHelper.checkContactSql(update);
            CallBack(update);
        }
    }

    @Override
    public String getBotUsername() {
        //TODO
        return BotKey.BOT_NAME_TEST;
    }

    @Override
    public String getBotToken() {
        //TODO
        return BotKey.BOT_KEY_TEST;
    }

    private static void messageHandler(org.telegram.telegrambots.api.objects.Message message) {

        String patternText = "09(0[0-3]|1[0-9]|2[0-9]|3[0-9])-?[0-9]{3}-?[0-9]{4}";
        Pattern pattern = Pattern.compile(patternText);
        final String userInput = message.getText();
        Long chatId = message.getChatId();
        if (userInput != null
                && message.getChat().getId() != BotKey.ADMIN_ID) {
            if (userInput.equals(Constant.START) || userInput.equals(Constant.START_AGAIN) || userInput.equals(Constant.DISSUASION) || userInput.equals(Constant.YUMBOX_INFORMATION)) {
                Main.userPlaceInBot.put(chatId, false);
                Main.bot.execute(new SendMessage(chatId
                        , Constant.WELCOME_TEXT)
                        .replyMarkup(Keyboards.replyKeyboardMarkupInline));

            } else if (userInput.equals(Constant.WEEKLY_MENU)) {
                if (Util.getDay() <= 4 || Util.getDay() == 7) {
                    Main.userPlaceInBot.put(chatId, false);
                    Main.bot.execute(new SendMessage(chatId
                            , Constant.PHOTO_PRICE)
                            .replyMarkup(Util.weeklySchedule(Util.getDay())));
                } else {
                    Main.userPlaceInBot.put(chatId, false);
                    Main.bot.execute(new SendMessage(chatId, Constant.AVALIBLE_DAYS)
                            .replyMarkup(Keyboards.replyKeyboardMarkupPlusStartInline));
                }


            } else if (userInput.equals(Constant.RECIPE_OF_THE_DAY) || userInput.equals(Constant.ORDER_RECIPE_OF_THE_DAY) || userInput.equals(Constant.MENU)) {
                if (Util.getDay() <= 4 || Util.getDay() == 7) {
                    Main.userPlaceInBot.put(chatId, true);
                    Main.userSelectedDay.put(chatId, "");
                    Main.selectedDay.put(chatId, 0);
                    File file = new File(Constant.BASE_URL + String.valueOf(Util.getDay()) + Constant.IMAGE_FORMAT);
                    Main.bot.execute(new SendPhoto(chatId, file));
                    Main.bot.execute(new SendMessage(chatId, Constant.ORDER_SUBMIT)
                            .replyMarkup(Keyboards.replyKeyboardMarkupDissuasionInline));
                } else {
                    Main.userPlaceInBot.put(chatId, false);
                    Main.bot.execute(new SendMessage(chatId, Constant.AVALIBLE_DAYS)
                            .replyMarkup(Keyboards.replyKeyboardMarkupPlusStartInline));
                }
            } else if (userInput.equals(Constant.WEDNESDAY) || userInput.equals(Constant.TUESDAY) || userInput.equals(Constant.MONDAY) || userInput.equals(Constant.SUNDAY)) {
                Main.userPlaceInBot.put(chatId, true);
                Main.userSelectedDay.put(chatId, userInput);
                int day = Util.getDayNum(userInput);
                File file = new File(Constant.BASE_URL + String.valueOf(day) + Constant.IMAGE_FORMAT);
                Main.bot.execute(new SendPhoto(chatId, file));
                Main.bot.execute(new SendMessage(chatId, Constant.ORDER_SUBMIT)
                        .replyMarkup(Keyboards.replyKeyboardMarkupDissuasionInline));

            } else if (Main.userPlaceInBot.get(chatId) != null && Main.userPlaceInBot.get(chatId)) {
                if (pattern.matcher(userInput).find()) {
                    String orderDay;
                    if (Util.getDay() == 7 && TextUtils.isEmpty(Main.userSelectedDay.get(chatId))) {
                        orderDay = Constant.SATURDAY;
                    } else {
                        orderDay = TextUtils.isEmpty(Main.userSelectedDay.get(chatId)) ? Util.dayList(Util.getDay()) : Main.userSelectedDay.get(chatId);
                    }
                    if (message.getChat().getUserName() != null) {
                        //TODO
                        String userName = message.getChat().getUserName();
                        String userInfo = String.format("Phone number : %s \n Telegram username: @ %s \n %s", message.getText(), userName, orderDay);
                        Main.bot.execute(new SendMessage(BotKey.OWNER_ID_TEST, userInfo));
                    } else {
                        //TODO
                        String userInfo = String.format("Phone number : %s \n %s", message.getText(), orderDay);
                        Main.bot.execute(new SendMessage(BotKey.OWNER_ID_TEST, userInfo));
                    }
                    DBHelper.addSaleSql(Util.getDayNum(orderDay), userInput, chatId);
                    Main.bot.execute(new SendMessage(chatId, Constant.ORDER_RESPONSE)
                            .replyMarkup(Keyboards.replyKeyboardMarkupPlusStartInline));
                    Main.userSelectedDay.put(chatId, "");
                    Main.userPlaceInBot.put(chatId, false);
                } else {
                    Main.bot.execute(new SendMessage(chatId, Constant.VALID_NUM)
                            .replyMarkup(Keyboards.replyKeyboardMarkupDissuasionInline));
                }
            } else {
                Main.userPlaceInBot.put(chatId, false);
                Main.bot.execute(new SendMessage(chatId, Constant.CHOOSE_MENU_ITEM));
            }
        } else {

            if (message.getChat().getId() == BotKey.ADMIN_ID) {

                if (userInput == null) {
                    GetFile request = new GetFile(message.getDocument().getFileId());
                    GetFileResponse getFileResponse = Main.bot.execute(request);
                    com.pengrad.telegrambot.model.File newPhoto = getFileResponse.file();
                    ImageHandler.deleteImage();
                    ImageHandler.saveImage(Main.bot.getFullFilePath(newPhoto));
                    Main.bot.execute(new SendMessage(chatId, Constant.THANK_YOU)
                            .replyMarkup(Keyboards.replyKeyboardMarkupAdmin));
                } else {
                    if (userInput.equals(Constant.START)) {
                        Main.bot.execute(new SendMessage(chatId, Constant.CHOOSE_DAY));
                    }
                    if (userInput.equals("7") || userInput.equals("1") || userInput.equals("2") || userInput.equals("3") || userInput.equals("4")) {
                        Main.photoDay = Integer.parseInt(userInput.trim());
                        Main.bot.execute(new SendMessage(chatId, Constant.UPLOAD_PHOTO));

                    }
                }
            }
        }
    }

    private void CallBack(Update update) {
        if (update.getCallbackQuery() != null) {
            String patternText = "09(0[0-3]|1[0-9]|2[0-9]|3[0-9])-?[0-9]{3}-?[0-9]{4}";
            Pattern pattern = Pattern.compile(patternText);
            final String userInput = update.getCallbackQuery().getData();
            if (userInput != null) {
                final Long chatId = update.getCallbackQuery().getMessage().getChatId();
                if (userInput.equals(Constant.START) || userInput.equals(Constant.START_AGAIN) || userInput.equals(Constant.DISSUASION) || userInput.equals(Constant.YUMBOX_INFORMATION)) {
                    Main.userPlaceInBot.put(chatId, false);
                    Main.bot.execute(new SendMessage(chatId, Constant.WELCOME_TEXT)
                            .replyMarkup(Keyboards.replyKeyboardMarkupInline));

                } else if (userInput.equals(Constant.WEEKLY_MENU)) {
                    if (Util.getDay() <= 4 || Util.getDay() == 7) {
                        Main.userPlaceInBot.put(chatId, false);
                        Main.bot.execute(new SendMessage(chatId
                                , Constant.PHOTO_PRICE)
                                .replyMarkup(Util.weeklySchedule(Util.getDay())));
                    } else {
                        Main.userPlaceInBot.put(chatId, false);
                        Main.bot.execute(new SendMessage(chatId, Constant.AVALIBLE_DAYS)
                                .replyMarkup(Keyboards.replyKeyboardMarkupPlusStartInline));
                    }


                } else if (userInput.equals(Constant.RECIPE_OF_THE_DAY) || userInput.equals(Constant.ORDER_RECIPE_OF_THE_DAY) || userInput.equals(Constant.MENU)) {
                    if (Util.getDay() <= 4 || Util.getDay() == 7) {
                        Main.userPlaceInBot.put(chatId, true);
                        Main.userSelectedDay.put(chatId, "");
                        Main.selectedDay.put(chatId, 0);
                        File file = new File(Constant.BASE_URL + String.valueOf(Util.getDay()) + Constant.IMAGE_FORMAT);
                        Main.bot.execute(new SendPhoto(chatId, file));
                        Main.bot.execute(new SendMessage(chatId, Constant.ORDER_SUBMIT)
                                .replyMarkup(Keyboards.replyKeyboardMarkupDissuasionInline));
                    } else {
                        Main.userPlaceInBot.put(chatId, false);
                        Main.bot.execute(new SendMessage(chatId, Constant.AVALIBLE_DAYS)
                                .replyMarkup(Keyboards.replyKeyboardMarkupPlusStartInline));
                    }
                } else if (userInput.equals(Constant.WEDNESDAY) || userInput.equals(Constant.TUESDAY) || userInput.equals(Constant.MONDAY) || userInput.equals(Constant.SUNDAY)) {
                    Main.userPlaceInBot.put(chatId, true);
                    Main.userSelectedDay.put(chatId, userInput);
                    int day = Util.getDayNum(userInput);
                    File file = new File(Constant.BASE_URL + String.valueOf(day) + Constant.IMAGE_FORMAT);
                    Main.bot.execute(new SendPhoto(chatId, file));
                    Main.bot.execute(new SendMessage(chatId, Constant.ORDER_SUBMIT)
                            .replyMarkup(Keyboards.replyKeyboardMarkupDissuasionInline));

                } else if (Main.userPlaceInBot.get(chatId) != null && Main.userPlaceInBot.get(chatId)) {
                    if (pattern.matcher(userInput).find()) {
                        String orderDay;
                        if (Util.getDay() == 7 && TextUtils.isEmpty(Main.userSelectedDay.get(chatId))) {
                            orderDay = Constant.SATURDAY;
                        } else {
                            orderDay = TextUtils.isEmpty(Main.userSelectedDay.get(chatId)) ? Util.dayList(Util.getDay()) : Main.userSelectedDay.get(chatId);
                        }
                        if (update.getCallbackQuery().getFrom().getUserName() != null) {
                            //TODO
                            String userName = update.getCallbackQuery().getFrom().getUserName();
                            String userInfo = String.format("Phone number : %s \n Telegram username: @ %s \n %s", userInput, userName, orderDay);
                            Main.bot.execute(new SendMessage(BotKey.OWNER_ID_TEST, userInfo));
                        } else {
                            //TODO
                            String userInfo = String.format("Phone number : %s \n %s", userInput, orderDay);
                            Main.bot.execute(new SendMessage(BotKey.OWNER_ID_TEST, userInfo));
                        }
                        DBHelper.addSaleSql(Util.getDayNum(orderDay), userInput, chatId);
                        Main.bot.execute(new SendMessage(chatId, Constant.ORDER_RESPONSE)
                                .replyMarkup(Keyboards.replyKeyboardMarkupPlusStartInline));
                        Main.userSelectedDay.put(chatId, "");
                        Main.userPlaceInBot.put(chatId, false);
                    } else {
                        Main.bot.execute(new SendMessage(chatId, Constant.VALID_NUM)
                                .replyMarkup(Keyboards.replyKeyboardMarkupDissuasionInline));
                    }
                } else {
                    Main.userPlaceInBot.put(chatId, false);
                    Main.bot.execute(new SendMessage(chatId, Constant.CHOOSE_MENU_ITEM));
                }
            }
        }
    }
}

