package club.kegh.java8.stream;

import club.kegh.java8.entity.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Stream_TestAPI_2 {

    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66),
            new Employee(101, "张三", 18, 9999.99),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );

    /*
	  筛选与切片
		filter——接收 Lambda ， 从流中排除某些元素。
		limit——截断流，使其元素不超过给定数量。
		skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
		distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
	 */

    //内部迭代：迭代操作 Stream API 内部完成
    @Test
    public  void  test1(){
        //在此只是返回流
        Stream<Employee> stream = emps.stream()
                .filter((e) ->{
                            System.out.println("中间操作");
                            return  e.getAge() > 35;
                        });
        //只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
        stream.forEach(System.out::println);

        //外部迭代(之前的方式)
        Iterator<Employee> employeeIterator = emps.iterator();
        while(employeeIterator.hasNext()){
            Employee next = employeeIterator.next();
        }
    }

    @Test
    public void test2() {
        //limit
        emps.stream().filter((e) -> {
                    System.out.println("测试中间操作");
                    return e.getSalary() >= 5000;
                }).limit(2).forEach(System.out::println);
    }

    @Test
    public void test3() {
        //skip
        emps.stream().filter((e) -> {
            System.out.println("测试中间操作");
            return e.getSalary() >= 5000;
        }).skip(2).forEach(System.out::println);
    }


    @Test
    public void test4() {
        //distinct去重 需要重新hash和equals方法
        emps.stream().filter((e) -> {
            System.out.println("测试中间操作");
            return e.getSalary() >= 5000;
        }).skip(2).distinct().forEach(System.out::println);
    }

	/*
		映射
		map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
		flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
	 */
    public void test5(){
        Stream<String> str = emps.stream()
                .map((e) -> e.getName());

        System.out.println("-------------------------------------------");

        List<String> strList = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");

        //map测试
        Stream<String> stream = strList.stream()
                .map(String::toUpperCase);
        stream.forEach(System.out::println);

        //流中存放一个流
        Stream<Stream<Character>> stream2 = strList.stream()
                .map(Stream_TestAPI_2::filterCharacter);

        stream2.forEach((sm) -> {
            sm.forEach(System.out::println);
        });

        System.out.println("---------------------------------------------");

        //使用flatMap，流的流-->变成一个流
        Stream<Character> stream3 = strList.stream()
                .flatMap(Stream_TestAPI_2::filterCharacter);

        stream3.forEach(System.out::println);
    }


    public  static  Stream<Character>  filterCharacter(String str){
        char[] charArray = str.toCharArray();
        List<Character> list =new ArrayList();
        for(Character character:charArray){
            list.add(character);
        }
        return  list.stream();
    }

    /*
		sorted()——自然排序
		sorted(Comparator com)——定制排序
	 */
    @Test
    public void test6(){
        emps.stream()
                .map(Employee::getName)
                .sorted()
                .forEach(System.out::println);

        System.out.println("------------------------------------");

        emps.stream()
                .sorted((x, y) -> {
                    if(x.getAge() == y.getAge()){
                        return x.getName().compareTo(y.getName());
                    }else{
                        return Integer.compare(x.getAge(), y.getAge());
                    }
                }).forEach(System.out::println);
    }
}
