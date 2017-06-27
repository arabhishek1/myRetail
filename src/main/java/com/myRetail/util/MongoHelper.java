package com.myRetail.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.UUID;

/**
 * Created by abhishek.ar on 24/06/17.
 */
public class MongoHelper {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("ddMMyyyyHHmmss").withZone(DateTimeZone.forOffsetHoursMinutes(5, 30));

    public String getNewHashKey(String pivot) {
        return String.valueOf(dateTimeFormatter.print(DateTime.now()) + "-" + UUID.randomUUID().toString().substring(0, 5) + "-" + pivot);
    }
}
