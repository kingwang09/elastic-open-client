package io.legendcup.elasticopenclient.support.util;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class TextUtil {
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    public String convertDueDate(int isStandingBy, String input) {
        String result;
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            Date scheduled = inputFormat.parse(input);

            if (isStandingBy == 1) {
                SimpleDateFormat sdf = new SimpleDateFormat("M/d(E) H시mm분", Locale.KOREA);
                return sdf.format(scheduled);
            } else {
                int scheduledDay = Integer.valueOf(new SimpleDateFormat("d").format(scheduled));
                String resultDueDate = (scheduledDay > 11) ? ((scheduledDay > 21) ? "말" : "중") : "초";
                String scheduledMonth = new SimpleDateFormat("M").format(scheduled);
                result = scheduledMonth.concat("월 ").concat(resultDueDate);
            }
        } catch (Exception e) {
            result = input;
        }

        return result;
    }

    public String makeSearchTextLike(String value) {
        if (value == null) {
            return null;
        }
        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z]";
        String confirm = value.replaceAll(match, "");
        return confirm.toLowerCase();
    }

    public String concat(String... args ) {
        return Arrays.stream(args)
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .distinct().collect(Collectors.joining());
    }

    public String join(String... args) {
        return Arrays.stream(args)
                .filter(StringUtils::isNotBlank)
                .distinct().collect(Collectors.joining(" "));
    }
}
