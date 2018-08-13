package midterm_ci;

/**
 *
 * @author anumitasrivastava
 */

import java.awt.font.NumericShaper.Range;
import java.text.DecimalFormat;
import java.util.*;
import java.lang.Math;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

public class test {
        
    int popSize = 2000;
    
    ArrayList<String> arr = new ArrayList<String>();
    String[] newIndividiuals = new String[popSize];
    ArrayList allFitness = new ArrayList();
    ArrayList lastIndividiuals = new ArrayList();
    double crossOverRate = 0.6;
    double mutationRate = 0.1;
    int generation = 20;
    int cnt=0;
    
    Random rnd = new Random();
  // double coolConstant = 0.00004768372718899898;
    public void createPopulation()
    {
        ArrayList news = new ArrayList();
        for (int i = 0; i < 100; i++) {            
        
            String[] binaryPopulation = new String[popSize];
            double[] fitnessValues = new double[popSize];
   
            long range = rnd.nextInt(70) + (-30);
            long[] decimalPopulation = new long[popSize];
            for (int l = 0; l < popSize; l++) {
                decimalPopulation[l] = (long)(rnd.nextDouble()*range);
            }

            binaryPopulation = convertDecimaltoBinary(decimalPopulation);     
            makingFortyFourBits(binaryPopulation);        

            for (int s = 0; s < generation; s++) {
                
                fitnessValues = calculateFitnessForeachChromosome(binaryPopulation);
                cnt++;
//sortFitnesses(fitnessValues);
                findingSelectionProbforRoulette(fitnessValues, binaryPopulation);                
                //tournamentSelection(binaryPopulation, fitnessValues);
             
                arr.removeAll(arr);    

            }            
            
            allFitness.add(fitnessValues[popSize-1]);
            lastIndividiuals.add(binaryPopulation[popSize - 1]);
            
            for (int k = 0; k < allFitness.size(); k++) {
                //System.out.print(lastIndividiuals.get(k));
                news.add(allFitness.get(k));
                double[] values = binToDec(lastIndividiuals.get(k).toString());
         
                System.out.println(  allFitness.get(k));
             //   System.out.println(lastIndividiuals.get(k));
              //  System.out.println(values[0] + "," + values[1]);
                System.out.println(cnt);
            }
            System.out.println();
            
            allFitness.removeAll(allFitness);
            lastIndividiuals.removeAll(lastIndividiuals);
            
        }
        Collections.sort(news);
        System.out.println(news.get(news.size()-1));
        
    }
    
    public double[] calculateFitnessForeachChromosome(String[] binaryPopulation)
    {
        double[] fitnessValues = new double[popSize];
        String[] stringRealXs = new String[popSize];
        String[] stringRealYs = new String[popSize];

        for (int j = 0; j < popSize; j++) {
            stringRealXs[j] = binaryPopulation[j].substring(0, 21);
            stringRealYs[j] = binaryPopulation[j].substring(22, 44);                
        }

        double[] decimalRealXs = binToDecimal(stringRealXs);
        double[] decimalRealYs = binToDecimal(stringRealYs);

//        for (int j = 0; j < popSize; j++) {
//           
//            decimalRealXs[j] = (decimalRealXs[j] * coolConstant - 100);
//            decimalRealYs[j] = (decimalRealYs[j] * coolConstant - 100);
//        }      
        
        //calculating fitness values for real values..
        for (int i = 0; i < popSize; i++) {
////        double sqrtPart = Math.sqrt(decimalRealXs[i]*decimalRealXs[i] + decimalRealYs[i]*decimalRealYs[i]);
////            double sinPart = Math.sin(sqrtPart);
////            double powPart = Math.pow(sinPart, 2);
////            double top = powPart - 0.5;
////            double bottom = (Math.pow((1.0 + 0.001*(decimalRealXs[i]*decimalRealXs[i]+decimalRealYs[i]*decimalRealYs[i])), 2));
           
           double part1 =(Math.abs(decimalRealXs[i]))- (Math.abs(decimalRealYs[i]));
           
           double part2 = 1 + (Math.abs(Math.sin(Math.abs(decimalRealXs[i])*3.14)));
           
      //     double part3 = Math.abs(Math.sin(decimalRealYs[i])*3.14);
      //(abs(x)+abs(y))*(1+abs(sin(abs(x)*pi))+abs(sin(abs(y)*pi)))
           
//double part3 = (Math.abs(decimalRealXs[i])+ Math.abs(decimalRealYs[i]))*(1+ Math.abs(Math.sin(Math.abs(decimalRealXs[i])*3.14))+ Math.abs(Math.sin(Math.abs(decimalRealYs[i])*3.14)));
//f(x, y) = (|x| + |y|) · (1 + |sin(3 · |x| · π)| + |sin(3 · |y| · π)|)
double part3 = (Math.abs(decimalRealXs[i])+ Math.abs(decimalRealYs[i]))*(1+ Math.abs(Math.sin(3*Math.abs(decimalRealXs[i])*3.14))+ Math.abs(Math.sin(3*Math.abs(decimalRealYs[i])*3.14)));
fitnessValues[i]= part3;

            
            //fitnessValues[i] = (0.5 + top/bottom);
        }
        return fitnessValues;
        
    }  
    
    public double[] binToDec(String binary)
    {
        double[] values = new double[2];
        String binaryX = binary.substring(0, 22);
        String binaryY = binary.substring(22, 44);
        double decimalX = Long.parseLong(binaryX, 2);
        double decimalY = Long.parseLong(binaryY, 2);
        values[0] = decimalX;
        values[1] = decimalY;
        return values;
    }
    
    public double[] binToDecimal(String[] binarys)
    {
        double[] decimals = new double[popSize];
        for (int i = 0; i < popSize; i++) {
            decimals[i] = Long.parseLong(binarys[i],2);
        }
        return decimals;
        
    }
    
    public void findingSelectionProbforRoulette(double[] fitnessValues, String[] binaryPopulation)
    {
        double sum = 0.0;
        double[] probabilites = new double[popSize];
       
        for (int i = 0; i < popSize; i++) {
            sum = sum + fitnessValues[i];
        }
        
        for (int i = 0; i < popSize; i++) {
            probabilites[i] = fitnessValues[i] / sum;
        }
        
        cumulativeProbabilityForRoulette(probabilites, binaryPopulation);
    }
    
    public void cumulativeProbabilityForRoulette(double[] selectionProbabilities, String[] binaryPopulation)
    {
        double[] cumulativeValues = new double[popSize];
        cumulativeValues[0] = selectionProbabilities[0];
        for (int i = 1; i < selectionProbabilities.length; i++) {
            cumulativeValues[i] = cumulativeValues[i-1] + selectionProbabilities[i];
        }
        
        rouletteWheelSelection(cumulativeValues, binaryPopulation);       
    }

    public void rouletteWheelSelection(double[] cumulativeOnes, String[] binaryPopulation)
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
                    //newIndividiuals[i] = binaryPopulation[j];
                    binaryPopulation[i] = binaryPopulation[i].replace(binaryPopulation[i], binaryPopulation[j]);
                    temp = cumulativeOnes[j];
                    break;
                }
                else
                    temp = cumulativeOnes[j];
            }
        }
         
        pairOperation(binaryPopulation);
    }
 
    
    
    public void tournamentSelection(String[] binaryPopulation, double[] fitnessValues)
    {
        ArrayList randomInd = new ArrayList();
        
        for (int i = 0; i < 150; i++) {
            int rndNumber = rnd.nextInt(149);
            int rndNumber2 = rnd.nextInt(149);
            
            if(fitnessValues[rndNumber] > fitnessValues[rndNumber2])
                randomInd.add(binaryPopulation[rndNumber]);
            
            else if(fitnessValues[rndNumber] < fitnessValues[rndNumber2])
                randomInd.add(binaryPopulation[rndNumber2]);
            
            else 
            {               
                randomInd.add(binaryPopulation[rndNumber]);
            }
        }
        
        for (int i = 0; i < popSize; i++) {
            binaryPopulation[i] = randomInd.get(i).toString();
        }
        
        pairOperation(binaryPopulation);
    }   

    public void pairOperation(String[] binaryPopulation)
    {
        String chromosomeOne;
        String chromosomeTwo;
        int rndNumber1 = rnd.nextInt(popSize - 1);
        int rndNumber2 = rnd.nextInt(popSize - 1);
               
        int crossoverProb = 0;
        
        for (int i = 0; i < (popSize/2); i++) {
            rndNumber1 = rnd.nextInt(popSize - 1);
            rndNumber2 = rnd.nextInt(popSize - 1);
           if((rndNumber1 != rndNumber2))
            {                
                chromosomeOne = binaryPopulation[rndNumber1];
                chromosomeTwo = binaryPopulation[rndNumber2];
                crossoverProb = rnd.nextInt(10);
                if(crossoverProb <= crossOverRate)
                {    
                    //onePointCrossover(chromosomeOne, chromosomeTwo)
                    //twoPointCrossover(chromosomeOne, chromosomeTwo, binaryPopulation);
                    uniformCrossover(chromosomeTwo, chromosomeOne, binaryPopulation);
                }
                else
                {
                    arr.add(chromosomeOne);
                    arr.add(chromosomeTwo);
                }
            }
            else
            {                
                i--;
            }
        }
    }
    
    public void uniformCrossover(String chromosomeZero, String chromosomeOne, String[] binaryPopulation)
    {
        String childZero = "";
        String childOne = "";
        String temp;
        int[] crossoverMask = new int[chromosomeOne.length()];
        for (int i = 0; i < crossoverMask.length; i++) {
            crossoverMask[i] = rnd.nextInt(2);
        }
        
        for (int i = 0; i < crossoverMask.length; i++) {
            if(crossoverMask[i] == 0)
            {
                childZero += chromosomeZero.charAt(i);
                childOne += chromosomeOne.charAt(i);
            }
            else if(crossoverMask[i] == 1)
            {
                childZero += chromosomeOne.charAt(i);
                childOne += chromosomeZero.charAt(i);
            }           
        }
        
        arr.add(childZero);
        arr.add(childOne);
        int mutProb = 0;
        if(arr.size() == popSize)
        {            
            for (int i = 0; i < popSize; i++) {
                for (int j = 0; j < childOne.length(); j++) {               
                    mutProb = rnd.nextInt(1000);
                    if(mutProb < mutationRate)
                    {
                        StringBuilder tempStr = new StringBuilder(arr.get(i));
                        char mutOld = tempStr.charAt(j);                                
                        char mutNew = doMut(tempStr.charAt(j));
                        
                        tempStr.setCharAt(j, mutNew);
                        arr.set(i, tempStr.toString());
                    }                   
                }              
            }
            
            for (int i = 0; i < popSize; i++) {
                binaryPopulation[i] = arr.get(i);
            }
        }
        
    }
    
    public void twoPointCrossover(String chromosomeOne, String chromosomeTwo, String[] binaryPopulation)
    {
        int pivotOne = rnd.nextInt(43);
        int pivotTwo = rnd.nextInt(43);
        int temp = 0;
        String tempChrom = "";
        
        if(pivotOne == pivotTwo)
        {
            while(pivotOne != pivotTwo)
            {
                pivotOne = rnd.nextInt(43);
                pivotTwo = rnd.nextInt(43);
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
        chromosomeOne = chromosomeOne.replace(chromosomeOne.substring(pivotOne, pivotTwo), chromosomeTwo.substring(pivotOne, pivotTwo));
        chromosomeTwo = chromosomeTwo.replace(chromosomeTwo.substring(pivotOne, pivotTwo), tempChrom.substring(pivotOne, pivotTwo));
        arr.add(chromosomeOne);
        arr.add(chromosomeTwo);
     
        if(arr.size() == popSize)
        {            
            for (int i = 0; i < popSize; i++) {
                for (int j = 0; j < 44; j++) {               
                    mutProb = rnd.nextInt(1000);
                    if(mutProb < mutationRate)
                    {
                        StringBuilder tempStr = new StringBuilder(arr.get(i));
                        char mutOld = tempStr.charAt(j);                                
                        char mutNew = doMut(tempStr.charAt(j));
                        
                        tempStr.setCharAt(j, mutNew);
                        arr.set(i, tempStr.toString());
                    }                   
                }              
            }
            
            for (int i = 0; i < popSize; i++) {
                binaryPopulation[i] = arr.get(i);
            }
                            
        }              
    }
//    public void onePointCrossover(String chromosomeOne, String chromosomeTwo, String[] newIndividuals)
//    {
//        Random rnd = new Random();
//        int pivot = rnd.nextInt(24);
//        int t=0;
//        int mutProb = 0;
//        
//        chromosomeOne = chromosomeOne.replace(chromosomeOne.substring(pivot, 24), chromosomeTwo.substring(pivot, 24));
//        chromosomeTwo = chromosomeTwo.replace(chromosomeTwo.substring(pivot, 24), chromosomeOne.substring(pivot, 24));
//        arr.add(chromosomeOne);
//        arr.add(chromosomeTwo);
//        
//        
//       // System.out.println(chromosomeOne +", "+ chromosomeTwo);
//        
//        if(arr.size() == 150)
//        {            
//            for (int i = 0; i < 150; i++) {
//                for (int j = 0; j < 25; j++) {               
//                    mutProb = rnd.nextInt(1000);
//                    if(mutProb < 3)
//                    {
//                       // doMut(Character.toString(arr.get(i).charAt(j)));
//                        //arr.get(i).charAt(j) = temp;                       
//                    }                    
//                    
//                }
//                arr.set(i,arr.get(i));
//                //arr.set(i, doMutation(arr.get(i)));
//            }
//           
//            for (int i = 0; i < 150; i++) {
//                newIndividuals[i] = arr.get(i);
//            }           
//        }      
//    }
    
    public char doMut(char mutChar)
    {
        if(mutChar == '1')
            mutChar = '0';
        else if (mutChar == '0')
            mutChar = '1';
        return mutChar;        
    }
    
//    public String doMutation(String mutChromosome)
//    {
//        Random rnd = new Random();
//        int startRandom = rnd.nextInt(24);
//        int endRandom = rnd.nextInt(24);
//        int tempRandom = 0;
//        
//        if(!(startRandom < endRandom))
//        {
//            tempRandom = startRandom;
//            startRandom = endRandom;
//            endRandom = tempRandom;
//        }
//            
//        for (int i = startRandom; i < endRandom; i++) {
//            if(mutChromosome.charAt(i) == '1')
//                mutChromosome = mutChromosome.replace(mutChromosome.charAt(i), '0');
//            else
//                mutChromosome = mutChromosome.replace(mutChromosome.charAt(i), '1');
//        }      
//        
//        return mutChromosome;
//    }
    
    
    
    /**
     * Converting decimal numbers to binary.
     * @param decimalPopulation
     * @return 
     */
    public String[] convertDecimaltoBinary(long[] decimalPopulation)
    {
        String[] binaryPopulation = new String[popSize];
        for (int i = 0; i < decimalPopulation.length; i++) {
             binaryPopulation[i] = Long.toBinaryString(decimalPopulation[i]);
        }
        return binaryPopulation;
    }
    
    /**
     * I completed the size of bits to 10.
     * @param binaryPopulation
     */
    public void makingFortyFourBits(String[] binaryPopulation)
    {        
        int addingValue = 0;
        for (int i = 0; i < binaryPopulation.length; i++) {          
            binaryPopulation[i] = leftPad(binaryPopulation[i], 44, '0');           
        }
    }

 
    public static String leftPad(String originalString, int length,
         char padCharacter) {
      String paddedString = originalString;
      while (paddedString.length() < length) {
         paddedString = padCharacter + paddedString;
      }
      return paddedString;
   }
    
} 