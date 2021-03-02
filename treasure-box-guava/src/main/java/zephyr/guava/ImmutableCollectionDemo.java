package zephyr.guava;

import com.google.common.collect.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImmutableCollectionDemo {

    // Collection               JDK             ImmutableCollection
    // List                     JDK             ImmutableList
    // Set                      JDK             ImmutableSet
    // SortedSet/NavigableSet   JDK             ImmutableSortedSet
    // Map                      JDK             ImmutableMap
    // SortedMap                JDK             ImmutableSortedMap
    // Multiset                 Guava           ImmutableMultiset
    // SortedMultiset           Guava           ImmutableSortedMultiset
    // Multimap                 Guava           ImmutableMultimap
    // ListMultimap             Guava           ImmutableListMultimap
    // SetMultimap              Guava           ImmutableSetMultimap
    // BiMap                    Guava           ImmutableBiMap
    // ClassToInstanceMap       Guava           ImmutableClassToInstanceMap
    // Table                    Guava           ImmutableTable

    public static void main(String[] args) {
        final ImmutableSet<String> ofColorSet = ImmutableSet.of(
                "red",
                "orange",
                "yellow",
                "green",
                "blue",
                "purple");

        final ImmutableSet<String> copyColorSet = ImmutableSet.copyOf(ofColorSet);

        final ImmutableSet<String> buildColorSet = ImmutableSet
                .<String>builder()
                .addAll(ofColorSet)
                .add("black")
                .build();
        log.info("ImmutableSet");
        log.info("{}", ofColorSet);
        log.info("{}", copyColorSet);
        log.info("{}", buildColorSet);

        // Multiset
        // Map                  Corresponding Multiset  Supports null elements
        // HashMap              HashMultiset            Yes
        // TreeMap              TreeMultiset            Yes
        // LinkedHashMap        LinkedHashMultiset      Yes
        // ConcurrentHashMap    ConcurrentHashMultiset  No
        // ImmutableMap         ImmutableMultiset       No

        final Multiset<String> hashMultiset = HashMultiset.create();
        hashMultiset.addAll(ofColorSet);
        hashMultiset.add("blue");

        log.info("");
        log.info("HashMultiset");
        log.info("{}", hashMultiset);
        log.info("read count: {}", hashMultiset.count("red"));
        log.info("blue count: {}", hashMultiset.count("blue"));

        final Multiset<String> treeMultiset = TreeMultiset.create();
        treeMultiset.addAll(ofColorSet);
        treeMultiset.add("blue");
        treeMultiset.add("orange");
        treeMultiset.add("orange");
        treeMultiset.add("orange");

        log.info("");
        log.info("TreeMultiset");
        log.info("{}", treeMultiset);
        log.info("read count: {}", treeMultiset.count("red"));
        log.info("blue count: {}", treeMultiset.count("blue"));
        log.info("orange count: {}", treeMultiset.count("orange"));

        // Implementation           Keys behave like...     Values behave like..
        // ArrayListMultimap        HashMap                 ArrayList
        // HashMultimap             HashMap                 HashSet
        // LinkedListMultimap       LinkedHashMap           LinkedList
        // LinkedHashMultimap       LinkedHashMap           LinkedHashSet
        // TreeMultimap             TreeMap                 TreeSet
        // ImmutableListMultimap    ImmutableMap            ImmutableList
        // ImmutableSetMultimap     ImmutableMap            ImmutableSet

        final ListMultimap<String, Integer> linkedHashArrayListMultiMap = MultimapBuilder.linkedHashKeys().arrayListValues().build();
        linkedHashArrayListMultiMap.put("c", 5);
        linkedHashArrayListMultiMap.put("a", 1);
        linkedHashArrayListMultiMap.put("a", 2);
        linkedHashArrayListMultiMap.put("a", 4);
        linkedHashArrayListMultiMap.put("b", 3);

        log.info("");
        log.info("MultiMap<K, V> 类似于 Map<K, Collection<V>>");
        log.info("linkedHashArrayListMultiMap");
        log.info("{}", linkedHashArrayListMultiMap);

        final SetMultimap<String, Integer> treeHashSetMultiMap = MultimapBuilder.treeKeys().hashSetValues().build();
        treeHashSetMultiMap.put("b", 3);
        treeHashSetMultiMap.put("a", 1);
        treeHashSetMultiMap.put("c", 4);
        treeHashSetMultiMap.put("c", 5);

        log.info("treeHashSetMultiMap");
        log.info("{}", treeHashSetMultiMap);

        // Key-Value Map Impl   Value-Key Map Impl  Corresponding BiMap
        // HashMap              HashMap             HashBiMap
        // ImmutableMap         ImmutableMap        ImmutableBiMap
        // EnumMap              EnumMap             EnumBiMap
        // EnumMap              HashMap             EnumHashBiMap

        BiMap<String, Integer> biMap = HashBiMap.create();
        biMap.put("Bob", 42);
        biMap.put("alice", 41);

        log.info("");
        log.info("biMap");
        log.info("{}", biMap);
        log.info("{}", biMap.inverse().get(42));

        Table<String, String, Integer> table = HashBasedTable.create();
        table.put("v1", "v2", 4);
        table.put("v1", "v3", 20);
        table.put("v2", "v3", 5);

        log.info("");
        log.info("table<R, C, V> 类似于 Map<R, Map<C, V>>");
        log.info("{}", table);
        log.info("{}", table.row("v1")); // returns a Map mapping v2 to 4, v3 to 20
        log.info("{}", table.column("v3")); // returns a Map mapping v1 to 20, v2 to 5
    }

}
