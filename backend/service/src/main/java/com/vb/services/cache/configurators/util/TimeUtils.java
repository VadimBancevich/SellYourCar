package com.vb.services.cache.configurators.util;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TimeUtils {

    public static Duration determineDuration(String time) {
        Pattern pattern = Pattern.compile("(\\d+)(\\w{1,2})");
        Matcher matcher = pattern.matcher(time);
        if (matcher.find()) {
            long duration = Long.parseLong(matcher.group(1));
            ChronoUnit timeUnit = determineChronoUnit(matcher.group(2));
            return Duration.of(duration, timeUnit);
        }
        throw new IllegalArgumentException("time must be presented like 32231ms or 2314s or 10m or 23h 3d");
    }

    public static ChronoUnit determineChronoUnit(String timeUnit) {
        int ordinal = determinUnitIndex(timeUnit);

        return ChronoUnit.values()[timeUnit.equals("d") ? ordinal - 1 : ordinal];

    }

    private static int determinUnitIndex(String timeUnit) {
        if ("m".equals(timeUnit)) {
            return TimeUnit.MINUTES.ordinal();
        } else if ("s".equals(timeUnit)) {
            return TimeUnit.SECONDS.ordinal();
        } else if ("h".equals(timeUnit)) {
            return TimeUnit.HOURS.ordinal();
        } else if ("d".equals(timeUnit)) {
            return TimeUnit.DAYS.ordinal();
        } else if ("ms".equals(timeUnit)) {
            return TimeUnit.MILLISECONDS.ordinal();
        }
        throw new IllegalArgumentException("Available time unit arguments: ms, s, m, h, d");

    }

    public static TimeUnit determineTimeUnite(String timeUnit) {
        return TimeUnit.values()[determinUnitIndex(timeUnit)];
    }


    private TimeUtils() {
    }


}
