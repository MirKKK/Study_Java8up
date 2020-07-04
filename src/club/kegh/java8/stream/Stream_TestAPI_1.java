package club.kegh.java8.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Stream_TestAPI_1 {

    public static void main(String[] args) {

    }

    @Test
    public  void  test1(){
        //1. Collection 提供了两个方法
        // 串型：stream() 并型：parallelStream()
        List<String> list =new ArrayList();
        Stream<String> stream1=list.stream();
        Stream<String> stream1_1=list.parallelStream();

        //2. 通过 Arrays 中的 stream() 获取一个数组流
        Integer[] nums = new Integer[10];
        Stream<Integer> stream2 = Arrays.stream(nums);

        //3. 通过 Stream 类中静态方法 of()
        Stream<Integer> stream3 = Stream.of(1,2,3,4,5,6);

        //4. 创建无限流
        //4-1迭代,累加
        Stream<Integer> stream4 = Stream.iterate(0, (x) -> x + 2).limit(10);
        stream4.forEach(System.out::println);

        //4-2生成，随机
        Stream<Double> stream4_2 = Stream.generate(Math::random).limit(2);
        stream4_2.forEach(System.out::println);
    }

}
