import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;



class Result {

    /*
     * Complete the 'optimalPoint' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY magic
     *  2. INTEGER_ARRAY dist
     */

    public static int optimalPoint(List<Integer> magic, List<Integer> dist) {
        // Write your code here
        int mSize = magic.size();
        int dSize = dist.size();
        int nSource = 0;
        int index = 0;

        if( (mSize >= 1 && mSize <= 100000) && (dSize >= 1 && dSize <= 100000) ){
            List<Integer> startIndexArray = startIndexArray(magic, dist);

            if(startIndexArray.size() <= 0){
                return 0;
            }
            
            List<Integer> newMagic = new ArrayList<Integer>();
            List<Integer> newDist = new ArrayList<Integer>();
            
            Map<Integer, Integer> indexValues = new HashMap<Integer, Integer>();

            for(int sia = 0; sia <= startIndexArray.size() - 1; ++sia){
                index = startIndexArray.get(sia);
                if (index >= 0){
                    for (int s=index; s<=mSize-1; s++){
                        newMagic.add(magic.get(s));
                        newDist.add(dist.get(s));
                    }
                
                    for(int s=0; s<=index-1; s++){
                        newMagic.add(magic.get(s));
                        newDist.add(dist.get(s));
                    }
                }
                else if (index == 0) {
                    newMagic = magic;
                    newDist = dist;
                }

                for (int m = 0; m < mSize; ++m){
                    if (m == 0) {
                        nSource =  newMagic.get(m) - newDist.get(m) + newMagic.get(m+1);
                    }else{
                        nSource = nSource - newDist.get(m);
                        if ((m+1) <= (mSize-1)){
                            nSource += newMagic.get(m+1);
                        }
                    }
                    
                }
                indexValues.put(index, nSource);
                newMagic = new ArrayList<Integer>();
                newDist = new ArrayList<Integer>();
            }
            int minKey = -1;
            int minValue = Integer.MAX_VALUE;
            for(int key : startIndexArray) {
                int value = indexValues.get(key);
                if(value < minValue) {
                    minValue = value;
                    minKey = key;
                }
            }
            if(minValue < 0){
                return -1;
            }else{
                return minKey;
            }
        }else{
            return -1;
        }
    }

    public static List<Integer> startIndexArray(List<Integer> magic, List<Integer> dist){
        int mSize = magic.size();
        int index = -1;
        List<Integer> startPoints = new ArrayList<Integer>();
        for (int m = 0; m <= mSize-1; m++){
            if( ((magic.get(m) >= 0) &&  (magic.get(m) <= 10000)) && ((dist.get(m) >= 0) &&  (dist.get(m) <= 10000)) ){
                if(magic.get(m) > dist.get(m)){
                    index = m;
                    startPoints.add(index);
                }
            }else{
                startPoints = new ArrayList<Integer>();
                return startPoints;
            }
        }
        return startPoints;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int magicCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> magic = new ArrayList<>();

        for (int i = 0; i < magicCount; i++) {
            int magicItem = Integer.parseInt(bufferedReader.readLine().trim());
            magic.add(magicItem);
        }

        int distCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> dist = new ArrayList<>();

        for (int i = 0; i < distCount; i++) {
            int distItem = Integer.parseInt(bufferedReader.readLine().trim());
            dist.add(distItem);
        }

        int result = Result.optimalPoint(magic, dist);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
