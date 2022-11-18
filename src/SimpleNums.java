import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SimpleNums {
    public static void main(String[] args) {
        System.out.println(Method.DEFAULT+" "+Arrays.deepToString(getTest(Method.DEFAULT)));
        System.out.println(Method.ERATOSTHENES+" "+Arrays.deepToString(getTest(Method.ERATOSTHENES)));
    }
    enum Method {
        DEFAULT,
        ERATOSTHENES
    }
    public static double[][] getTest(Method method) {
        long number = 8;
        double[][] testResults = new double[10][2];
        for (int i=0; i<10;i++) {
            testResults[i][0] = number;
            testResults[i][1] = testSpeed(method, number);
            number = number * 2;
        }
        return testResults;
    }

    public static double testSpeed(Method method, long number) {
        double sum = 0;
        for (int j=0; j<10000; j++) {
            Date start = new Date();
            switch (method) {
                case DEFAULT -> findSimpleIntsDefault(number);
                case ERATOSTHENES -> findSimpleIntsEratosthenes(number);
            }
            Date end = new Date();
            sum += end.getTime() - start.getTime();
        }
        return sum;
    }

    public static List<Integer> findSimpleIntsEratosthenes(long number) {
        boolean[] nums = new boolean[(int)number];
        List<Integer> simpleInts = new ArrayList<>();
        Arrays.fill(nums,true);
        nums[1] = false;
        for (int i=2; i*i < (int) number; i++) {
            if (nums[i]) {
                for (int j = i * i; j < (int) number; j += i) {
                    nums[j] = false;
                }
                simpleInts.add(i);
            }
        }
        for (int i = (int) (Math.sqrt(number) + 1); i < number; i++) {
            if (nums[i]) simpleInts.add(i);
        }
        simpleInts.add(1);
        return simpleInts;
    }

    public static List<Integer> findSimpleIntsDefault(long number) {
        List<Integer> simples = new ArrayList<>();
        for (int i = 2; i <= number; i++) {
            if ((i > 10) && (i % 10 == 5)) continue;
            boolean trash = false;
            for (Integer s:simples) {
                if (s*s - 1 > i) {
                    break;
                }
                if (i % s == 0) {
                    trash = true;
                    break;
                }
            }
            if (!trash) {
                simples.add(i);
            }
        }
        simples.add(1);
        return simples;
    }
}
