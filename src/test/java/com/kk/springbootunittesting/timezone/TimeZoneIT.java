package com.kk.springbootunittesting.timezone;

import com.kk.springbootunittesting.SpringBootUnitTestingApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the UTC Hibernate configuration.
 *
 * Properities file : spring.jpa.properties.hibernate.jdbc.time_zone= UTC
 */
@Slf4j
@ActiveProfiles("test")
@SpringBootTest(classes = SpringBootUnitTestingApplication.class)
public class TimeZoneIT {

    @Autowired
    private DateTimeWrapperRepository dateTimeWrapperRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private DateTimeWrapper dateTimeWrapper;
    private DateTimeFormatter dateTimeFormatter;
    private DateTimeFormatter timeFormatter;
    private DateTimeFormatter dateFormatter;

    @BeforeEach
    public void setup() {
        dateTimeWrapper = new DateTimeWrapper();
        dateTimeWrapper.setInstant(Instant.parse("2019-11-12T05:50:00.0Z"));
        dateTimeWrapper.setLocalDateTime(LocalDateTime.parse("2019-11-12T07:50:00.0"));
        dateTimeWrapper.setOffsetDateTime(OffsetDateTime.parse("2019-12-14T08:30:00.0Z"));
        dateTimeWrapper.setZonedDateTime(ZonedDateTime.parse("2019-12-14T08:30:00.0Z"));
        dateTimeWrapper.setLocalTime(LocalTime.parse("15:30:00"));
        dateTimeWrapper.setOffsetTime(OffsetTime.parse("16:30:00+02:00"));
        dateTimeWrapper.setLocalDate(LocalDate.parse("2019-08-11"));

        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(ZoneId.of("UTC"));

        timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.of("UTC"));

        dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    public void printDateTimeWrapperFromDB(){
        log.info("Before DateTimeWrapper        : "+dateTimeWrapper.toString());
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        Optional<DateTimeWrapper> dateTimeWrapperFromDB = dateTimeWrapperRepository.findById(dateTimeWrapper.getId());
        log.info("After DateTimeWrapper(from DB) : "+dateTimeWrapperFromDB.get().toString());
    }

    @Test
    @Transactional
    public void storeInstantWithUtcConfigShouldBeStoredOnGMTTimeZone() {
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        String request = generateSqlRequest("instant", dateTimeWrapper.getId());
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(request);
        String expectedValue = dateTimeFormatter.format(dateTimeWrapper.getInstant());

        assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(resultSet, expectedValue);
    }

    @Test
    @Transactional
    public void storeLocalDateTimeWithUtcConfigShouldBeStoredOnGMTTimeZone() {
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        String request = generateSqlRequest("local_date_time", dateTimeWrapper.getId());
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(request);
        String expectedValue = dateTimeWrapper.getLocalDateTime().atZone(ZoneId.systemDefault()).format(
                dateTimeFormatter);

        assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(resultSet, expectedValue);
    }

    @Test
    @Transactional
    public void storeOffsetDateTimeWithUtcConfigShouldBeStoredOnGMTTimeZone() {
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        String request = generateSqlRequest("offset_date_time", dateTimeWrapper.getId());
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(request);
        String expectedValue = dateTimeWrapper.getOffsetDateTime().format(dateTimeFormatter);

        assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(resultSet, expectedValue);
    }

    @Test
    @Transactional
    public void storeZoneDateTimeWithUtcConfigShouldBeStoredOnGMTTimeZone() {
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        String request = generateSqlRequest("zoned_date_time", dateTimeWrapper.getId());
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(request);
        String expectedValue = dateTimeWrapper.getZonedDateTime().format(dateTimeFormatter);

        assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(resultSet, expectedValue);
    }

    @Test
    @Transactional
    public void storeLocalTimeWithUtcConfigShouldBeStoredOnGMTTimeZoneAccordingToHis1stJan1970Value() {
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        String request = generateSqlRequest("local_time", dateTimeWrapper.getId());
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(request);
        String expectedValue = dateTimeWrapper.getLocalTime()
                .atDate(LocalDate.of(1970, Month.JANUARY, 1))
                .atZone(ZoneId.systemDefault())
                .format(timeFormatter);

        assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(resultSet, expectedValue);
    }

    @Test
    @Transactional
    public void storeOffsetTimeWithUtcConfigShouldBeStoredOnGMTTimeZoneAccordingToHis1stJan1970Value() {
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        String request = generateSqlRequest("offset_time", dateTimeWrapper.getId());
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(request);
        String expectedValue = dateTimeWrapper.getOffsetTime()
                .toLocalTime()
                .atDate(LocalDate.of(1970, Month.JANUARY, 1))
                .atZone(ZoneId.systemDefault())
                .format(timeFormatter);

        assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(resultSet, expectedValue);
    }

    @Test
    @Transactional
    public void storeLocalDateWithUtcConfigShouldBeStoredWithoutTransformation() {
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        String request = generateSqlRequest("local_date", dateTimeWrapper.getId());
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(request);
        String expectedValue = dateTimeWrapper.getLocalDate().format(dateFormatter);

        assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(resultSet, expectedValue);
    }

    private String generateSqlRequest(String fieldName, long id) {
        return format("SELECT %s FROM date_time_wrapper where id=%d", fieldName, id);
    }

    private void assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(SqlRowSet sqlRowSet,
                                                                                String expectedValue) {
        while (sqlRowSet.next()) {
            String dbValue = sqlRowSet.getString(1);

            assertThat(dbValue).isNotNull();
            assertThat(dbValue).isEqualTo(expectedValue);
        }
    }
}
