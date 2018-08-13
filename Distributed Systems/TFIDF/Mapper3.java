package project1;

import java.io.IOException;
import java.util.regex.Matcher;

import java.util.HashSet;
import java.util.Set;

import java.util.regex.Pattern;

import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.io.Text;
import java.util.regex.Matcher;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;




public class Mapper3 extends Mapper<LongWritable, Text, Text, Text> {
 
    public Mapper3() {}
 
 
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        
        String[] wordCount = value.toString().split("\t");
        
        String[] w = wordCount[0].split("@");
        
        context.write(new Text(w[0]), new Text(w[1] + "=" + wordCount[1]));
        
    }
}
