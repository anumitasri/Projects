import java.util.Arrays;
import java.util.Random;

public class RollerTrack {

	public static int survivalOfTheFittest(track[] population) // Gets the fitness to
														// be killed
	{

		int bestfitness = -1, largefitness = -1;
		double smallest = 1000000, largest = 0;
		for (int i = 0; i < population.length; i++) {
			if (population[i].fitnessCal(1) > largest) {
				largefitness = i;
				largest = population[i].fitnessCal(1);

			}

		}

		System.out.println("The best fitness is :" + largefitness + " The best fitness value :" + largest);
		return largefitness;

	}
	public static int jackTheRipper(track[] population)  // Gets the fitness to be killed
	{
		
		int bestfitness=-1,largefitness=-1;
		double smallest=1000000,largest=0;
		for(int i=0;i<population.length;i++)
		{
			if(population[i].fitnessCal(1)<smallest)
			{
			bestfitness=i;
			smallest=population[i].fitnessCal(1);
			}

		}

		System.out.println("The worst fitness is :"+bestfitness+"The worst fitness value :"+smallest);
		return bestfitness;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Random rand = new Random();
		int KIA;

		track[] population = new track[10];

		for (int i = 0; i < 10; i++) {
			population[i] = new track();
			population[i].createTrack();
			System.out.println(Arrays.toString(population[i].trackSeq));
		}
		System.out.println("-------------------------");
		/*
		 * KIA=jackTheRipper(population); int n = rand.nextInt(10);
		 * //System.out.println(population[KIA].fitnessCal(1));
		 * population[KIA].mutate();
		 * //System.out.println(population[KIA].fitnessCal(1));
		 * population[KIA].crossover(population[n].trackSeq);
		 */

		int loop = 100, chosen1, probailityIntiger, probailityIntiger2;
		while (loop >= 0) {
			chosen1 = jackTheRipper(population);
			probailityIntiger = rand.nextInt(10);
			probailityIntiger2 = rand.nextInt(100);
			if (probailityIntiger2 >= 0 && probailityIntiger < 90) {
				population[chosen1].mutate();
				population[chosen1].crossover(population[probailityIntiger].trackSeq); // Cross
			} else {
				population[chosen1].crossover(population[probailityIntiger].trackSeq); // Cross
																						// mutation

			}
			if (probailityIntiger >= 0 && probailityIntiger < 80) {
				population[chosen1].mutate();
			}
			loop--;
			
		}
		int kingOftheHill=survivalOfTheFittest(population);
		population[kingOftheHill].print(population[kingOftheHill].trackSeq.length);
		System.out.println(Arrays.toString(population[kingOftheHill].trackSeq));
	
	}

}
