package com.example.yangchaoming.bappdemo;

import com.example.yangchaoming.bappdemo.miscellaneous.TestBean;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String TAG = "ExampleUnitTest";
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testNullArr(){
        TestBean testBean0 = new TestBean("爱文华", 100, "A");
        TestBean testBean1 = new TestBean("张文顺", 12, "C");
        TestBean testBean2 = new TestBean("孙阿斯顿", 13, "B");
        TestBean testBean3 = new TestBean("阿斯顿", 13, "#");

        List<TestBean> list=new ArrayList<>();
        list.add(testBean1);
        list.add(testBean2);
        list.add(testBean0);
        list.add(testBean3);

        Collections.sort(list, new Comparator<TestBean>() {
            @Override
            public int compare(TestBean o1, TestBean o2) {
                return o1.getLastName().compareTo(o2.getLastName());
//                return o1.getAge()-(o2.getAge());
            }
        });

//        Log.e(TAG, "testNullArr: "+list.toString() );
        assertEquals(testBean3, list.get(0));
    }
}