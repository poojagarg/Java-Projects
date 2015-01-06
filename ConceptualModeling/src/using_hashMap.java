/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptual_modelling;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author pooja
 */
public class using_hashMap {
    public static void main(String[] args){
        String doc_str="hello my name is pooja is name hello";
        
        String[] words = doc_str.split(" ");
         Map<String, Integer> doc_str_frequency = new HashMap<>();
         
         for(int i=0; i<words.length;i++){
                 if (words[i].length()>1) {
                    Integer frequency = doc_str_frequency.get(words[i]);
                    if (frequency == null) {
                        frequency = 0;
                    }
                    ++frequency;
                    try{
                    doc_str_frequency.put(words[i], frequency);
                    }  catch(Exception e){
                        System.out.println("map add error");
                    }
                    
                }//end of if
         }//end of for
         System.out.println(doc_str_frequency);
         
    }
    
}
