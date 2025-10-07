package Coding;

/*
 * 
 * Given a multiset of integers, find the subset of integers that adds to the maximum sum, such that no two numerically consecutive integers (i.e., n and n+1) are in the subset. Return the maximum sum. 
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class MultisetProblem {
    static Map<Integer, Integer> map = new HashMap<>();

    static int recSol(int[] arr, int n){

        if(n<0) return 0;
        if(n==0) return arr[0]*map.get(arr[0]) ;

        if(arr[n]-arr[n-1] == 1){
            return Math.max(recSol(arr, n-2)+(map.get(arr[n])*arr[n]), recSol(arr,n-1));
        }

        return recSol(arr, n-1)+(map.get(arr[n])*arr[n]) ;

    }

    static int dpSol(int[] arr, int n){
        int dp[] = new int[n] ;
        dp[0] = arr[0]*map.get(arr[0]) ;;

        for(int i=1;i<n;i++){
            if(arr[i] - arr[i-1] ==1){
                int dpIminus2 = 0;
                if(i>1) dpIminus2 = dp[i-2] ;
                dp[i] = Math.max(dpIminus2+(map.get(arr[i])*arr[i]), dp[i-1]);
            }
            else{
                dp[i] = dp[i-1]+(map.get(arr[i])*arr[i]) ;
            }
        }
        return dp[n-1];
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 1, 2, 2, 3, 4, 4, 5, 6, 6};
        int n = arr.length ;

        for(int i=0;i<n;i++){
            int val = map.getOrDefault(arr[i], 0);
            map.put(arr[i], val+1) ;
        }

        Set<Integer> keys = map.keySet();
        int newN = keys.size();
        int[] uniqueArr = new int[keys.size()];
        int index = 0;
        for(Integer element : keys) uniqueArr[index++] = element.intValue();

        System.out.println("Using recursive solution : "+recSol(uniqueArr, newN-1));

        System.out.println("-----------");

        System.out.println("Using DP solution : "+dpSol(uniqueArr, newN));


    }
}
