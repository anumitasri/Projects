package project1;


import org.apache.hadoop.mapreduce.Mapper;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import java.io.IOException;

public class Mapper2 extends Mapper<LongWritable, Text, Text, Text> {
 
    public Mapper2() {}
 
   
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
    {
        
        String[] wordCount = value.toString().split("\t");
        
        String[] w = wordCount[0].split("@");
       
        context.write(new Text(w[1]), new Text(w[0] + "=" + wordCount[1]));
    }
}
