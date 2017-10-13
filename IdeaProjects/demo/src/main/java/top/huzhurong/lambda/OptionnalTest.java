    package top.huzhurong.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by 竹 on 2017/10/13.
 */
public class OptionnalTest {
    public static void main(String[] args) {
        //我要达到的效果应该是，如果为null，就怎么样，不为null就怎么样，而不是多了这么多的废话去做些什么东西
        final List<String> lists = Arrays.asList("chen","shun","hu","zhu","rong");
        Stream<String> stream = lists.stream();

        stream.map(name->name.toUpperCase()).findAny().ifPresent(ac-> System.out.println(ac));

//        stream.filter(name -> name.endsWith("hu")).findAny().ifPresent(action-> System.out.println(action));
//        u.ifPresent(action-> System.out.println(action));

        //这个的缺点是只能选择一个（有识之士可以告知）
//        lists.stream().filter(name->name.endsWith("n")).findFirst().ifPresent(value->{
            //操作 value
//        });

//        Optional<String> n = stream.filter(name -> name.endsWith("n")).findFirst();
//
//        n.ifPresent(action->{
//            System.out.println(action);
//        });

//        Optional<List> ok1 = Optional.ofNullable(lists);



        //省下了判断null的时间
//        if(ok1.isPresent()){
//            System.out.println("***********");
//        }else{
//            System.out.println("$$$$$$$$$$$");
//        }
//        ok1.ifPresent(name->{System.out.println(name);});

//        Optional<List> optional = ok1.filter(name->name.size()>2);
//        optional.ifPresent(name->name.stream().filter(value->{
//            System.out.print(value);
//            return true;
//        }));
    }
}
