package edu.matc.taglibdemo;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;

public class HelloWorldTag extends SimpleTagSupport  {
    int MORNING_END = 12;
    int HAPPY_HOUR = 17;
    int NIGHT_END = 4;


    int month = 0;
    int day = 0;
    int hour = 0;

    boolean isHalloween = false;

    private String hourMessage;
    private String dayMessage;
    StringWriter sw = new StringWriter();

    @Override
    public void doTag() throws JspException, IOException {
        super.doTag();

        getDate();
        checkIfHalloween();
        generateDayMessage();
        generateHourMessage();

            if (dayMessage != null) {
                JspWriter out = getJspContext().getOut();
                out.println(dayMessage);
                out.println("Where you are, it's " + getDate() + ", which means  . . . ");
                out.println(hourMessage);
            } else {
                getJspBody().invoke(sw);
                getJspContext().getOut().println(sw.toString());
            }
    }



    private Date getDate() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);

        return date;
    }

    private void checkIfHalloween() {
        if (month == 9 && day == 31) {
            isHalloween = true;
        }
    }

    private void generateDayMessage() {


        if (isHalloween) {
            dayMessage = "Trick or Treat! It's Halloween!";
        } else {
            dayMessage = "I regret to inform you that it's not Halloween.";
        }
    }

    private void generateHourMessage() {
        if (hour < NIGHT_END) {
            hourMessage = "you should be sleeping!";
        } else if (hour >= NIGHT_END && hour < MORNING_END){
            hourMessage = "have a good morning!";
        } else if (hour >= MORNING_END && hour < HAPPY_HOUR) {
            hourMessage = "have a good afternoon.";
        } else if (hour == HAPPY_HOUR) {
            hourMessage = "it's 5 o'clock somewhere.";
        } else if (hour > HAPPY_HOUR){
            hourMessage = "have a good night!";
        }
    }
}
