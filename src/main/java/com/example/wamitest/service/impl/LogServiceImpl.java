package com.example.wamitest.service.impl;

import com.example.wamitest.dto.LogDto;
import com.example.wamitest.model.Logs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.example.wamitest.constants.LoggingConstants.LOG_DEBUG_EMPTY_PATTERN;


@Service
public class LogServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(LogServiceImpl.class);
    private final String pattern = "yy-MM-dd HH:mm:ss";
    public List<LogDto> findStartLogs(){
        LOG.debug(LOG_DEBUG_EMPTY_PATTERN.getMessage());
        final String fileName = "/home/vladbagach/file/start.log";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))){

            List<Logs> logs = stream
                    .map(t -> {
                                try {
                                    return new Logs(t.substring(4,16), parseLongToDate(Long.valueOf(t.substring(16, 28))));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    ).collect(Collectors.toList());
            Map<String , Date> map = logs.stream().collect(Collectors.toMap(Logs::getId, Logs::getTime , (k , k1) -> k1));

            return map.entrySet().stream().map(t -> new LogDto(t.getKey() , t.getValue())).collect(Collectors.toList());
        } catch (IOException e) {
            throw new AssertionError();
        }
    }

    public List<LogDto> findFinishLogs(){
        LOG.debug(LOG_DEBUG_EMPTY_PATTERN.getMessage());
        final String fileName = "/home/vladbagach/file/finish.log";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))){
            List<Logs> logs = stream
                    .map(t -> {
                                try {
                                    return new Logs(t.substring(4,16), (parseLongToDate(Long.valueOf(t.substring(16, 28)))));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    ).collect(Collectors.toList());
            Map<String , Date> map = logs.stream().collect(Collectors.toMap(Logs::getId, Logs::getTime , (k , k1) -> k1));

            return map.entrySet().stream().map(t -> new LogDto(t.getKey() , t.getValue())).collect(Collectors.toList());
        } catch (IOException e) {
            throw new AssertionError();
        }
    }

    public List<LogDto> usersWithTheLeastTimeSpent(){
        LOG.debug(LOG_DEBUG_EMPTY_PATTERN.getMessage());
        return IntStream.range(0 , findStartLogs().size()).mapToObj(s -> findFinishLogs().get(s).getId())
                .map(s -> {
                    try {
                        return new LogDto(s , parseLongToDate(diffTime().get(Integer.parseInt(s)).getTime()));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }).sorted(Comparator.comparing(LogDto::getTime)).limit(10).collect(Collectors.toList());
    }

    public List<String> commonIdOfTwoFiles(){
        LOG.debug(LOG_DEBUG_EMPTY_PATTERN.getMessage());
        List<String> a = new ArrayList<>();
        a = (findStartLogs().stream().map(LogDto::getId).collect(Collectors.toList())).stream()
                .filter((findFinishLogs().stream()
                        .map(LogDto::getId).collect(Collectors.toList()))::contains).peek(a::add).collect(Collectors.toList());
        return a;
    }


    private List<Date> diffTime() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern , Locale.US);
        List<Date>longs = new ArrayList<>();

        for(int i = 0 ; i < findFinishLogs().size(); i++){

            String stringStart = simpleDateFormat.format(findFinishLogs().get(i).getTime());
            String stringFinish = simpleDateFormat.format(newListEqualInSizeToFinishLog().get(i).getTime());
            LocalDate localDateS = LocalDate.parse(stringStart, DateTimeFormatter.ofPattern(pattern));
            LocalDate localDateF = LocalDate.parse(stringFinish , DateTimeFormatter.ofPattern(pattern));
            Period period = Period.between(localDateS , localDateF);
            long years = Math.abs(period.getYears());
            long months = Math.abs(period.getMonths());
            long days = Math.abs(period.getDays());

            long[]a = {years , months , days};

            String ss = Arrays.toString(a).replaceAll("\\D" , "");

            longs.add(parseStringToDate(ss));
        }
        return longs;
    }

    public List<LogDto> newListEqualInSizeToFinishLog(){

        return IntStream.range(0 , findFinishLogs().size()).filter(x -> !findFinishLogs().equals(findStartLogs()))
                   .boxed().map(i -> findStartLogs().remove((int)i)).collect(Collectors.toList());
    }


    private Date parseLongToDate(Long d) throws ParseException {
        return new Date(d * 1000L);
    }

    private Date parseStringToDate(String str) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern , Locale.US);
        return format.parse(str);
    }

}
