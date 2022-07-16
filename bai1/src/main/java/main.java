import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

public class main {
    public static void main(String[] args) {
        System.out.println("Most occur char: " + mostChar("fadafwqweefafdsvsxxxx"));
        System.out.println("Sorted array: " + Arrays.toString(sortArray(new int[]{1, 5, 6, 8, 5, 2, 1, 4, 5, 222, 11, 4})));
        System.out.println("Number is prime: " + checkPrime(14));
        System.out.println("Area of triangle: " + area(3, 4, 5));
        drawCircle(10);
    }

    public static int sumOfArray(int[] input) {
        int sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += input[i];
        }
        return sum;
    }

    public static ArrayList<Character> mostChar(String input) {
        String s = input.replaceAll(" ", "");


        // Initial parameters
        char[] charSet = s.toCharArray();
        Arrays.sort(charSet);
        int max = 0;
        int count = 0;
        char pointChar = charSet[0];
        ArrayList<Integer> countList = new ArrayList<>();
        ArrayList<Character> uniqueChar = new ArrayList<>();
        ArrayList<Character> result = new ArrayList<>();
        uniqueChar.add(pointChar);


        for (int i = 0; i < charSet.length; i++) {
            if (charSet[i] == pointChar) {
                ++count;
            } else {
                if (count > max) {
                    max = count;
                }
                pointChar = charSet[i];
                uniqueChar.add(pointChar);
                countList.add(count);
                count = 1;
            }
        }
        countList.add(count);

        for (int i = 0; i < countList.size(); i++) {
            if (countList.get(i) == max) {
                result.add(uniqueChar.get(i));
            }
        }
        return result;
    }

    public static int[] sortArray(int[] arr) {
        Arrays.sort(arr);
        return arr;
    }

    public static boolean checkPrime(int num) {
        boolean isPrime = true;
        for (int i = 2; i < num / 2; i++) {
            if (num % i == 0) {
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }

    public static double area(double a, double b, double c) {
        if ((a + b > c) && (b + c > a) && (c + a > b)) {
            double semiPerimeter = (a + b + c) / 2;
            double area = Math.sqrt(semiPerimeter * (semiPerimeter - a) * (semiPerimeter - b) * (semiPerimeter - c));
            return area;
        }
        throw new ArithmeticException("This is not a triangle");
    }

    public static void drawCircle(double radius) {
        int revolution = 20;
        for (int y = -revolution; y < revolution; y++) {
            for (int x = -revolution; x < revolution; x++) {
                if (((radius >= Math.sqrt(x * x + y * y) - 0.6) && (radius <= Math.sqrt(x * x + y * y) + 0.6))) {
                    System.out.print("*  ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }

    }
}
