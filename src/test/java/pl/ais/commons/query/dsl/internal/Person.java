package pl.ais.commons.query.dsl.internal;

/**
 * Person.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public class Person {

    private int age;

    private String name;

    protected Person() {
        super();
    }

    /**
     * Constructs new instance.
     *
     * @param name the name
     * @param age  the age
     */
    public Person(final String name, final int age) {
        this();
        this.age = age;
        this.name = name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

}
