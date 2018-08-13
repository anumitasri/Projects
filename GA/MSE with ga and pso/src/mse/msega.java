/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mse;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Random;
import java.math.*;
import java.util.Arrays;

public class msega {
    private static final int START_SIZE = 100000;                    // Population size at start.
    private static final int MAX_EPOCHS = 100000;                  // Arbitrary number of test cycles.
    private static final double MATING_PROBABILITY = 0.7;        // Probability of two chromosomes mating. Range: 0.0 < MATING_PROBABILITY < 1.0
    private static final double MUTATION_RATE = 0.001;           // Mutation Rate. Range: 0.0 < MUTATION_RATE < 1.0
    private static final int MIN_SELECT = 10;                    // Minimum parents allowed for selection.
    private static final int MAX_SELECT = 50;                    // Maximum parents allowed for selection. Range: MIN_SELECT < MAX_SELECT < START_SIZE
    private static final int OFFSPRING_PER_GENERATION = 100000;      // New offspring created per generation. Range: 0 < OFFSPRING_PER_GENERATION < MAX_SELECT.
    private static final int MINIMUM_SHUFFLES = 8;               // For randomizing starting chromosomes
    private static final int MAXIMUM_SHUFFLES = 20;
    private static final int PBC_MAX = 4;                        // Maximum Position-Based Crossover points. Range: 0 < PBC_MAX < 8 (> 8 isn't good).
    
    private static final int MAX_LENGTH = 10;                    // chess board width.

    private static int epoch = 0;
    private static int childCount = 0;
    private static int nextMutation = 0;                         // For scheduling mutations.
    private static int mutations = 0;
     
    Random rnd = new Random();
    int popSize = 200;
    int generation = 50;
    double crossoverRate = 0.8;
    double mutationRate = 0.03;
    ArrayList populationArray = new ArrayList();
    ArrayList newPopulationArray = new ArrayList();
   
    double[] cardinalityValues;
    double[] sen1MinusSen2Values;
    double[] connectivityValues;
    int minValues = 1;
    int maxValues = 1;    
    double[] DNVTSupValues;
    double[] MSRTSupValues; 
    
    double[] fitnessValues;
    double DNVTRequired = 1495;
    double MSRTRequired = 672;
    
    ArrayList tempArr = new ArrayList();
    public void createPopulation()
 {       
 int popSize = 0;
        Chromosome thisChromo = null;
        boolean done = false;

        initializeChromosomes();
        mutations = 0;
        nextMutation = getRandomNumber(0, (int)Math.round(1.0 / MUTATION_RATE));
        
        while(!done)
        {
            popSize = population.size();
            for(int i = 0; i < popSize; i++)
            {
                thisChromo = population.get(i);
                if((thisChromo.conflicts() == 0) || epoch == MAX_EPOCHS){
                    done = true;
                }
            }
            
            calculateFitness();
            
            rouletteSelection();
            
            mating();

            prepNextEpoch();
            
            epoch++;
            // This is here simply to show the runtime status.
            System.out.println("Epoch: " + epoch);
        }
        
        System.out.println("done.");
        
        if(epoch != MAX_EPOCHS){
            popSize = population.size();
            for(int i = 0; i < popSize; i++)
            {
                thisChromo = population.get(i);
                if(thisChromo.conflicts() == 0){
                    printbestSolution(thisChromo);
                }
            }
        }
        System.out.println("Completed " + epoch + " epochs.");
        System.out.println("Encountered " + mutations + " mutations in " + childCount + " offspring.");
        return;
    }

    
    
    public void showPopulationArray()
    {
        for (int i = 0; i < populationArray.size(); i++) {
            System.out.println(populationArray.get(i));
        }
    }
    
    public void calculateFitness(ArrayList population)
    {        
        for (int i = 0; i < population.size(); i++) {
            
        }
        
        //showFitnessValues();
    }
    
    public void showFitnessValues()
    {
        for (int i = 0; i < fitnessValues.length; i++) {
            System.out.println(fitnessValues[i]);
        }
    }
    
    public int[] addToArray(ArrayList population, int index)
    {
        int[] arr = new int[7];
        for (int i = 0; i < 7; i++) {
            arr[i] = Integer.parseInt(populationArray.get(index).toString().split(",")[i]);

        }
        return arr;
    }
    
   
        
    //it is working well.
    
    
    // calculates mod function
    public int remainder(int num1, int num2)
    {
        int result = 0;
        result = num1%num2;
        return result;
    }
 
    //it is working well..
    public void findingSelectionProbforRoulette()
    {
        double sum = 0.0;
        double[] probabilites = new double[popSize];
       
        for (int i = 0; i < popSize; i++) {
            sum = sum + fitnessValues[i];
        }
        
        for (int i = 0; i < popSize; i++) {
            probabilites[i] = fitnessValues[i] / sum;
        }
        
        cumulativeProbabilityForRoulette(probabilites);
    }
    
    //it is working well..
    public void cumulativeProbabilityForRoulette(double[] selectionProbabilities)
    {
        double[] cumulativeValues = new double[popSize];
        cumulativeValues[0] = selectionProbabilities[0];
        for (int i = 1; i < selectionProbabilities.length; i++) {
            cumulativeValues[i] = cumulativeValues[i-1] + selectionProbabilities[i];
        }
        
        rouletteWheelSelection(cumulativeValues);       
    }
    
    public void rouletteWheelSelection(double[] cumulativeOnes)
    {
        double[] rouletteWheelResults = new double[popSize];
        double temp = 0;
        for (int i = 0; i < popSize; i++) {
            rouletteWheelResults[i] = Math.random();
        }
        
        for (int i = 0; i < rouletteWheelResults.length; i++) {
            temp = 0;
            for (int j = 0; j < cumulativeOnes.length; j++) {
                if(temp < rouletteWheelResults[i] && rouletteWheelResults[i] < cumulativeOnes[j])
                {                    
                    //newPopulationArray.add(i, populationArray.get(j));
                    populationArray.set(i, populationArray.get(j));
                    temp = cumulativeOnes[j];
                    break;
                }
                else
                    temp = cumulativeOnes[j];
            }
        }
        
        pairOperation();
        
    }

    public void pairOperation()
    {
        Random rnd = new Random();
        String[] chromosomeOne = {};
        String[] chromosomeTwo = {};
        
        int rndNumber1 = rnd.nextInt(popSize - 1);
        int rndNumber2 = rnd.nextInt(popSize - 1);
               
        int crossoverProb = 0;
        
        for (int i = 0; i < (popSize/2); i++) {
            rndNumber1 = rnd.nextInt(popSize - 1);
            rndNumber2 = rnd.nextInt(popSize - 1);
           if((rndNumber1 != rndNumber2))
            {                   
               // chromosomeOne = newPopulationArray.get(rndNumber1).toString().split(",");
                //chromosomeTwo = newPopulationArray.get(rndNumber2).toString().split(",");
                chromosomeOne = populationArray.get(rndNumber1).toString().split(",");
                chromosomeTwo = populationArray.get(rndNumber2).toString().split(",");

                crossoverProb = rnd.nextInt(10);
                if(crossoverProb <= crossoverRate)
                {    
                    //onePointCrossover(chromosomeOne, chromosomeTwo)
                    twoPointCrossover(chromosomeOne, chromosomeTwo);
                }
                else
                {
                    tempArr.add(chromosomeOne);
                    tempArr.add(chromosomeTwo);
                }
            }
            else
            {                
                i--;
            }
        }
    }
    
    public void twoPointCrossover(String[] chromosomeOne, String[] chromosomeTwo)
    {
        Random rnd = new Random();
        int pivotOne = rnd.nextInt(6);
        int pivotTwo = rnd.nextInt(6);
        int temp = 0;
        String[] tempChrom = {};
        
        if(pivotOne == pivotTwo)
        {
            while(pivotOne != pivotTwo)
            {
                pivotOne = rnd.nextInt(6);
                pivotTwo = rnd.nextInt(6);
            }
        }
        
        else if(pivotOne > pivotTwo)
        {
            temp = pivotOne;
            pivotOne = pivotTwo;
            pivotTwo = temp;   
        }
        
        int t=0;
        int mutProb = 0;
        tempChrom = chromosomeOne;
        for (int i = pivotOne; i <= pivotTwo; i++) {
            chromosomeOne[i] = chromosomeOne[i].replace(chromosomeOne[i], chromosomeTwo[i]);
            chromosomeTwo[i] = chromosomeTwo[i].replace(chromosomeTwo[i], tempChrom[i]);
        }
     
        tempArr.add(chromosomeOne);
        tempArr.add(chromosomeTwo);
     
        if(tempArr.size() == popSize)
        {            
            for (int i = 0; i < popSize; i++) {
                for (int j = 0; j < 7; j++) {               
                    mutProb = rnd.nextInt(1000);
                    if(mutProb < mutationRate)
                    {
                        StringBuilder tempStr = new StringBuilder((String) tempArr.get(i));
                        char mutOld = tempStr.charAt(j);                                
                        char mutNew = doMut(tempStr.charAt(j));
                        
                        tempStr.setCharAt(j, mutNew);
                        tempArr.set(i, tempStr.toString());
                    }
                }
            }
            
            for (int i = 0; i < popSize; i++) {
               populationArray.set(i, tempArr.get(i));
            }
            
            
            
//            for (int i = 0; i < newPopulationArray.size(); i++) {
//                populationArray.set(i, newPopulationArray.get(i));
//            }
        }
    }
    
    public char doMut(char mutChar)
    {
        if(mutChar == '1')
            mutChar = '0';
        else if (mutChar == '0')
            mutChar = '1';
        return mutChar;       
    }    
}

public void computeConflicts()
        {
            int x = 0;
            int y = 0;
            int tempx = 0;
            int tempy = 0;
            String board[][] = new String[MAX_LENGTH][MAX_LENGTH];
            int conflicts = 0;
            int dx[] = new int[] {-1, 1, -1, 1};
            int dy[] = new int[] {-1, 1, 1, -1};
            boolean done = false;

            // Clear the board.
            for(int i = 0; i < MAX_LENGTH; i++)
            {
                for(int j = 0; j < MAX_LENGTH; j++)
                {
                    board[i][j] = "";
                }
            }

            for(int i = 0; i < MAX_LENGTH; i++)
            {
                board[i][this.mData[i]] = "Q";
            }

            // Walk through each of the Queens and compute the number of conflicts.
            for(int i = 0; i < MAX_LENGTH; i++)
            {
                x = i;
                y = this.mData[i];

                // Check diagonals.
                for(int j = 0; j <= 3; j++)
                {
                    tempx = x;
                    tempy = y;
                    done = false;
                    while(!done)
                    {
                        tempx += dx[j];
                        tempy += dy[j];
                        if((tempx < 0 || tempx >= MAX_LENGTH) || (tempy < 0 || tempy >= MAX_LENGTH)){
                            done = true;
                        }else{
                            if(board[tempx][tempy].compareToIgnoreCase("Q") == 0){
                                conflicts++;
                            }
                        }
                    }
                }
            }

            this.mConflicts = conflicts;
        }
        
        public void conflicts(int value)
        {
            this.mConflicts = value;
            return;
        }
        
        public int conflicts()
        {
            return this.mConflicts;
        }