package algorithms;

/**
 * Implementation of Insertion sort algorithm
 */
public class InsertionSort extends SortAlgorithm {

    @Override
    public void sort(int[] arr, SortCallback update) {
        int n = arr.length;
        for(int i = 1; i < n; ++i){
            int key = arr[i];
            int j = i - 1;
 
            while(j >= 0 && arr[j] > key){
                arr[j + 1] = arr[j];
                j = j-1;
                
            }
            arr[j+1] = key;

            update.update(new int[]{j}, arr.clone());           
        }
    }
    
}
