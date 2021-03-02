package zephyr.guava;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;

@Slf4j
public class ObjectDemo {

    public static void main(String[] args) {
        // equals
        log.info("{}", Objects.equal("a", "a"));
        log.info("{}", Objects.equal(null, "a"));
        log.info("{}", Objects.equal("a", null));
        log.info("{}", Objects.equal(null, null));

        // hashCode
        log.info("{}", Objects.hashCode("a"));
        log.info("{}", Objects.hashCode("b"));

        // 生成 toString 字符串
        log.info("{}", MoreObjects.toStringHelper(new Object()).add("x", 1));
        log.info("{}", MoreObjects.toStringHelper("MyObject")
                .add("x", 1)
                .toString());

        // compare/compareTo
        log.info("{}", new Person("123", "456", 213).compareTo(new Person("123", "456", 214)));

        // order
        Ordering<Foo> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<Foo, String>() {
            public String apply(Foo foo) {
                return foo.sortedBy;
            }
        });

        log.info("{}", ordering.compare(new Foo("123", 1), new Foo(null, 12)));

    }

    static class Person implements Comparable<Person> {
        private String lastName;
        private String firstName;
        private int zipCode;

        public Person(String lastName, String firstName, int zipCode) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.zipCode = zipCode;
        }

        // compare/compareTo
        public int compareTo(Person that) {
            return ComparisonChain.start()
                    .compare(this.lastName, that.lastName)
                    .compare(this.firstName, that.firstName, Ordering.natural().nullsLast())
                    .compare(this.zipCode, that.zipCode)
                    .result();
        }
    }

    static class Foo {
        @Nullable
        private String sortedBy;
        private int notSortedBy;

        public Foo(@Nullable String sortedBy, int notSortedBy) {
            this.sortedBy = sortedBy;
            this.notSortedBy = notSortedBy;
        }
    }
}
