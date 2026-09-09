package org;
import java.util.ArrayList;import java.util.Arrays;
import java.util.List;
import java.util.List;

public class Methods {
    public static boolean isEven(int n) {
        Boolean ternaryStatus = (n % 2 == 0) ? true : false;
        System.out.println(ternaryStatus);
        return ternaryStatus;

    }

    public static String checkAccess(int age) {
        String result;
        if (age > 18) result = "Allowed";
        else {
            result = "Denied";
        }
        System.out.println(result);
        return result;
    }

    public static boolean isPositive(int n) {

        return n >= 0 ? true : false;
    }

    public static String getGrade(int score) {
        String Grade;
        if (score <= 20 && score >= 0) {
            System.out.println(Grade = "E");
        } else if (score >= 21 && score <= 40) {
            System.out.println(Grade = "D");
        } else if (score >= 41 && score <= 60) {
            System.out.println(Grade = "C");
        } else if (score >= 61 && score <= 80) {
            System.out.println(Grade = "B");
        } else if (score >= 81 && score <= 100) {
            System.out.println(Grade = "A");
        } else System.out.println(Grade = "Error");
        return Grade;
    }

    public static String blastOff(int Start) {
        String result = "";
        if (Start <= 0) {
            for (Start = Start; Start != 1 && Start <= 0; Start++) {
                result = result + Start + " ";
                continue;
            }
        } else
            for (Start = Start; Start != 1 && Start >= 0; Start--) {
                result = result + Start + " ";
                continue;
            }
        if (Start == 1) {
            result = result + Start + " " + "Поехали!";
        }
        System.out.println(result);

        return result;

    }

    public static int sumToN(int n) {
        int sum = 0;
        if (n >= 0) {
            for (n = n; n >= 1; n--) {
                sum = sum + n;
            }
            System.out.println(sum);
            return sum;
        } else {
            for (n = n; n <= 1; n++) {
                sum = sum + n;
            }
            System.out.println(sum);
            return sum;
        }
    }

    public static boolean hasBug(String[] messages) {
        for (int i = 0; i < messages.length; i++) {
            if (messages[i].equals("Bug")) {
                return true;
            }
        }

        return false;
    }

    public static String getEvenInRange(int start, int end) {
        String result = "";
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }
        {
            for (int i = start; i <= end; i++) {
                if (i % 2 == 0) {
                    if (result.isEmpty()) {
                        result = result + i;

                    } else {
                        result = result + " " + i;
                    }
                }
            }
            System.out.println(result);
        }
        return result;
    }

    public static int findMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];

            }
        }
        System.out.println(max);
        return max;

    }

    public static String[] reverse(String[] arr2) {
        String [] rev  = new String[arr2.length];
        int j = 0;
        for (int i = arr2.length-1;i >= 0; i--) {
            rev [j] = arr2 [i];
            j++;
        }
        System.out.println(java.util.Arrays.toString(rev));
        return rev;
    }

    public static double calcAverage (List<Integer> list) {
        int sum = 0;
        for (int i = list.size()-1; i >= 0; i--)
            sum = sum + list.get(i);
        System.out.println(sum/list.size());
        return sum/list.size();
    }

    public static List<String> removeSpecificName(List<String> list2,String nameToRemove){
        List<String> list3 = new ArrayList<>(list2);
        for (int i = list3.size()-1; i >= 0; i--) {
            if (list3.get(i).equals(nameToRemove))
                list3.remove(i);

        }
        System.out.println(list3);
        return list3;
    }

}
