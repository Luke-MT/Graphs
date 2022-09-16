package upo.progdin20038688;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DynamicProgrammingTest {

	@Test
	void Knapsack01test() {
		int [] values1 = {12, 6, 20, 1};
		int [] weights1 = {2, 7, 6, 2}; 		
		boolean [] k = DynamicProgramming.getKnapsack01(weights1, values1, 10);
		boolean [] expectedK1 = {false,true,false,true,true};
		for(int i = 0; i<k.length; i++) {
			Assertions.assertEquals(k[i], expectedK1[i]);
		}
		
		int [] values2 = {12, 6, 4, 1};
		int [] weights2 = {2, 7, 6, 4}; 		
		k = DynamicProgramming.getKnapsack01(weights2, values2, 10);
		boolean [] expectedK2 = {false,true,true,false,false};
		for(int i = 0; i<k.length; i++) {
			Assertions.assertEquals(k[i], expectedK2[i]);
		}
		
		
	}

}
