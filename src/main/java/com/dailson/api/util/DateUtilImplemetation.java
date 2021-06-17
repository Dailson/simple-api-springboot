package com.dailson.api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateUtilImplemetation implements DateUtils {

	@Override
	public String formatLocalDateTimeDatabaseStyle(LocalDateTime localDateTime) {
		return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(localDateTime);

	}
}
