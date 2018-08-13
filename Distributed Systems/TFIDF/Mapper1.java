package project1;

import java.util.Set;
import java.util.regex.Matcher;

import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Pattern;
import org.apache.hadoop.io.Text;
 import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.lib.input.FileSplit;
 
public class Mapper1 extends Mapper<LongWritable, Text, Text, IntWritable> {
 
    public Mapper1() {}
 
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        Pattern p = Pattern.compile("\\w+");
        Matcher m = p.matcher(value.toString());
        String fName = ((FileSplit) context.getInputSplit()).getPath().getName();
        StringBuilder v = new StringBuilder();
        while (m.find()) {
            String matchedKey = m.group().toLowerCase();
            if (!Character.isLetter(matchedKey.charAt(0)) || matchedKey.contains("_")|| Character.isDigit(matchedKey.charAt(0))) {
                continue;
            }
            v.append(matchedKey);
            v.append("@");
            v.append(fName);
            context.write(new Text(v.toString()), new IntWritable(1));
        }
    }
}
