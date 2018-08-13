package project1;

import java.io.IOException;
import java.util.Map;
import java.text.DecimalFormat;
import org.apache.hadoop.io.Text;

import java.util.HashMap;

import org.apache.hadoop.mapreduce.Reducer;

public class Reducer3 extends Reducer<Text, Text, Text, Text> {
    
 
    private static final DecimalFormat DF = new DecimalFormat("###.########");
 
    public Reducer3() {}
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException
    {
        int numDocs = Integer.parseInt(context.getJobName());
                int numDocsK = 0;
        
        Map<String, String>tempFreq = new HashMap<String, String>();
        
        for (Text value : values)
        {
            String[] docFreq = value.toString().split("=");
            
            numDocsK++;
           tempFreq.put(docFreq[0], docFreq[1]);
        }
        
        for (String doc :tempFreq.keySet())
        {
            String[] freqandTW =tempFreq.get(doc).split("/");
 
            
            double tf = Double.valueOf(Double.valueOf(freqandTW[0])/ Double.valueOf(freqandTW[1]));
            double idf = (double) numDocs / (double) numDocsK;
            double tfIdf = numDocs == numDocsK ?
                    tf : tf * Math.log10(idf);
 
            context.write(new Text(key + "@" + doc), new Text("[" + numDocsK + "/"+ numDocs + " , " + freqandTW[0] + "/" + freqandTW[1] + " , " + DF.format(tfIdf) + "]"));

                   
        }
        
    }
    
}
