package algorithms;


/**
 * Implementation of selection sort algorithm
 */
public class SelectionSort extends SortAlgorithm {

    @Override
    public void sort(int[] arr, SortCallback update) {

        int n = arr.length;

        for (int i = 0; i < n-1; i++)
        {
            int min_idx = i;
            for (int j = i+1; j < n; j++){
                if (arr[j] < arr[min_idx]){
                   min_idx = j;
                  // highlights.add(new int[]{j});
                    }
                }
                   

            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
            int[] stepArray = arr.clone();

            update.update(new int[]{i}, stepArray);          

        }
        
    }

}
