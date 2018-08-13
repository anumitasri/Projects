
package midterm_ci_pso;

import java.util.ArrayList;
import java.util.Random;
import java.math.*;
import java.util.Arrays;

public class midtermPso {
    
    Random rnd = new Random();
    int popSize = 30;
    int generation = 150; 
    int cnt=0;
    
    ArrayList pBestPositions;    
    ArrayList pBestValues;
    ArrayList gBestPositions;
    ArrayList bestValues;
    
    int lowVelocity = -4;
    int highVelocity = 4;
    
    int lowXLocation = -4;
    int highXLocation = 4;
    int lowYLocation = -4;
    int highYLocation = 4;
    int C1 = 2;
    int C2 = 2;
    
    double velocityX;
    double velocityY;
    double locationX;
    double locationY;
    
    double wInertia;
    
    double coolConstant = 0.00004768372718899898;
    long range = 17592186044416L;

    ArrayList tempArr = new ArrayList();
    public void createPopulation()
    {
        double[] fitnessValues = new double[popSize];
       // long decimalPopulation;
         
        String binaryChromosome;
        ArrayList populationArray = new ArrayList();        
        pBestValues = new ArrayList();       
        pBestPositions = new ArrayList();
        gBestPositions = new ArrayList();
        bestValues = new ArrayList();
        //for (int r = 0; r < 200; r++) {           
        
            for (int iteration = 0; iteration < generation; iteration++) {
                for (int i = 0; i < popSize; i++) {            

                    long decimalPopulation = (long)(rnd.nextDouble()*range);                

                    binaryChromosome = convertDecimaltoBinary(decimalPopulation);     
                    binaryChromosome = makingFortyFourBits(binaryChromosome); 
                    int x = binaryChromosome.length();
                    velocityX = rnd.nextInt(highVelocity - lowVelocity + 1) - highVelocity;
                    velocityY = rnd.nextInt(highVelocity - lowVelocity + 1) - highVelocity;

                    locationX = rnd.nextInt(highXLocation - lowXLocation + 1) - highXLocation;
                    locationY = rnd.nextInt(highYLocation - lowYLocation + 1) - highYLocation;

                    populationArray.add(binaryChromosome + ","+ velocityX + "," + velocityY + "," + locationX + "," + locationY);

                }           

                fitnessValues = calculateFitnessForeachChromosome(populationArray);
                cnt++;
                pBestandGBest(fitnessValues, populationArray, iteration);
            }
        //}
    }
    
    public double[] calculateFitnessForeachChromosome(ArrayList populationArray)
    {
        double[] fitnessValues = new double[popSize];
        String[] stringRealXs = new String[popSize];
        String[] stringRealYs = new String[popSize];

        for (int j = 0; j < popSize; j++) {
            stringRealXs[j] = populationArray.get(j).toString().substring(0, 22);
            stringRealYs[j] = populationArray.get(j).toString().substring(22, 44);                
        }

        double[] decimalRealXs = binToDecimal(stringRealXs);
        double[] decimalRealYs = binToDecimal(stringRealYs);

        for (int j = 0; j < popSize; j++) {
            decimalRealXs[j] = (decimalRealXs[j] * coolConstant - 100);
            decimalRealYs[j] = (decimalRealYs[j] * coolConstant - 100);
        }      
        
        //calculating fitness values for real values..
        for (int i = 0; i < popSize; i++) {
            double sqrtPart = Math.sqrt(decimalRealXs[i]*decimalRealXs[i] + decimalRealYs[i]*decimalRealYs[i]);
            double sinPart = Math.sin(sqrtPart);
            double powPart = Math.pow(sinPart, 2);
            double top = powPart - 0.5;
            double bottom = (Math.pow((1.0 + 0.001*(decimalRealXs[i]*decimalRealXs[i]+decimalRealYs[i]*decimalRealYs[i])), 2));
            fitnessValues[i] = (0.5 + top/bottom);
        }
        //System.out.println();
        return fitnessValues;
        
    }

    public double[] binToDecimal(String[] binarys)
    {
        double[] decimals = new double[popSize];
        for (int i = 0; i < popSize; i++) {
            decimals[i] = Long.parseLong(binarys[i],2);
        }
        return decimals;        
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
    
    public void VidNext(ArrayList populationArray, int t, String[] pBestPoss, double[] gBestPos, double gBest, String bestValue)//, double[] pBestValues)
    {        
        double[] values = binToDec(bestValue);
        double vNextX = 0;
        double vNextY = 0;
        double locX = 0;
        double locY = 0;
        for (int i = 0; i < popSize; i++) {
            String[] arr = addToArray(populationArray,i);
            
            double r1 = Math.random();
            double r2 = Math.random();
            
            vNextX = Double.parseDouble(arr[1]) + C1 * ((Double.parseDouble(pBestPoss[i].split(",")[0])) - Double.parseDouble(arr[3])) * r1 + C2 * (gBestPos[0] - Double.parseDouble(arr[3])) * r2;
            vNextY = Double.parseDouble(arr[2]) + C1 * ((Double.parseDouble(pBestPoss[i].split(",")[1])) - Double.parseDouble(arr[4])) * r1 + C2 * (gBestPos[1] - Double.parseDouble(arr[4])) * r2;  

            if(vNextX > highVelocity)
                vNextX = highVelocity;
            if(vNextY > highVelocity)
                vNextY = highVelocity;
           
            locX = Double.parseDouble(arr[3]) + vNextX;
            locY = Double.parseDouble(arr[4]) + vNextY;
            String[] x = populationArray.get(i).toString().split(",");

            x[1] = Double.toString(vNextX);
            x[2] = Double.toString(vNextY);
            x[3] = Double.toString(locX);
            x[4] = Double.toString(locY);            
            String newStr = x[0] + "," + x[1] + "," + x[2] + "," + x[3] + "," +x[4];
            populationArray.set(i, newStr);   
        }
        
       
            System.out.println("ITERATION " + t + ": ");
            System.out.println("     Best x : " + gBestPos[0]);
            System.out.println("     Best y : " + gBestPos[1]);
            System.out.println("     Value  : " + gBest + "------>" + values[0] + "," + values[1]);
            System.out.println(cnt);
        
        
        populationArray.clear();
    }

    public void pBestandGBest(double[] fitnessValues, ArrayList populationArray, int iteration)
    {        
        double[] pBest = new double[popSize];
        String[] pBestPoss = new String[popSize];
        double gBest = 0;
        double[] gBestPos = new double[2];
        String bestValue="";
        if(iteration==0)
        {
            pBestValues.add(pBest);
            pBestPositions.add(pBestPoss);
            gBestPositions.add(gBestPos);
            bestValues.add(bestValue);
        }
        
        for (int i = 0; i < populationArray.size(); i++) {
            String[] arr = addToArray(populationArray, i);
            
            if(pBestHistory(i, pBestValues, fitnessValues))
            {
                pBest[i] = fitnessValues[i];
                pBestPoss[i] = (arr[3] + "," + arr[4]);
            }
            
            else
            {
                pBest[i] = ((double[])pBestValues.get(iteration-1))[i];
                pBestPoss[i] = ((String[])pBestPositions.get(iteration-1))[i];
            }
            
            if(pBest[i] > gBest)
            {
                gBest = pBest[i];
                gBestPos[0] = Double.parseDouble(arr[3]);
                gBestPos[1] = Double.parseDouble(arr[4]);
                bestValue = arr[0];
            }
        }
        
        if(iteration != 0)
        {
            pBestValues.add(pBest);
            pBestPositions.add(pBestPoss);
            gBestPositions.add(gBestPos);
            //bestValues.add(bestValue);
        }

    //    System.out.println();
        VidNext(populationArray, iteration, (String[])pBestPositions.get(iteration), (double[])gBestPositions.get(iteration), gBest, bestValue);//, (double[])pBestValues.get(iteration));
        System.out.println();
        //populationArray.clear();
        
    }
    
    public String[] addToArray(ArrayList populationArray, int index)
    {
        String[] arr = new String[5];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (populationArray.get(index).toString().split(",")[i]);

        }
        return arr;
    }
    
    public void display(ArrayList populationArray)
    {
        for (int i = 0; i < popSize; i++) {
            System.out.println(populationArray.get(i));
        }
    }
    
    public boolean pBestHistory(int i, ArrayList pBestVals, double[] fitnessValues)
    {
        
        int cnt = 0;        
        for (int j = 0; j < pBestVals.size(); j++) {
            double[] pBests = (double[])pBestVals.get(j);
            if(fitnessValues[i] >= pBests[i])
            {
                cnt++;
                if(cnt == pBestVals.size())
                {
                   return true;
                }
            }            
        }
        return false;
    }
    
    public String convertDecimaltoBinary(long decimalPopulation)
    {
        String binaryChromosome;
        binaryChromosome = Long.toBinaryString(decimalPopulation);
        
        return binaryChromosome;
    }
    
    /**
     * I completed the size of bits to 10.
     * @param binaryChromosome
     */
    public String makingFortyFourBits(String binaryChromosome)
    {        
        binaryChromosome = leftPad(binaryChromosome, 44, '0');           
        return binaryChromosome;
    }
    
    public static String leftPad(String originalString, int length, char padCharacter) {
      String paddedString = originalString;
      while (paddedString.length() < length) {
         paddedString = padCharacter + paddedString;
      }
      return paddedString;
   }
    
    public boolean isEqual(ArrayList oldPopulation, ArrayList newPopulation)
    {
        int counter = 0;
        int repeatability = 0;
        for (int i = 0; i < oldPopulation.size(); i++) {
            if(oldPopulation.get(i) == newPopulation.get(i))
            {
                counter++;
                if(counter == oldPopulation.size())
                    return true;
                
            }
        }
        return false;
        //System.out.println();
    }

    
       
 
}