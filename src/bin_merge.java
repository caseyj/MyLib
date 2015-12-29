/**
 * 
 */

/**
 * @author Casey
 *
 */
public class bin_merge {

	
	/**
	 * 
	 * @param sortable
	 * @param start
	 * @param end
	 * @return
	 */
	public static int[] mergeSort(int[] sortable, int start, int end){
		if(end-start < 2){
			int[] returner = new int[1];
			returner[0] = sortable[start];
			return returner;
		}
		int splitPoint = (int) Math.floor((start+end)/2);
		int[] A = mergeSort(sortable, start, splitPoint);
		int[] B = mergeSort(sortable, splitPoint+1, end);
		return merge(A,B);
	}
	/**
	 * 
	 * @param listA
	 * @param listB
	 * @return
	 */
	public static int[] merge(int[] listA, int[] listB){
		int[] returner = new int[listA.length+listB.length];
		int counter = 0;
		int AC= 0, BC = 0;
		while(AC<listA.length && BC<listB.length){
			if(listA[AC]<=listB[BC]){
				returner[counter] = listA[AC];
				AC++;
			}else{
				returner[counter] = listB[BC];
				BC++;
			}
			counter++;
		}
		for(int i = AC; i<listA.length; i++){
			returner[counter] = listA[i];
			counter++;
		}
		for(int i = BC; i<listB.length; i++){
			returner[counter] = listB[i];
			counter++;
		}
		return returner;
	}
	
	/**
	 * 
	 * @param sorted
	 * @param search
	 * @param start
	 * @param end
	 * @return index of search value or -1 if it does not exist
	 */
	public static int binSearch(int[] sorted, int search, int start, int end){
		if(end-start < 2){
			if(sorted[start]==search){
				return start;
			}
			else{
				return -1;
			}
		}
		int middle = (int) Math.floor((end+start)/2);
		if(search<sorted[middle]){
			return binSearch(sorted, search, start, middle);
		}
		if(search==sorted[middle]){
			return middle;
		}
		else{
			return binSearch(sorted, search, middle+1, end);
		}
	}
	
	
	
	
}
