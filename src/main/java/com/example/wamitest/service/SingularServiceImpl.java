package com.example.wamitest.service;
import com.example.wamitest.constants.NameFileConstants;
import com.example.wamitest.exception.LogicalError;
import com.example.wamitest.model.Singular;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.example.wamitest.constants.LoggingConstants.LOG_DEBUG_EMPTY_PATTERN;


@Service
public class SingularServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(SingularServiceImpl.class);
    private static final String startZoneId = "UTC";
    private static final String finishZoneId = "UTC+3" ;


    public List<Singular> getUsersWithTheLeastTime(){
        LOG.debug(LOG_DEBUG_EMPTY_PATTERN.getMessage());
        return IntStream.range( 0 , findFinishLogs().size()).
                mapToObj(s -> new Singular(idFinishFile().get(s) , diffDate(timeStartFile().get(s) , timeFinishFile().get(s))))
                .sorted(Comparator.comparing(Singular::getTime))
                .limit(10)
                .collect(Collectors.toList());
    }
    public Set<Singular> findStartLogs1(){
        LOG.debug(LOG_DEBUG_EMPTY_PATTERN.getMessage());
        final String fileName = "/home/vladbagach/file/start.log";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))){
            Set<Singular> logs = new LinkedHashSet<>();
            Set<Singular> finalLogs = logs;
            logs = stream
                     .map(t -> new Singular(t.substring(4,16), (parseStringToZonedDataType((Long.parseLong(t.substring(16, 28))), NameFileConstants.FINISH))))
                     .filter(finalLogs::add)
                     .collect(Collectors.toCollection(LinkedHashSet::new));
                return logs;

        } catch (IOException e) {
            throw new LogicalError();
        }
    }

    public Set<Singular> findFinishLogs(){
        LOG.debug(LOG_DEBUG_EMPTY_PATTERN.getMessage());
        final String fileName = "/home/vladbagach/file/finish.log";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))){
            Set<Singular> logs = new LinkedHashSet<>();
            Set<Singular> finalLogs = logs;
            logs = reverseFile(stream)
                    .map(t -> new Singular(t.substring(4,16), (parseStringToZonedDataType((Long.parseLong(t.substring(16, 28))), NameFileConstants.FINISH))))
                    .filter(finalLogs::add)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            return logs;

        } catch (IOException e) {
            throw new LogicalError();
        }
    }

    private ZonedDateTime parseStringToZonedDataType(long m , NameFileConstants name) {
        LOG.debug(LOG_DEBUG_EMPTY_PATTERN.getMessage());
        Instant instant = Instant.ofEpochSecond(m);
        ZonedDateTime result = null;
        if(name == NameFileConstants.START){
            result = ZonedDateTime.ofInstant(Instant.from(instant), ZoneId.of(startZoneId));
        } else if (name == NameFileConstants.FINISH) {
            result = ZonedDateTime.ofInstant(Instant.from(instant), ZoneId.of(finishZoneId));
        }
       return result;
    }

    private <T> Stream<T> reverseFile(Stream<T> stream) {
        LOG.debug(LOG_DEBUG_EMPTY_PATTERN.getMessage());
        LinkedList<T> stack = new LinkedList<>();
        stream.forEach(stack::push);

        return stack.stream();
    }

    private ZonedDateTime diffDate(ZonedDateTime start , ZonedDateTime finish){
        LOG.debug(LOG_DEBUG_EMPTY_PATTERN.getMessage());
        Period period = Period.between(start.toLocalDate() , finish.toLocalDate());
        Duration duration = Duration.between(start , finish);
        long second = (int) duration.getSeconds();
        long hours = (int) duration.toHours();
        long minutes = (int) duration.toMinutes();
        long years = period.getYears();
        long days = period.getDays();
        long moths = period.getMonths();

        String f = String.format("%ty%tm%td%tH%tM%tS" , years , moths , days , hours , minutes , second);
        long d = Long.parseLong(f);

        return parseStringToZonedDataType(d , NameFileConstants.START);
    }

    private List<ZonedDateTime> timeStartFile(){
        LOG.debug(LOG_DEBUG_EMPTY_PATTERN.getMessage());
        return findStartLogs1().stream().map(Singular::getTime).collect(Collectors.toList());
    }

    private List<ZonedDateTime> timeFinishFile(){
        LOG.debug(LOG_DEBUG_EMPTY_PATTERN.getMessage());
        return findFinishLogs().stream().map(Singular::getTime).collect(Collectors.toList());
    }
    private List<String> idFinishFile(){
        LOG.debug(LOG_DEBUG_EMPTY_PATTERN.getMessage());
        return findFinishLogs().stream().map(Singular::getId).collect(Collectors.toList());
    }

}
