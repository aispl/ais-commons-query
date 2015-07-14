# AIS.PL Commons - Query

This library provides few abstractions usable while querying the data, like _Selection_ and _SearchResults_,
along with their [Querydsl](http://www.querydsl.com/) based implementation.

## Usage examples

We will use simple domain model for the examples below

![Course - Participant diagram](src/site/resources/Course - Participant.png?raw=true "Course - Participant")

### Reading all courses into list

Having _JPAQuery_ you may create new _Results_ instance with _forQuery_ method, and request query results transforming
into number of records, single result, _List_, or _SearchResults_ using one of the [Transformers](src/main/java/pl/ais/commons/query/dsl/transformer/Transformers.java).
```java
import static pl.ais.commons.query.dsl.Transformers.list;
import static pl.ais.example.webapp.domain.model.QCourse.course;
...
final JPAQuery query = new JPAQuery(entityManager, jpqlTemplates).from(course);
final List<Course> courses = Results.forQuery(query).as(list(course));
```
The above code leads to following JPQL query:
```sql
select course from Course course
```

### Counting all courses

Simple example, counting all records returned by the query.

```java
import static pl.ais.commons.query.dsl.Transformers.numberOfResults;
import static pl.ais.example.webapp.domain.model.QCourse.course;
...
final JPAQuery query = new JPAQuery(entityManager, jpqlTemplates).from(course);
final Long coursesNo = Results.forQuery(query).as(numberOfResults());
```
The above code leads to following JPQL query:
```sql
select count(course) from Course course
```

### Fetching one of the properties for course matching specified criteria

A little more complicated use case, where you specify criteria to be matched by returned results (using _matching_ method),
and fetch only single _Course_ property (_name_) instead of the whole _Course_ entity.

```java
import static pl.ais.commons.query.dsl.Transformers.singleResult;
import static pl.ais.example.webapp.domain.model.QCourse.course;
...
final JPAQuery query = new JPAQuery(entityManager, jpqlTemplates).from(course);
final String courseName = Results.forQuery(query).matching(course.id.eq(1)).as(singleResult(course.name));
```
The above code leads to following JPQL query:
```sql
select course.name from Course course where course.id = 1
```

### Fetching participant names for courses having more than 5 participants, along with the total number of matching participants.

Again we specify additional criteria to be matched by returned results, but this time we request to transform them into _SearchResults_ instance. _SearchResults_ is a container for holding query results, along with total number of records
matching given criteria. It is very useful, when you want to paginate results using _Selection_ (see examples below),
in this case you fetch only subset of all records matching criteria, and are aware about the total number of matching records too.

```java
import static pl.ais.commons.query.dsl.Transformers.searchResults;
import static pl.ais.example.webapp.domain.model.QCourse.course;
import static pl.ais.example.webapp.domain.model.QParticipant.participant;
...
final JPAQuery query = new JPAQuery(entityManager, jpqlTemplates).from(course).leftJoin(course.participants, participant).orderBy(participant.name.asc());
final SearchResults<String> searchResults = Results.forQuery(query).matching(course.participants.size().gt(5)).as(searchResults(participant.name));
```
The above code leads to following JPQL queries:
```sql
select count(course) from Course course left join course.participants as participant where size(course.participants) > 5
select participant.name from Course course left join course.participants as participant where size(course.participants) > 5 order by participant.name asc
```

### Fetching first 10 course names, ordered by name

This time we use _Selection_ for fetching only subset of matching records (ex. for paginating them on UI).
_Selection_ instances should be created with specific _SelectionFactory_. If you use [Spring Framework](http://projects.spring.io/spring-framework/) in your application, you may define _SelectionFactory_ as singleton:
```xml
<bean id="selectionFactory" class="pl.ais.commons.query.dsl.QuerydslSelectionFactory" />
```
and then inject as dependency into your code:
```java
import static pl.ais.commons.query.dsl.Transformers.list;
import static pl.ais.example.webapp.domain.model.QCourse.course;
...
@Autowired
private transient QuerydslSelectionFactory selectionFactory;
....
final JPAQuery query = new JPAQuery(entityManager, jpqlTemplates).from(course);
final QuerydslSelection selection = selectionFactory.createSelection(0, 10, course.name.asc(), course.id.asc());
final List<String> searchResults = Results.forQuery(query).within(selection).as(list(course.name));
```
The above code leads to following JPQL query:
```sql
select course.name from Course course order by course.name asc, course.id asc
```

Above _Selection_ usage example restricts returned results to first 10 records, ordered by course name.
Please, note that we use course ID as the second ordering part, this is required to avoid problems with ordering data
described in [JPQL - pagination on Oracle Database with Hibernate ](http://vard-lokkur.blogspot.com/2012/08/jpql-pagination-on-oracle-database-with.html) - see also discussion on: [DZone](http://java.dzone.com/articles/jpql-pagination-oracle).
