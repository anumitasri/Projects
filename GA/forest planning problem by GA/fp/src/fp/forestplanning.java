/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.text.DecimalFormat;
import java.util.*;
import java.lang.Math;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author anumitasrivastava
 */
public class forestplanning {
    
    
    
    
    String[] adjacency = {"1,6,34,48",
"2,3,66,67",
"3,2,4",
"4,3,67",
"5,6,12,15,27,34",
"6,1,5,17,48,50",
"7,67,72",
"8,9,69,71",
"9,8",
"10,11,49",
"11,10,13,19,49,53",
"12,5,18,27",
"13,11,51",
"14,15,18,30,31,32,45,46",
"15,5,14,16,17,18,20,31,50,55",
"16,15,20,49",
"17,6,15,50",
"18,12,14,15",
"19,11,20,49,54",
"20,15,16,19,54",
"21,22,24",
"22,21,25",
"23,31,41,55",
"24,21,25,61",
"25,22,24,63",
"26,39,42,44",
"27,5,12,28",
"28,27",
"29,34,35",
"30,14,37,45",
"31,14,15,23,38,41,43,55",
"32,14",
"33,34",
"34,1,5,29,33,47,48",
"35,29,36",
"36,35",
"37,30",
"38,31,43",
"39,26",
"40,42",
"41,23,31,43,55,56",
"42,26,40,44",
"43,31,38,41",
"44,26,42",
"45,14,30",
"46,14",
"47,34",
"48,1,6,34",
"49,10,11,16,19",
"50,6,15,17",
"51,13,53",
"52,57",
"53,11,51,54",
"54,19,20,53",
"55,15,23,31,41,59",
"56,41,58,60,62",
"57,52,59",
"58,56",
"59,55,57",
"60,56,62,64",
"61,24,63,65",
"62,56,60",
"63,25,61",
"64,60",
"65,61",
"66,2,73",
"67,2,4,7,72",
"68,70",
"69,8,71",
"70,68",
"71,8,69",
"72,7,67",
"73,66"};
  
    String[] volume ={

"1,5.69,29,43,55",
"2,28.128,60,69,78",
"3,29.941,60,69,78",
"4,17.068,8,17,29",
"5,37.208,3,8,17",
"6,41.896,3,8,17",
"7,19.977,43,55,65",
"8,17.68,0,3,8",
"9,9.049,0,3,8",
"10,12.928,92,92,92",
"11,60.687,92,92,92",
"12,63.081,55,65,74",
"13,19.648,0,5,12",
"14,55.53,17,29,43",
"15,63.084,23,36,49",
"16,5.604,23,36,49",
"17,2.896,23,36,49",
"18,21.42,0,3,8",
"19,35.477,0,3,8",
"20,23.226,0,3,8",
"21,11.43,0,5,12",
"22,22.526,0,5,12",
"23,7.38,29,43,55",
"24,19.599,23,36,49",
"25,2.932,23,36,49",
"26,41.835,36,49,60",
"27,45.981,29,43,55",
"28,.696,69,78,84",
"29,11.996,69,78,84",
"30,69.151,17,29,43",
"31,66.031,17,29,43",
"32,33.174,17,29,43",
"33,50.021,43,55,65",
"34,85.919,29,43,55",
"35,55.73,29,43,55",
"36,0.85,43,55,65",
"37,15.047,65,74,81",
"38,13.989,65,74,81",
"39,52.743,65,74,81",
"40,3.883,0,5,12",
"41,60.805,0,5,12",
"42,29.699,3,8,17",
"43,16.726,3,8,17",
"44,34.11,92,92,92",
"45,14.699,92,92,92",
"46,46.272,92,92,92",
"47,19.541,49,60,69",
"48,62.531,49,60,69",
"49,39.273,92,92,92",
"50,47.416,49,60,69",
"51,34.353,3,8,17",
"52,3.165,3,8,17",
"53,41.985,60,69,78",
"54,37.832,3,8,17",
"55,44.699,29,43,55",
"56,39.582,29,43,55",
"57,67.783,60,69,78",
"58,4.285,60,69,78",
"59,27.88,3,8,17",
"60,39.044,43,55,65",
"61,48.645,43,55,65",
"62,19.469,43,55,65",
"63,1.908,43,55,65",
"64,.633,12,23,36",
"65,41.532,12,23,36",
"66,45.98,8,17,29",
"67,82.437,8,17,29",
"68,16.315,23,36,49",
"69,48.27,23,36,49",
"70,.315,49,60,69",
"71,3.211,49,60,69",
"72,8.533,49,60,69",
"73,5.826,49,60,69"};
    int popSize = 10000;    
    ArrayList<double[]> tempArr = new ArrayList<>();
    double crossOverRate = 0.8;
    double mutationRate = 0.3;
    int generation = 100;
    Random rnd = new Random();
    double T = 34467;
    int cnt=0;
    
    
    public int remainder(int num1, int num2)
    {
        int result = 0;
        result = num1%num2;
        return result;
    }
    public void createPopulation()
    {       
        ArrayList<double[]> populationArray = new ArrayList<>();
                    ArrayList<int[]> schedules = new ArrayList<>();

        double[] fitnessValues = new double[popSize];
        
        for (int i = 0; i < popSize; i++) {   
            double[] representation = new double[219];
            for (int j = 0; j < representation.length; j++) {
                    representation[j] = Math.random();
            }
           
            populationArray.add(representation);
        }
        
        for (int s = 0; s < generation; s++)
        {
            schedules = getSchedules(populationArray, schedules); 
            fitnessValues = calculateFitnessForeachChromosome(schedules,fitnessValues, populationArray);
            //populationArray = findingSelectionProbforRoulette(populationArray, fitnessValues);  
            populationArray = selectIndividual(populationArray, fitnessValues);
            tempArr.removeAll(tempArr);
            System.out.println();
            System.out.println("Generation: " + s);
            System.out.println();
                for (int j = 0; j < 73; j++) {
                    System.out.print((schedules.get(popSize-1))[j]);
                }
                System.out.println("---->" + fitnessValues[popSize - 1] + cnt);
                System.out.println();
            
            schedules.removeAll(schedules);
        }
    }
    
    public ArrayList getSchedules(ArrayList<double[]> populationArray, ArrayList<int[]> schedules)
    {
        int[] indexes = new int[219];
        for (int k = 0; k < popSize; k++) {
                int[] schedule = new int[73];
                double[] tempRep = new double[populationArray.get(k).length];
                System.arraycopy(populationArray.get(k), 0, tempRep, 0, populationArray.get(k).length);
                for (int j = 0; j < indexes.length; j++) {
                        double max = 0;
                        int index = 0;
                    for (int i = 0; i < tempRep.length; i++) {
                        if(tempRep[i] > max)
                        {
                            max = tempRep[i];
                            index = i;
                        }
                        if(i == tempRep.length-1)
                        {
                            tempRep[index] = 0;
                            indexes[j] = index;
                        }
                    }
                }
                for (int i = 0; i < schedule.length; i++) {
                    int rem = remainder(indexes[i], 3);
                    int timePeriod = rem + 1;
                    int stand = (indexes[i] / 3);
                    schedule[stand] = timePeriod;

                }
                for (int i = 0; i < adjacency.length; i++) {
                    int[] arr = array(i, adjacency); //1,6,34,48
                    for (int j = 1; j < arr.length; j++) {
                        if(schedule[arr[j] - 1] == schedule[i])
                        {
                            schedule[arr[j] - 1] = 0;

                        }
                    }
                }
                schedules.add(schedule);

            }
        return schedules;
    }
    
    public double[] calculateFitnessForeachChromosome(ArrayList<int[]> schedules, double[] fitnessValues, ArrayList<double[]> populationArray)
    { 
        
        for (int k = 0; k < schedules.size(); k++) {
            int[] schedule = schedules.get(k);            
            double H1 = 0;
            double H2 = 0;
            double H3 = 0;
            double result1 = 0;
            double result2 = 0;
            double result3 = 0;
            double penaltyResult = 0;
            double totalResults = 0;
            for (int j = 0; j < schedule.length; j++) {
                double[] arr = array2(j,volume);

                if(schedule[j] == 1)
                {
                    H1 += arr[2] * arr[1];
                }
                else if(schedule[j] == 2)
                {
                    H2 += arr[3] * arr[1];
                }

                else if(schedule[j] == 3)
                {
                    H3 += arr[4] * arr[1];
                }
                else
                {
                    penaltyResult = 0;
                }                       
            }
            result1 = (H1 - T) * (H1 - T);
            result2 = (H2 - T) * (H2 - T);
            result3 = (H3 - T) * (H3 - T);
            totalResults = result1 + result2 + result3 + penaltyResult;
            fitnessValues[k] = totalResults;
            cnt++;
        }
        return fitnessValues;

    }   
    
    public int[] array(int index, String[] arry)
    {        
        String[] arr = (arry[index].split(","));
        int[] arrr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrr[i] = Integer.parseInt(arr[i]);
        }
        return arrr;
    }
    
    public double[] array2(int index, String[] arry)
    {
        String[] arr = (arry[index].split(","));
        double[] arrr = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrr[i] = Double.parseDouble(arr[i]);
        }
        return arrr;
    }
    
    public ArrayList findingSelectionProbforRoulette(ArrayList<double[]> populationArray, double[] fitnessValues)
    {
        double sum = 0.0;
        double[] probabilites = new double[popSize];
       
        for (int i = 0; i < popSize; i++) {
            sum = sum + fitnessValues[i];
        }
        
        for (int i = 0; i < popSize; i++) {
            probabilites[i] = fitnessValues[i] / sum;
        }
        
        populationArray = rouletteWheelSelection(probabilites, populationArray);
        return populationArray;
    }
    
    public ArrayList rouletteWheelSelection(double[] probabilities, ArrayList<double[]> populationArray)
    {
        double rndNumb = Math.random();
        double sum = 0;
        int k = 0;
        for (int i = 0; i < popSize; i++) {
           sum = sum + probabilities[i];
           if(rndNumb > sum)
           {
               populationArray.set(k, populationArray.get(i));
               k++;
           }
        }
        //System.out.println();
        populationArray = pairOperation(populationArray);
        return populationArray;
    }

    public ArrayList selectIndividual(ArrayList<double[]> populationArray, double[] fitnessValues) 
    {  
        int tournamentSize = 6;  
        ArrayList<double[]> randomInd = new ArrayList<>();

        for (int p = 0; p < popSize; p++) {       
            ArrayList<double[]> tournament = new ArrayList<>();  
            double[] tournamentFitness = new double[tournamentSize];  
            for(int i = 0; i < tournamentSize; i++) {  
                int index = (int) (Math.random() * populationArray.size());  
                tournament.add(populationArray.get(index));  
                tournamentFitness[i] = fitnessValues[index];  
            }  

            double[] bestIndividual = tournament.get(0);  
            double bestFitness = tournamentFitness[0];  
            for(int i = 1; i < tournamentSize; i++)  
                if(tournamentFitness[i] < bestFitness) {  
                    bestIndividual = tournament.get(i);  
                    bestFitness = tournamentFitness[i];  
                }
            randomInd.add(bestIndividual);

        }
        System.out.println();
        for (int i = 0; i < popSize; i++) {
            populationArray.set(i,randomInd.get(i));
        }
        populationArray = pairOperation(populationArray);

        return populationArray;  
    }  
    
    public ArrayList pairOperation(ArrayList<double[]> populationArray)
    {
        double[] chromosomeOne;
        double[] chromosomeTwo;
        int rndNumber1;
        int rndNumber2;
               
        int crossoverProb = 0;
        
        for (int i = 0; i < (popSize/2); i++) {
            rndNumber1 = rnd.nextInt(popSize - 1);
            rndNumber2 = rnd.nextInt(popSize - 1);
           if((rndNumber1 != rndNumber2))
            {                
                chromosomeOne = populationArray.get(rndNumber1);
                chromosomeTwo = populationArray.get(rndNumber2);
                crossoverProb = rnd.nextInt(10);
                if(crossoverProb <= crossOverRate)
                {    
                    populationArray = twoPointCrossover(chromosomeOne, chromosomeTwo, populationArray);
                    //uniformCrossover(chromosomeTwo, chromosomeOne);
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
        
        return populationArray;
    }
    
//    public void uniformCrossover(String[] chromosomeZero, String[] chromosomeOne)
//    {
//        String childZero = "";
//        String childOne = "";
//        String temp;
//        int[] crossoverMask = new int[chromosomeOne.length];
//        for (int i = 0; i < crossoverMask.length; i++) {
//            crossoverMask[i] = rnd.nextInt(2);
//        }
//        
//        for (int i = 0; i < crossoverMask.length; i++) {
//            if(crossoverMask[i] == 0)
//            {
//                childZero += chromosomeZero.charAt(i);
//                childOne += chromosomeOne.charAt(i);
//            }
//            else if(crossoverMask[i] == 1)
//            {
//                childZero += chromosomeOne.charAt(i);
//                childOne += chromosomeZero.charAt(i);
//            }           
//        }
//        
//        arr.add(childZero);
//        arr.add(childOne);
//        int mutProb = 0;
//        if(arr.size() == popSize)
//        {            
//            for (int i = 0; i < popSize; i++) {
//                for (int j = 0; j < childOne.length(); j++) {               
//                    mutProb = rnd.nextInt(1000);
//                    if(mutProb < mutationRate)
//                    {
//                        StringBuilder tempStr = new StringBuilder(arr.get(i));
//                        char mutOld = tempStr.charAt(j);                                
//                        char mutNew = doMut(tempStr.charAt(j));
//                        
//                        tempStr.setCharAt(j, mutNew);
//                        arr.set(i, tempStr.toString());
//                    }                   
//                }              
//            }
//            
//            for (int i = 0; i < popSize; i++) {
//                binaryPopulation[i] = arr.get(i);
//            }
//        }
//        
//    }
    
    public ArrayList twoPointCrossover(double[] chromosomeOne, double[] chromosomeTwo, ArrayList<double[]> populationArray)
    {
        int pivotOne = rnd.nextInt(219);
        int pivotTwo = rnd.nextInt(219);
        int temp = 0;
        double[] tempChrom = {};
        
        if(pivotOne == pivotTwo)
        {
            while(pivotOne != pivotTwo)
            {
                pivotOne = rnd.nextInt(219);
                pivotTwo = rnd.nextInt(219);
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
            chromosomeOne[i] = chromosomeTwo[i];
            chromosomeTwo[i] = tempChrom[i];
     
        }
        tempArr.add(chromosomeOne);
        tempArr.add(chromosomeTwo);
     
        if(tempArr.size() == popSize)
        {            
            for (int i = 0; i < popSize; i++) {
                for (int j = 0; j < 219; j++) {               
                    mutProb = rnd.nextInt(1000);
                    if(mutProb < mutationRate)
                    {
                        double[] tempDoub = tempArr.get(i);
                        double mutOld = tempDoub[j];                                
                        double mutNew = doMut(tempDoub[j]);
                        
                        tempDoub[j] = mutNew;
                    }
                }
            }
            
            for (int i = 0; i < popSize; i++) {
                populationArray.set(i, tempArr.get(i));
            }
        }
        
        return populationArray;
    }

    public double doMut(double mutChar)
    {
        double newElement = Math.random();
        mutChar = newElement;
        return mutChar;        
    }
    
} 
