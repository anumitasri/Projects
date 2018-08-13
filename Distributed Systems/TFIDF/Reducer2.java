package project1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sonali
 */

import java.util.Map;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.mapreduce.Reducer;
 

public class Reducer2 extends Reducer<Text, Text, Text, Text> {
 
    
    public Reducer2() {}
 
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException
    {
        int wordsum = 0;
        
        Map<String, Integer> temp = new HashMap<String, Integer>();
        
        for (Text value : values) {
            
            String[] wordCount = value.toString().split("=");
            
            temp.put(wordCount[0], Integer.valueOf(wordCounter[1]));
            
            wordsum += Integer.parseInt(value.toString().split("=")[1]);
            
        }
        for (String wordKey : temp.keySet()) {
            
            context.write(new Text(wordKey + "@" + key.toString()), new Text(temp.get(wordKey) + "/"
                    + wordsum));
        }
    }
}
