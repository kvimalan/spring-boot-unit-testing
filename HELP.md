# Getting Started

### TimeZone - UTC 
Store time with timezone (UTC - Configured in application.properties file by default)
Format : Date & Time in UTC timezone.

Eg:
DateTimeWrapper (Before store data into DB) : DateTimeWrapper(id=null, instant=2019-11-12T05:50:00Z, localDateTime=2019-11-12T07:50, offsetDateTime=2019-12-14T08:30Z, zonedDateTime=2019-12-14T08:30Z, localTime=15:30, offsetTime=16:30+02:00, localDate=2019-08-11)
After DateTimeWrapper(from DB)              : DateTimeWrapper(id=1, instant=2019-11-12T05:50:00Z, localDateTime=2019-11-12T07:50, offsetDateTime=2019-12-14T14:00+05:30, zonedDateTime=2019-12-14T14:00+05:30[Asia/Kolkata], localTime=15:30, offsetTime=16:30+05:30, localDate=2019-08-11)

