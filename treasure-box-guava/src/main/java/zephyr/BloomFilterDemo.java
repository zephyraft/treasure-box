package zephyr;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 布隆过滤器
 * 空间效率高的概率型数据结构，用来检查一个元素是否在一个集合中
 * 返回 可能存在 或 一定不存在
 */
public class BloomFilterDemo {

    public static void main(String[] args) {
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), 10);
        bloomFilter.put(1);
        bloomFilter.put(3);
        bloomFilter.put(103);
        bloomFilter.put(1000);
        bloomFilter.put(8);

        System.out.println(bloomFilter.mightContain(9));
        System.out.println(bloomFilter.mightContain(1000));

    }

}
