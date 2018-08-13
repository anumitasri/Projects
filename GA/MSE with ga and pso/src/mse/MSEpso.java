/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mse;
import java.util.ArrayList;
import java.util.Random;
import java.math.*;
import java.util.Arrays;
/**
 *
 * @author anumitasrivastava
 */
public class MSEpso {
    String[] corps = new String[7];   
    Random rnd = new Random();
    int popSize = 25;
    int generation = 30;
    double crossoverRate = 0.6;
    double mutationRate = 0.003;
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
        corps[0] = "42";//NC
        corps[1] = "9";//LEN
        corps[2] = "168";//SEN1
        corps[3] = "56";//SEN2
        corps[4] = "7";//SCC
        corps[5] = "92";//RAU
        corps[6] = "4";//NAI 
            cardinalityValues = new double[popSize];
            sen1MinusSen2Values = new double[popSize];
            connectivityValues = new double[popSize];
            DNVTSupValues = new double[popSize];
            MSRTSupValues = new double[popSize];    
            fitnessValues = new double[popSize];
            int NC = 0;
            int LEN = 0;
            int SEN1 = 0;
            int SEN2 = 0;
            int SCC = 0;
            int RAU = 0;
            int NAI = 0;
            double MSRT = 0;
            double DNVT = 0;
            for (int i = 0; i < popSize; i++) {

//             NC = rnd.nextInt(41) + 1;
//                LEN = rnd.nextInt(8) + 1; 
//                SEN1 = rnd.nextInt(167) + 1;
//                SEN2 = rnd.nextInt(55) + 1;        
//                SCC = rnd.nextInt(6) + 1;
//                RAU = rnd.nextInt(91) + 1;
//                NAI = rnd.nextInt(3) + 1;
                NC = 9;
                LEN = 1;
               SEN1 = 30;
                SEN2 = 9;
                SCC = 1;
                RAU = 27;
                NAI = 1;
                MSRT = 25 * RAU;
                DNVT = NC * 24 + LEN * 176 + SEN1 * 26 + SEN2 * 41;

                sen1sen2Difference(SEN1, SEN2, i); 
                MSRTSupportTerm(MSRT, i);
                DNVTSupportTerm(DNVT, i);      
                populationArray.add(NC + "," + LEN + "," + SEN1 + "," + SEN2 + "," + SCC + "," + RAU + "," + NAI);

            }
           for (int p = 0; p < generation; p++) {
            
        
            showPopulationArray();
            calculateFitness(populationArray);  
           // findingSelectionProbforRoulette();
//            cardinalityValues = new double[popSize];
//            sen1MinusSen2Values = new double[popSize];
//            connectivityValues = new double[popSize];
//            DNVTSupValues = new double[popSize];
//            MSRTSupValues = new double[popSize];    
//            fitnessValues = new double[popSize];
           
            System.out.println("changed population");
            showPopulationArray();
            showFitnessValues();        
            //populationArray.clear();
            
           }
    }

    public void MSRTSupportTerm(double MSRT, int i)
    {
      //  MSRTSupValues = new double[popSize];
        double msrtSupport = 1.0;
        if(MSRTRequired <= MSRT)
        {
            msrtSupport = (MSRTRequired / MSRT);
        }

        else 
            msrtSupport = 1;
        
        MSRTSupValues[i] = msrtSupport;        
    }
    
    //it is working well..
    public void DNVTSupportTerm(double DNVT, int i)
    {
        double dnvtSupport = 1.0;
        
        if(DNVTRequired <= DNVT)
        {
            dnvtSupport = (DNVTRequired / DNVT);
        }

        else 
            dnvtSupport = 1;
        
        DNVTSupValues[i] = dnvtSupport;        
    }
    
    //it is working well.
    public void sen1sen2Difference(int sen1, int sen2, int i)
    {
        double sen1Difsen2 = 0;

        if(sen1 > 3*sen2)
            sen1Difsen2 = (double)(3 * sen2) / (double)sen1;
            
        else if(sen1 < 3 * sen2)
            sen1Difsen2 = (double)((double)sen1 / (double)(3 * sen2));
        
        sen1MinusSen2Values[i] = sen1Difsen2;        
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
            cardinality();
            needed();
            fitnessValues[i] = cardinalityValues[i] * 
                               sen1MinusSen2Values[i] * 
                               1 * 
                               minValues * 
                               maxValues * 
                               DNVTSupValues[i] * 
                               MSRTSupValues[i];
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
    
    public void cardinality()
    {
        double sum = 0.0;
        double sum2 = 0.0;
        double percentage = 0.142857;
        
        double lastSum = 0.0;
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
    public void needed() // connectivity
    {
        double totalAvailable = 0;
        double availableFromBackbone = 0;
        int X = 0;
        int Y = 0;
        double Z = 0;
        int others = 0;
        for (int i = 0; i < populationArray.size(); i++) {
            int[] components = addToArray(populationArray, i);
            totalAvailable = components[0] * 12;
            X = (components[0] - 1);
            X = remainder(X, 4) + 1;
            
            Y = (components[0] - 1) / 4;
            Z = Y + 1;
            availableFromBackbone = (32*Y) - (X*X) + (13*X);
            others = components[1] * 2 + components[2] * 1 + components[3] * 1 + components[4] * 0 + components[5] * 1 + components[6] * 1;
            connectivityValues[i] = totalAvailable - availableFromBackbone + others;
        }
    }
    
    // calculates mod function
    public int remainder(int num1, int num2)
    {
        int result = 0;
        result = num1%num2;
        return result;
    }
 
       
}

