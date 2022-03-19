package algorithms;

/**
 * Implementation of Bubble sort algorithm
 */
public class BubbleSort extends SortAlgorithm {

    @Override
    public void sort(int[] arr, SortCallback update) {
        
        int[] tempArray = new int[arr.length];

        for (int c = 0; c < tempArray.length; c++) {
            tempArray[c] = arr[c];
        }
     

        int n = tempArray.length;
        int temp = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (tempArray[j - 1] > tempArray[j]) {
                    // swap elements
                    temp = tempArray[j - 1];
                    tempArray[j - 1] = tempArray[j];
                    tempArray[j] = temp;

                    int[] stepArray = new int[tempArray.length];

                    for (int b = 0; b < tempArray.length; b++) {
                        stepArray[b] = tempArray[b];
                    }


                    update.update(new int[]{j}, stepArray);

                    /*
                     * System.out.println(Arrays.toString(tempArray));
                     * steps.add(tempArray);
                     * System.out.println(steps.toString());
                     */

                }

            }
        }

       
        
    }
    
}
