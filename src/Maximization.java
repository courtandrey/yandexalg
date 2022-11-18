import java.util.*;

public class Maximization {

    public static void main(String[] args) {
        System.out.println(Method.ARRAY_SORTED_NO_OPTIMIZATION+" "+getTests(Method.ARRAY_SORTED_NO_OPTIMIZATION));
        System.out.println(Method.ARRAY_SORTED_OPTIMIZED+" "+getTests(Method.ARRAY_SORTED_OPTIMIZED));
        System.out.println("\n");
        System.out.println(Method.PRIORITY_QUEUE_NO_OPTIMIZATION+" "+getTests(Method.PRIORITY_QUEUE_NO_OPTIMIZATION));
        System.out.println(Method.PRIORITY_QUEUE_OPTIMIZED+" "+getTests(Method.PRIORITY_QUEUE_OPTIMIZED));
    }

    enum Method {
        PRIORITY_QUEUE_NO_OPTIMIZATION,
        PRIORITY_QUEUE_OPTIMIZED,
        ARRAY_SORTED_OPTIMIZED,
        ARRAY_SORTED_NO_OPTIMIZATION,
    }

    public record TestResult(double[][] testaB, double[][] testAb) {
        @Override
            public String toString() {
                return "TestResult{" +
                        "testaB=" + Arrays.deepToString(testaB) +
                        ", testAb=" + Arrays.deepToString(testAb) +
                        '}';
            }
        }

    public static String getTests(Method method) {
        return (new TestResult(getTestaB(method), getTestAb(method))).toString();
    }

    private static double[][] getTestaB(Method method) {
        double[][] testaB = new double[10][2];
        int pow = 4;
        for (int i=0; i<10; i++) {
            double thisTest = testSpeed(method, (4), (pow));
            testaB[i][0] = pow;
            testaB[i][1] = thisTest;
            pow = pow * 2;
        }
        return testaB;
    }

    private static double[][] getTestAb(Method method) {
        double[][] testaB = new double[10][2];
        int pow = 4;
        for (int i=0; i<10; i++) {
            double thisTest = testSpeed(method, (pow), (4));
            testaB[i][0] = pow;
            testaB[i][1] = thisTest;
            pow = pow * 2;
        }
        return testaB;
    }

    private static double testSpeed(Method method, int aLength, int bLength) {
        double sum = 0;
        for (int j=0; j<10000; j++) {
            String a = generateStringWithLength(aLength);
            String b = generateStringWithLength(bLength);
            Date start = new Date();
            switch (method) {
                case PRIORITY_QUEUE_NO_OPTIMIZATION -> testSpeedPriorityNonOptimized(a,b);
                case ARRAY_SORTED_OPTIMIZED -> testSpeedArraySortedOptimized(a,b);
                case ARRAY_SORTED_NO_OPTIMIZATION -> testSpeedArraySortNonOptimized(a,b);
                case PRIORITY_QUEUE_OPTIMIZED -> testSpeedPriorityOptimized(a,b);
            }
            Date end = new Date();
            sum += end.getTime() - start.getTime();
        }
        return sum;
    }

    private static String testSpeedArraySortNonOptimized(String a, String b) {
        int[] aArray = new int[a.length()];
        for (int i=0; i<a.length();i++) {
            aArray[i] = Integer.parseInt(String.valueOf(a.charAt(i)));
        }
        int[] bArray = new int[b.length()];
        for (int i=0; i<b.length();i++) {
            bArray[i] = Integer.parseInt(String.valueOf(b.charAt(i)));
        }
        Arrays.sort(bArray);
        int marker = bArray.length - 1;
        if (bArray[marker] != 0) {
            for (int i = 0; i < a.length(); i++) {
                if (aArray[i] < bArray[marker]) {
                    aArray[i] = bArray[marker];
                    --marker;
                    if (marker < 0 || bArray[marker] == 0) break;
                }
            }
        }
        StringBuilder result = new StringBuilder();
        for (int i:aArray) {
            result.append(i);
        }
        return result.toString();
    }

    private static String testSpeedArraySortedOptimized(String a, String b) {
        if (a.length() < b.length()) {
            return countNinesArraySorted(a,b);
        }

        else {
            return substringArraySorted(a,b);
        }
    }

    private static String substringArraySorted(String a, String b) {
        int[] bArray = new int[b.length()];
        for (int i = 0; i < b.length(); i++) {
            int num = Integer.parseInt(String.valueOf(b.charAt(i)));
            bArray[i] = num;
        }
        Arrays.sort(bArray);

        StringBuilder resultBuilder = new StringBuilder();
        int marker = bArray.length - 1;
        for (int i = 0; i < a.length(); i++) {
            int num = Integer.parseInt(String.valueOf(a.charAt(i)));
            if (marker < 0 || bArray[marker] == 0) {
                resultBuilder.append(a.substring(i));
                break;
            }
            if (num < bArray[marker]) {
                resultBuilder.append(bArray[marker]);
                --marker;
            } else {
               resultBuilder.append(num);
            }
        }
        return resultBuilder.toString();
    }

    private static String countNinesArraySorted(String a, String b) {
        int nines = 0;
        int lengthA = a.length();
        int[] aArray = new int[lengthA];

        for (int i = 0; i < a.length(); i++) {
            int num = Integer.parseInt(String.valueOf(a.charAt(i)));
            if (num == 9) lengthA--;
            aArray[i] = num;
        }

        int[] bArray = new int[b.length()];
        for (int i = 0; i < b.length(); i++) {
            int num = Integer.parseInt(String.valueOf(b.charAt(i)));
            if (num == 9) ++nines;
            bArray[i] = num;
            if (nines == lengthA) break;
        }

        Arrays.sort(bArray);

        StringBuilder resultBuilder = new StringBuilder();
        int marker = bArray.length - 1;
        if (bArray[marker] != 0) {
            for (int i = 0; i < a.length(); i++) {
                if (marker < 0 || bArray[marker] == 0) {
                    resultBuilder.append(a.substring(i));
                    break;
                }
                if (aArray[i] < bArray[marker]) {
                    resultBuilder.append(bArray[marker]);
                    --marker;
                } else {
                    resultBuilder.append(aArray[i]);
                }
            }
        }
        return resultBuilder.toString();
    }

    private static String testSpeedPriorityOptimized(String a, String b) {
        if (a.length() < b.length()) {
            return countNinesPriority(a,b);
        } else {
            return substringPriority(a,b);
        }
    }

    private static String substringPriority(String a, String b) {
        PriorityQueue<Integer> bQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < b.length(); i++) {
            int num = Integer.parseInt(String.valueOf(b.charAt(i)));
            if (num == 0) continue;
            bQueue.offer(num);
        }

        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            int num = Integer.parseInt(String.valueOf(a.charAt(i)));
            if (bQueue.peek() == null || bQueue.peek() == 0) {
                resultBuilder.append(a.substring(i));
                break;
            }
            if (num < bQueue.peek()) {
                resultBuilder.append(bQueue.poll());
            }
            else {
                resultBuilder.append(num);
            }
        }
        return resultBuilder.toString();
    }

    private static String countNinesPriority(String a, String b) {
        int lengthA = a.length();
        int[] aArray = new int[lengthA];
        int nines = 0;
        for (int i = 0; i < a.length(); i++) {
            int num = Integer.parseInt(String.valueOf(a.charAt(i)));
            if (num == 9) lengthA--;
            aArray[i] = num;
        }

        PriorityQueue<Integer> bQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < b.length(); i++) {
            int num = Integer.parseInt(String.valueOf(b.charAt(i)));
            if (num == 9) ++nines;
            if (num == 0) continue;
            bQueue.offer(num);
            if (nines == lengthA) break;
        }

        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            if (bQueue.peek() == null || bQueue.peek() == 0) {
                resultBuilder.append(a.substring(i));
                break;
            }
            if (aArray[i] < bQueue.peek()) {
                resultBuilder.append(bQueue.poll());
            } else {
                resultBuilder.append(aArray[i]);
            }
        }
        return resultBuilder.toString();
    }


    private static String testSpeedPriorityNonOptimized(String a, String b) {
        int[] aArray = new int[a.length()];
        for (int i=0; i<a.length();i++) {
            aArray[i] = Integer.parseInt(String.valueOf(a.charAt(i)));
        }
        PriorityQueue<Integer> bQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i=0; i<b.length();i++) {
            bQueue.add(Integer.parseInt(String.valueOf(b.charAt(i))));
        }
        for (int i = 0; i <a.length(); i++) {
            if (bQueue.peek() == null) break;
            if (aArray[i] < bQueue.peek()) {
                aArray[i] = bQueue.poll();
            }
        }
        StringBuilder result = new StringBuilder();
        for (int i:aArray) {
            result.append(i);
        }
        return result.toString();
    }

    private static String generateStringWithLength(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

}