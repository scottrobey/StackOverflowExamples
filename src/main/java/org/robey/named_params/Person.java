package org.robey.named_params;

/**
 * See: https://stackoverflow.com/questions/1988016/named-parameter-idiom-in-java/47916808#47916808 
 *
**/
class Person {
    static class TypedContainer<T> {
        T val;
        TypedContainer(T val) { this.val = val; }
    }
    static Age age(int age) { return new Age(age); }
    static class Age extends TypedContainer<Integer> {
        Age(Integer age) { super(age); }
    }
    static Weight weight(int weight) { return new Weight(weight); }
    static class Weight extends TypedContainer<Integer> {
        Weight(Integer weight) { super(weight); }
    }
    static Height heightInches(int height) { return new Height(height); }
    static class Height extends TypedContainer<Integer> {
        Height(Integer height) { super(height); }
    }

    private final int age;
    private final int weight;
    private final int height;

    Person(Age age, Weight weight, Height height) {
        this.age = age.val;
        this.weight = weight.val;
        this.height = height.val;
    }
    public int getAge() { return age; }
    public int getWeight() { return weight; }
    public int getHeight() { return height; }

}
