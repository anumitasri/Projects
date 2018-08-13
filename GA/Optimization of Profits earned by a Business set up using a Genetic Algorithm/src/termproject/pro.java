/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class pro {
    
    String[] corps = {"42","9","168","56","7","92","4"};   
    Random rnd = new Random();
    int popSize = 600;
    int generation = 25;
    double crossoverRate = 0.6;
    double mutationRate = 0.051;
    
    double[] cardinalityValues;
    double[] sen1MinusSen2Values;
    double[] connectivityValues;
    
    int minValues = 1;
    int maxValues = 1;  
    double[] DNVTSupValues;
    double[] MSRTSupValues;   
    double DNVTRequired = 1495;
    double MSRTRequired = 672;
    
    int NC;
    int LEN;
    int SEN1;
    int SEN2;
    int SCC;
    int RAU;
    int NAI;
    double[] MSRT;
    double[] DNVT;
    
    ArrayList tempArr = new ArrayList();
    public void createPopulation()
    {        
        ArrayList populationArray = new ArrayList();
        double[] fitnessValues = new double[popSize];
        cardinalityValues = new double[popSize];
        sen1MinusSen2Values = new double[popSize];
        connectivityValues = new double[popSize];
        DNVTSupValues = new double[popSize];
        MSRTSupValues = new double[popSize];    
       // maxValues = new double[popSize];
        MSRT = new double[popSize];
        DNVT = new double[popSize];
        int[] counts = new int[100];
            ArrayList newPopulationArray = new ArrayList();

        //for (int t = 0; t < 100; t++) {
            
        //population is successful.
        for (int i = 0; i < popSize; i++) {

            NC = 9;//rnd.nextInt(41) + 1;
            LEN = 0;//rnd.nextInt(8) + 1; 
            
            SEN1 = rnd.nextInt(167) + 1;
            SEN2 = rnd.nextInt(55) + 1;  
           
            SCC = 1;//rnd.nextInt(6) + 1;
            RAU = 27;//rnd.nextInt(91) + 1;
            NAI = 0;//rnd.nextInt(3) + 1;
           
            populationArray.add(NC + "," + LEN + "," + SEN1 + "," + SEN2 + "," + SCC + "," + RAU + "," + NAI);
               
            }
        //System.out.println();
        int count=0;
           for (int p = 0; p < generation; p++) {
                System.out.println("=================");
                System.out.println("Generation: " + p);
             
                fitnessValues = calculateFitness(populationArray, fitnessValues, p);            
                findingSelectionProbforRoulette(fitnessValues, populationArray, p);  
                //tournamentSelection(fitnessValues, populationArray, p);
                for (int i = 0; i < popSize; i++) {
                   System.out.println(populationArray.get(i) + "," + fitnessValues[i]);
                }
                tempArr.removeAll(tempArr);
                System.out.println("=================");
           }
           
          // counts[t] = count;
          
       // }
//        for (int i = 0; i < 100; i++) {
//             System.out.println(counts[i]);
//        }
    }
    
    public boolean isEqual(ArrayList oldPopulation, ArrayList newPopulation)
    {
        int counter = 0;
        for (int i = 0; i < oldPopulation.size(); i++) {
            if(oldPopulation.get(i).equals(newPopulation.get(i)))
            {
                counter++;
                if(counter == oldPopulation.size())
                    return true;
                
            }
        }
        return false;
        //System.out.println();
    }
    
    public ArrayList addToArrayList(ArrayList populationArray)
    {
        ArrayList newPopulationArray = new ArrayList();

        newPopulationArray.clear();
        for (int i = 0; i < populationArray.size(); i++) {
            newPopulationArray.add(populationArray.get(i));
        }
        return newPopulationArray;
    }

    // it is working well..
    public void MSRTSupportTerm(ArrayList populationArray)
    {
        double msrtSupport;

        for (int i = 0; i < popSize; i++) {
            int[] arr = addToArray(populationArray,i);
            MSRT[i] = arr[5] * 25;
        } 
        for (int j = 0; j < MSRT.length; j++) {
       
            if(MSRTRequired < MSRT[j])
            {
                msrtSupport = (MSRTRequired / MSRT[j]);
            }

            else if(MSRTRequired > MSRT[j])
            {
                msrtSupport = 0;
            }
            else
                msrtSupport = 1;

            MSRTSupValues[j] = msrtSupport;  
        }
    }
    
    //it is working well..
    public void DNVTSupportTerm(ArrayList populationArray)
    {
        double dnvtSupport;
        for (int i = 0; i < popSize; i++) {
            int[] arr = addToArray(populationArray,i);
            DNVT[i] = arr[0] * 24 + arr[1] * 176 + arr[2] * 26 + arr[3] * 41;
        } 
        for (int j = 0; j < DNVT.length; j++) {
            
            if(DNVTRequired < DNVT[j])
            {
                dnvtSupport = (DNVTRequired / DNVT[j]);
            }
            
            else if(DNVTRequired > DNVT[j])
                dnvtSupport = 0;

            else 
                dnvtSupport = 1;

            DNVTSupValues[j] = dnvtSupport; 
        }
    }
    
    //it is working well.
    public void sen1sen2Difference(ArrayList populationArray)
    {
        double sen1Difsen2 = 0;
        for (int i = 0; i < popSize; i++) {
            int[] arr = addToArray(populationArray, i);
            if(arr[2] > 3*arr[3])
            {
                if(!(arr[2] + arr[3] < 45 && 2.90 < arr[2] / arr[3] && arr[2] / arr[3] == 3))
                    sen1Difsen2 = ((double)(3 * arr[3]) / (double)arr[2])/2;
                else
                    sen1Difsen2 = ((double)(3 * arr[3]) / (double)arr[2]);
            
            }
            else if(arr[2] <= 3 * arr[3])
            {
                if(!(arr[2] + arr[3] < 45 && 2.90 < arr[2] / arr[3] && arr[2] / arr[3] == 3))
                    sen1Difsen2 = (double)((double)arr[2] / (double)(3 * arr[3]))/2;
                else
                    sen1Difsen2 = (double)((double)arr[2] / (double)(3 * arr[3]));
            }
            sen1MinusSen2Values[i] = sen1Difsen2;
        }
    }
    
//    public void showPopulationArray()
//    {
//        for (int i = 0; i < populationArray.size(); i++) {
//            System.out.println(populationArray.get(i));
//        }
//    }
    
    public double[] calculateFitness(ArrayList populationArray, double[] fitnessValues, int p)
    {
        for (int i = 0; i < popSize; i++) {
            cardinality(populationArray);
            sen1sen2Difference(populationArray); 
            needed(populationArray);            
            MSRTSupportTerm(populationArray);
            DNVTSupportTerm(populationArray); 
            fitnessValues[i] = cardinalityValues[i] * 
                               sen1MinusSen2Values[i] * 
                               connectivityValues[i] * 
                               minValues * 
                               maxValues * 
                               //maxValues[i] * 
                               DNVTSupValues[i] * 
                               MSRTSupValues[i];
        }
        return fitnessValues;
        
    }
    
//    public void showFitnessValues()
//    {
//        for (int i = 0; i < fitnessValues.length; i++) {
//            System.out.println(fitnessValues[i]);
//        }
//    }
    
    public int[] addToArray(ArrayList populationArray, int index)
    {
        int[] arr = new int[7];
        for (int i = 0; i < 7; i++) {
            arr[i] = Integer.parseInt(populationArray.get(index).toString().split(",")[i]);

        }
        return arr;
    }
    
    public void cardinality(ArrayList populationArray)
    {
        double sum;
        double sum2;
        double percentage = 0.142857;
        
        double lastSum;
        for (int i = 0; i < populationArray.size(); i++) {
            lastSum = 0.0;
            int[] components = addToArray(populationArray, i);//splitting the arraylist 
            for (int j = 0; j < 7; j++) {
                sum = components[j];
                sum2 = (Integer.parseInt(corps[j]));
                
                lastSum += (double)((sum/sum2)*percentage);
            }
            
            cardinalityValues[i] = (double)(50/lastSum);
        }
    }
        
    //it is working well.
    public void needed(ArrayList populationArray) // connectivity
    {
        double totalAvailable;
        double availableFromBackbone;
        double needed;
        int X;
        int Y;
        double Z;
        int others;
        for (int i = 0; i < populationArray.size(); i++) {
            int[] components = addToArray(populationArray, i);
            totalAvailable = components[0] * 12;
            X = (components[0] - 1);
            X = remainder(X, 4) + 1;
            
            Y = (components[0] - 1) / 4;
            Z = Y + 1;
            availableFromBackbone = (32*Y) - (X*X) + (13*X);
            others = components[1] * 2 + components[2] * 1 + components[3] * 1 + components[4] * 0 + components[5] * 1 + components[6] * 1;
            needed = totalAvailable - availableFromBackbone + others;
            
//            if(totalAvailable < needed) // || components[0] > 4*Z || components[1] > 1*Z || components[2] > 12*Z || components[3] > 4*Z || components[4] > 1*Z ||components[5] > 9*Z || components[6] > 1* Z)
//            {
//                maxValues[i] = 0;
//            }
            
            
            if(needed - totalAvailable > 12)
                connectivityValues[i] = needed/totalAvailable;
            else
                connectivityValues[i] = 1;
            
            
        }
    }
 
    // calculates mod function
    public int remainder(int num1, int num2)
    {
        int result;
        result = num1%num2;
        return result;
    }
 
    public void tournamentSelection(double[] fitnessValues, ArrayList populationArray, int p)
    {
        ArrayList randomInd = new ArrayList();
        
        for (int i = 0; i < popSize; i++) {
            int rndNumber = rnd.nextInt(popSize - 1);
            int rndNumber2 = rnd.nextInt(popSize - 1);
            
            if(fitnessValues[rndNumber] > fitnessValues[rndNumber2])
                randomInd.add(populationArray.get(rndNumber));
            
            else if(fitnessValues[rndNumber] < fitnessValues[rndNumber2])
                randomInd.add(populationArray.get(rndNumber2));
            
            else 
            {               
                randomInd.add(populationArray.get(rndNumber2));
            }
        }
        
        populationArray.removeAll(populationArray);
        for (int i = 0; i < popSize; i++) {
            populationArray.add(randomInd.get(i).toString());
        }
        
        pairOperation(populationArray, p);
    }
    
    //it is working well..
    public void findingSelectionProbforRoulette(double[] fitnessValues, ArrayList populationArray, int p)
    {
        double sum = 0.0;
        double[] probabilites = new double[popSize];
       
        for (int i = 0; i < popSize; i++) {
            sum = sum + fitnessValues[i];
        }
        
        for (int i = 0; i < popSize; i++) {
            probabilites[i] = fitnessValues[i] / sum;
        }
        
        cumulativeProbabilityForRoulette(probabilites, populationArray, p);
    }
    
    //it is working well..
    public void cumulativeProbabilityForRoulette(double[] selectionProbabilities, ArrayList populationArray, int p)
    {
        double[] cumulativeValues = new double[popSize];
        cumulativeValues[0] = selectionProbabilities[0];
        for (int i = 1; i < selectionProbabilities.length; i++) {
            cumulativeValues[i] = cumulativeValues[i-1] + selectionProbabilities[i];
        }
        
        rouletteWheelSelection(cumulativeValues, populationArray, p);       
    }
    
    public void rouletteWheelSelection(double[] cumulativeOnes, ArrayList populationArray, int p)
    {
        double[] rouletteWheelResults = new double[popSize];
        ArrayList tempPop = new ArrayList();
        double temp;
        for (int i = 0; i < popSize; i++) {
            rouletteWheelResults[i] = Math.random();
        }
        
        for (int i = 0; i < rouletteWheelResults.length; i++) {
            temp = 0;
            for (int j = 0; j < cumulativeOnes.length; j++) {
                if(temp < rouletteWheelResults[i] && rouletteWheelResults[i] < cumulativeOnes[j])
                {                    
                    //populationArray.set(i, populationArray.get(j));
                    tempPop.add(populationArray.get(j));
                    temp = cumulativeOnes[j];
                    break;
                }
                else
                    temp = cumulativeOnes[j];
            }
            
        }

        if(p == 1)
            System.out.println();
        populationArray.removeAll(populationArray);
        for (int i = 0 ; i<popSize;i++){
            populationArray.add(tempPop.get(i));
        }
           
        
        pairOperation(populationArray, p);
        
    }

    public void pairOperation(ArrayList populationArray, int p)
    {
        if(p == 1)
            System.out.println();
        String[] chromosomeOne;
        String[] chromosomeTwo;
        
        int rndNumber1;
        int rndNumber2;
               
        int crossoverProb;
        
        for (int i = 0; i < (popSize/2); i++) {
            rndNumber1 = rnd.nextInt(popSize - 1);
            rndNumber2 = rnd.nextInt(popSize - 1);
            if((rndNumber1 != rndNumber2))
            {
                chromosomeOne = populationArray.get(rndNumber1).toString().split(",");
                chromosomeTwo = populationArray.get(rndNumber2).toString().split(",");
                String ch1 = "";
                String ch2 = "";
                
                for (int j = 0; j < chromosomeOne.length; j++) {
                    if(j != chromosomeOne.length-1)
                    {
                        ch1+=chromosomeOne[j] + ",";
                        ch2+=chromosomeTwo[j] + ",";
                    }
                    else
                    {
                        ch1+=chromosomeOne[j];
                        ch2+=chromosomeTwo[j];
                    }
                }
                crossoverProb = rnd.nextInt(10);
                if(crossoverProb <= crossoverRate * 10)
                {    
                    //onePointCrossover(chromosomeOne, chromosomeTwo)
                    twoPointCrossover(chromosomeOne, chromosomeTwo, populationArray);
                }
                else
                {
                    tempArr.add(ch1);
                    tempArr.add(ch2);
                }
            }
            else
            {                
                i--;
            }
        }
    }
    
    public void twoPointCrossover(String[] chromosomeOne, String[] chromosomeTwo, ArrayList populationArray)
    {
        
        int pivotOne = rnd.nextInt(6);
        int pivotTwo = rnd.nextInt(6);
        int temp;
        String[] tempChrom;
        
        if(pivotOne == pivotTwo)
        {
            while(pivotOne == pivotTwo)
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
        
        int mutProb;
        tempChrom = Arrays.copyOf(chromosomeOne, chromosomeOne.length);
       // tempChrom = chromosomeOne;
        for (int i = pivotOne; i <= pivotTwo; i++) {
            chromosomeOne[i] = chromosomeOne[i].replace(chromosomeOne[i], chromosomeTwo[i]);
            chromosomeTwo[i] = chromosomeTwo[i].replace(chromosomeTwo[i], tempChrom[i]);
        }
     
        String ch1 = "";
        String ch2 = "";
        for (int i = 0; i < chromosomeOne.length; i++) {
            if(i != chromosomeOne.length-1)
            {
                ch1+=chromosomeOne[i] + ",";
                ch2+=chromosomeTwo[i] + ",";
            }
            else
            {
                ch1+=chromosomeOne[i];
                ch2+=chromosomeTwo[i];
            }
        }
        
        tempArr.add(ch1);
        tempArr.add(ch2);
     
        if(tempArr.size() == popSize)
        {
            for (int i = 0; i < popSize; i++) {
                //for (int j = 0; j < 7; j++) {               
                    mutProb = rnd.nextInt(1000);
                    if(mutProb < mutationRate * 1000)
                    {
                        int[] components = addToArray(tempArr, i);

                        int mutOld = components[2];
                        int mutNew = mutOld - 1;
                        components[2] = mutNew;
                        
                        int mutOld2 = components[3];
                        int mutNew2 = mutOld2 - 1;
                        components[3] = mutNew2;
                    }
                //}
            }
            
            populationArray.removeAll(populationArray);
            for (int i = 0; i < popSize; i++) {
               populationArray.add(tempArr.get(i));
            }
            System.out.println();
        }
    }   
}

