package project1;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.BasicConfigurator;
 

public class Driver extends Configured implements Tool {
	
    static int i=1;
 
    private static final String op1 = "/home/cloudera/Desktop/output1.txt";

    private static final String ip1 = "/home/cloudera/Desktop/Fw%3a_/input1.txt";
     private static final String op2 = "/home/cloudera/Desktop/output2.txt";
 
    
    private static final String ip2 = "/home/cloudera/Desktop/output1.txt";
    private static final String op3 = "/home/cloudera/Desktop/final.txt";
 
   
    private static final String ip3 = "/home/cloudera/Desktop/output2.txt";
 
    public int run(String[] args) throws Exception {
        
        if(i==1){
            
        Configuration conf = getConf();
        Job job = new Job(conf, "Word Frequence In Document");
 
        job.setJarByClass(Driver.class);
            
        job.setMapperClass(Mapper1.class);
            
        job.setReducerClass(Reducer1.class);
            
        job.setCombinerClass(Reducer1.class);
 
        job.setOutputKeyClass(Text.class);
            
        job.setOutputValueClass(IntWritable.class);
 
        FileInputFormat.addInputPath(job, new Path(ip1));
            
        FileOutputFormat.setOutputPath(job, new Path(op1));
 
        return job.waitForCompletion(true)?0 :1;
        }
        
        if(i==2){
            Configuration conf = getConf();
            
        Job job = new Job(conf, "Words Counts");
 
        job.setJarByClass(Driver.class);
            
        job.setMapperClass(Mapper2.class);
            
        job.setReducerClass(Reducer2.class);
 
        job.setOutputKeyClass(Text.class);
            
        job.setOutputValueClass(Text.class);
 
        FileInputFormat.addInputPath(job, new Path(ip2));
            
        FileOutputFormat.setOutputPath(job, new Path(op2));
 
        return job.waitForCompletion(true)?0 :1;
            
        }
        
        if(i==3){
            Configuration conf = getConf();
            
        Job job = new Job(conf, "Word in Corpus, TF-IDF");
 
        job.setJarByClass(Driver.class);
            
        job.setMapperClass(Mapper3.class);
            
        job.setReducerClass(Reducer3.class);
 
        job.setOutputKeyClass(Text.class);
            
        job.setOutputValueClass(Text.class);
 
        FileInputFormat.addInputPath(job, new Path(ip3));
            
        FileOutputFormat.setOutputPath(job, new Path(op3));
 
 
        return job.waitForCompletion(true)? 0 :1 ;
        }
        return 1;
    }
 
    public static void main(String[] args) throws Exception {
    	BasicConfigurator.configure();
        
        int res1 = ToolRunner.run(new Configuration(), new Driver(), args);
        
        i++;
        int res2 = ToolRunner.run(new Configuration(), new Driver(), args);
        i++;
         int res3 = ToolRunner.run(new Configuration(), new Driver(), args);
        
         if(res1==0 && res2==0 && res3==0)
        System.exit(0);
    }
}
