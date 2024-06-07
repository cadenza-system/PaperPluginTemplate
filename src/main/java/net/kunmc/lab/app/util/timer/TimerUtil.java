package net.kunmc.lab.app.util.timer;

import java.util.Objects;

class TimerUtil {

    static String limitText(String displayName, double currentTime) {
        StringBuilder text = new StringBuilder();
        if (Objects.nonNull(displayName)) {
            text.append(displayName);
        }
        text.append(" 残り ");

        int sec = (int) Math.floor(currentTime);

        int hour = sec / 3600;
        int min = (sec % 3600) / 60;
        sec = sec % 60;

        if (hour > 0) {
            text.append(hour)
                    .append("時間");
        }

        if (min > 0) {
            text.append(min)
                    .append("分");
        }
        text.append(sec)
                .append("秒");

        return text.toString();
    }

    static double progressRate(double currentTime, double limit) {
        double rate = currentTime / limit;
        if (rate < 0) {
            rate = 0;
        }
        return rate;
    }
}
