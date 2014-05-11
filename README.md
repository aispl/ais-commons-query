# AIS.PL Commons - Query

This library provides few abstractions usable while querying the data, like _Selection_ and _SearchResults_,
along with their [Querydsl](http://www.querydsl.com/) based implementation.

## Usage examples

We will use simple domain model for the examples below

![Course - Participant diagram](src/site/resources/Course - Participant.png?raw=true "Course - Participant")

### Reading all courses into list

```java
import static pl.ais.commons.query.dsl.transformer.Transformers.asList;
import static pl.ais.example.webapp.domain.model.QCourse.course;
...
final JPAQuery query = new JPAQuery(entityManager, jpqlTemplates).from(course);
final List<Course> courses = Results.forQuery(query).transform(asList(course));
```

### Counting all courses
```java
import static pl.ais.commons.query.dsl.transformer.Transformers.asNumberOfResults;
import static pl.ais.example.webapp.domain.model.QCourse.course;
...
final JPAQuery query = new JPAQuery(entityManager, jpqlTemplates).from(course);
final Long coursesNo = Results.forQuery(query).transform(asNumberOfResults());
```

### Fetching one of the properties for course matching specified criteria
```java
import static pl.ais.commons.query.dsl.transformer.Transformers.asSingleResult;
import static pl.ais.example.webapp.domain.model.QCourse.course;
...
final JPAQuery query = new JPAQuery(entityManager, jpqlTemplates).from(course);
final String courseName = Results.forQuery(query).matching(course.id.eq(1)).transform(asSingleResult(course.name));
```

### Fetching participant names for courses having more than 5 participants, along with the total number of matching participants.
```java
import static pl.ais.commons.query.dsl.transformer.Transformers.asSearchResults;
import static pl.ais.example.webapp.domain.model.QCourse.course;
import static pl.ais.example.webapp.domain.model.QParticipant.participant;
...
final JPAQuery query = new JPAQuery(entityManager, jpqlTemplates).from(course).leftJoin(course.participants, participant).orderBy(participant.name.asc());
final SearchResults<String> searchResults = Results.forQuery(query).matching(course.participants.size().gt(5)).transform(asSearchResults(participant.name));
```