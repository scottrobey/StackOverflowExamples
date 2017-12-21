package org.robey.named_params;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.robey.named_params.Person.age;
import static org.robey.named_params.Person.heightInches;
import static org.robey.named_params.Person.weight;

public class PersonTest {
    @Test
    public void testPersonConstruction() {
        Person p = new Person( age(16), weight(100), heightInches(65) );
        assertEquals(16, p.getAge());
        assertEquals(100, p.getWeight());
        assertEquals(65, p.getHeight());
    }

    @Test
    public void testWrongOrder() throws Exception {
        // compile-error, but no arbitrary ordering
        //Person p = new Person(weight(16), age(100), heightInches(65));

    }

}
