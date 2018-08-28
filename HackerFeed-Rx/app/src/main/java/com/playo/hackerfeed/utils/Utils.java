package com.playo.hackerfeed.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Utils {

    public static boolean isOnline(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            return netInfo != null && netInfo.isConnected();
        }

        return false;
    }

    public static int[] rotateRight(int[] A, int k) {
        for (int i=0; i<k; i++) {
            rotateRightArray(A);
        }

        return A;
    }

    private static int[] rotateRightArray(int[] A) {
        int temp = A[A.length-1];
        for (int i=A.length-1; i>0; i--) {
            A[i] = A[i-1];
        }
        A[0] = temp;

        return A;
    }

    private static Map<String, Integer> sortHashMapValues(Map<String, Integer> unsortMap, final boolean order) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static void main(String[] args) {
        //System.out.println(gcd(3,2));
        //swap(-4,5);

        /*System.out.println(kthLargestSum(new int[]{10, -10, 20, -40}, 3, 5));*/

        //Scanner
        /*Scanner s = new Scanner(System.in);
        int N = s.nextInt();

        System.out.println("Entered value: " +N);*/

        /*pushToPQueue(10);
        pushToPQueue(20);
        pushToPQueue(5);
        pushToPQueue(25);
        pushToPQueue(48);
        pushToPQueue(48);
        pushToPQueue(48);

        System.out.println("CountHigh : "+countHigh());
        System.out.println("diff : "+diff());*/


        /*ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        ArrayList<Integer> l1 = new ArrayList<>();
        l1.add(0);
        l1.add(3);

        ArrayList<Integer> l2 = new ArrayList<>();
        l1.add(1);
        l1.add(4);

        ArrayList<Integer> l3 = new ArrayList<>();
        l1.add(3);
        l1.add(1);

        ArrayList<Integer> l4 = new ArrayList<>();
        l1.add(4);
        l1.add(0);

        list.add(l1);
        list.add(l2);
        list.add(l3);
        list.add(l4);

        System.out.println(getMinimumBusCapacity(4, list));*/

        /*System.out.println(getBeautyNumbers(1,2,3));*/

        //System.out.println(getBowlersOrderToSmith(3, 8, new int[]{10, 11, 8}).toString());

        //System.out.println(getTop3Coders(new String[]{"Nithin 98", "Niasasdasd 89", "Fadasda 67", "Pasasdad 78"}).toString());

        /*System.out.println(isOdd());*/

        int[] res = rotateRight(new int[] {1,2,3,4,5}, 3);
        for (int i: res) {
            System.out.println(i);
        }
    }

    static boolean isOdd() {
        return 2%2==1;
    }


    static List<String> getTop3Coders(String[] A) {
        Map<String, Integer> map = new HashMap<>();
        for (int i=0; i<A.length; i++) {
            String[] split = A[i].split(" ");
            map.put(split[0], Integer.valueOf(split[1]));
        }

        map = sortHashMapValues(map, false);
        Iterator<String> iter = map.keySet().iterator();
        int count = 0;
        List<String> list = new ArrayList<>();
        while (iter.hasNext() && count++ < 3) {
            list.add(iter.next());
        }

        return list;
    }

    static class BowlerEntry {
        int bowlerId;
        int ballsLeft;
    }

    static ArrayList<Integer> getBowlersOrderToSmith(int noOfBowlers, int ballsCanFace, int[] ballsLeftForBowlers) {
        PriorityQueue<BowlerEntry> priorityQueue = new PriorityQueue<>(3, new Comparator<BowlerEntry>() {
            @Override
            public int compare(BowlerEntry left, BowlerEntry right) {
                int id = left.bowlerId - right.bowlerId;
                int ballsLeft = right.ballsLeft - left.ballsLeft;
                if (ballsLeft == 0) {
                    return left.bowlerId - right.bowlerId;
                } else {
                    return ballsLeft;
                }
            }
        });
        ArrayList<Integer> bowlersOrder = new ArrayList<>();

        for (int i=0; i<noOfBowlers;i++) {
            BowlerEntry entry = new BowlerEntry();
            entry.ballsLeft = ballsLeftForBowlers[i];
            entry.bowlerId = i + 1;

            priorityQueue.add(entry);
        }

        int count = 0;
        while (count < ballsCanFace) {
            BowlerEntry max = priorityQueue.poll();
            int bowlerId = max.bowlerId;
            int ballsLeft = max.ballsLeft;

            bowlersOrder.add(bowlerId);

            ballsLeft -= 1;
            BowlerEntry newEntry = new BowlerEntry();
            newEntry.bowlerId = bowlerId;
            newEntry.ballsLeft = ballsLeft;

            priorityQueue.add(newEntry);
            count++;
        }

        return bowlersOrder;
    }

    static int getDuplicateProfiles(int n, int m, ArrayList <ArrayList<Integer>> relations) {
        /*ArrayList<Integer> l1 = new ArrayList<>();
        l1.add(3);
        map.put(1, l1);
        ArrayList<Integer> l2 = new ArrayList<>();
        l2.add(4);
        map.put(2, l2);
        ArrayList<Integer> l3 = new ArrayList<>();
        l3.add(1);
        map.put(3, l3);
        ArrayList<Integer> l4 = new ArrayList<>();
        l4.add(2);
        map.put(4, l4);*/


        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i=0; i<relations.size()*2; i++) {
            ArrayList<Integer> list = relations.get(i);
            map.put(list.get(0), list.get(1));
            map.put(list.get(1), list.get(0));
        }

        return n-m;
    }

    static int getBeautyNumbers(int a, int b, int n) {
        int sumA = 0;
        int sumB = 0;

        for (int i=1; i<=n; i++) {
            sumA += a;
            sumB += b;
        }

        int count = 0;
        if (sumA == a || sumA == b)
            count++;
        if (sumB == a || sumB == b)
            count++;

        return count;
    }


    static int getMinimumBusCapacity(int n, ArrayList <ArrayList<Integer>> stations) {
        ArrayList<Integer> firstStation = stations.get(0);
        int sum = firstStation.get(0) + firstStation.get(1);

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1;i<n;i++) {
            ArrayList<Integer> station = stations.get(i);
            if (station != null && !station.isEmpty()) {
                sum = sum - station.get(0) + station.get(1);
                list.add(sum);
            }
        }

        if (list.isEmpty())
            return sum;

        return Collections.max(list);
    }

    static PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(10, Collections.<Integer>reverseOrder());

    static void pushToPQueue(int n) {
        priorityQueue.add(n);
    }

    static int diff() {
        int min = Integer.MAX_VALUE;

        Iterator<Integer> iterator = priorityQueue.iterator();
        while (iterator.hasNext()) {
            int val = iterator.next();
            if (min > val)
                min = val;
        }

        int difference = priorityQueue.element() - min;

        priorityQueue.poll();
        priorityQueue.remove(Integer.valueOf(min));

        return difference;
    }

    static int countHigh(){
        if (priorityQueue.size() == 0)
            return -1;

        Integer max = priorityQueue.peek();
        Integer val;
        int count = 0;

        Iterator<Integer> iterator = priorityQueue.iterator();
        while (iterator.hasNext()) {
            int value = iterator.next();

            if (value == max) {
                count++;
            } else {
                break;
            }
        }

        /*while ( (val = priorityQueue.poll()) != null) {
            if (val == max) {
                count++;
            } else {
                break;
            }
        }*/

        return count;

    }



    static int kthLargestSum(int arr[], int n, int k)
    {
        // array to store predix sums
        int sum[] = new int[n + 1];
        sum[0] = 0;
        sum[1] = arr[0];
        for (int i = 2; i <= n; i++)
            sum[i] = sum[i - 1] + arr[i - 1];

        // priority_queue of min heap
        PriorityQueue<Integer> Q = new PriorityQueue<Integer> ();

        // loop to calculate the contigous subarray
        // sum position-wise
        for (int i = 1; i <= n; i++)
        {

            // loop to traverse all positions that
            // form contiguous subarray
            for (int j = i; j <= n; j++)
            {
                // calculates the contiguous subarray
                // sum from j to i index
                int x = sum[j] - sum[i - 1];

                // if queue has less then k elements,
                // then simply push it
                if (Q.size() < k)
                    Q.add(x);

                else
                {
                    // it the min heap has equal to
                    // k elements then just check
                    // if the largest kth element is
                    // smaller than x then insert
                    // else its of no use
                    if (Q.peek() < x)
                    {
                        Q.poll();
                        Q.add(x);
                    }
                }
            }
        }

        // the top element will be then kth
        // largest element
        return Q.poll();
    }



    static int[] getKLargeElements(int[] arr, int k) {
        //Arrays.asList(arr);
        //Arrays.sort(arr, Collections.reverseOrder());

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();


        return null;
    }

    public static int gcd(int n, int m) {
        if (n%m == 0) return m;
        if (n < m) swap(n, m);

        while (m > 0) {
            n = n%m;
            swap(n, m);
        }

        return n;
    }

    private static void swap(int a, int b) {
        b = a + b;
        a = b - a;
        b = b - a;

        System.out.println("a: "+a+" b: "+b);
    }



    public static List<List<String>> getNQueenSquares(int n) {
        if (n<=3)
            return null;

        List<List<String>> mainList = new ArrayList<>();
        for (int i=0; i<n; i++) {

        }

        return null;
    }

}
