package project1;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
 
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

 

public class Reducer1 extends Reducer<Text, IntWritable, Text, IntWritable> {
 
    public Reducer1() {}
 
    
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
 
        int a = 0;
        
        for (IntWritable value : values)
        {
            a += value.get();
        }
       
        context.write(key, new IntWritable(a));
    
    }
    
}
