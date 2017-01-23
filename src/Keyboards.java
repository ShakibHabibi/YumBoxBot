import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

public class Keyboards {
    public static InlineKeyboardMarkup replyKeyboardMarkupInline;
    public static com.pengrad.telegrambot.model.request.Keyboard replyKeyboardMarkup;
    public static InlineKeyboardMarkup replyKeyboardMarkupPlusStartInline;
    public static com.pengrad.telegrambot.model.request.Keyboard replyKeyboardMarkupPlusStart;
    public static InlineKeyboardMarkup replyKeyboardMarkupDissuasionInline;
    public static com.pengrad.telegrambot.model.request.Keyboard replyKeyboardMarkupDissuasion;
    public static com.pengrad.telegrambot.model.request.Keyboard replyKeyboardMarkupAdmin;
    public static void initialize(){
        replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{Constant.WEEKLY_MENU, Constant.RECIPE_OF_THE_DAY})
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);
        InlineKeyboardButton[][] button = new InlineKeyboardButton[5][1];
        button[0][0] = new InlineKeyboardButton(Constant.RECIPE_OF_THE_DAY).callbackData(Constant.RECIPE_OF_THE_DAY);
        button[1][0] = new InlineKeyboardButton(Constant.WEEKLY_MENU).callbackData(Constant.WEEKLY_MENU);
        button[2][0] = new InlineKeyboardButton(Constant.CONTACT_US).url(Constant.TELEGRAM_URL);
        button[3][0] = new InlineKeyboardButton(Constant.INSTAGRAM).url(Constant.INSTAGRAM_URL);
        button[4][0] = new InlineKeyboardButton(Constant.TELEGRAM).url(Constant.TELEGRAM_CHANNEL_URL);
        replyKeyboardMarkupInline = new InlineKeyboardMarkup(button);


        InlineKeyboardButton[][] plusStartInline = new InlineKeyboardButton[4][1];
        plusStartInline[0][0] = new InlineKeyboardButton(Constant.RECIPE_OF_THE_DAY).callbackData(Constant.RECIPE_OF_THE_DAY);
        plusStartInline[1][0] = new InlineKeyboardButton(Constant.WEEKLY_MENU).callbackData(Constant.WEEKLY_MENU);
        plusStartInline[2][0] = new InlineKeyboardButton(Constant.YUMBOX_INFORMATION).callbackData(Constant.YUMBOX_INFORMATION);
        plusStartInline[3][0] = new InlineKeyboardButton(Constant.CONTACT_US).url(Constant.TELEGRAM_URL);
        replyKeyboardMarkupPlusStartInline = new InlineKeyboardMarkup(plusStartInline);


        InlineKeyboardButton[][] dissuasionInline = new InlineKeyboardButton[2][1];
        dissuasionInline[0][0] = new InlineKeyboardButton(Constant.WEEKLY_MENU).callbackData(Constant.WEEKLY_MENU);
        dissuasionInline[1][0] = new InlineKeyboardButton(Constant.DISSUASION).callbackData(Constant.DISSUASION);
        replyKeyboardMarkupDissuasionInline = new InlineKeyboardMarkup(dissuasionInline);


        replyKeyboardMarkupPlusStart = new ReplyKeyboardMarkup(
                new String[]{Constant.YUMBOX_INFORMATION},
                new String[]{Constant.WEEKLY_MENU, Constant.RECIPE_OF_THE_DAY})
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);
        replyKeyboardMarkupAdmin = new ReplyKeyboardMarkup(
                new String[]{Constant.START})
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);
        replyKeyboardMarkupDissuasion = new ReplyKeyboardMarkup(
                new String[]{Constant.WEEKLY_MENU, Constant.DISSUASION})
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);
    }
}
