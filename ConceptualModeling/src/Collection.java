/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptual_modelling;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Math;

/**
 *
 * @author pooja
 */
public class Collection {
    String query_name;
    String directory_path; 
    String[] input_concept;
    public static String[] stopword={"","a","....", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves", "the"};

    int nod;
    Map<String, Integer> concept = new HashMap<>();
    Map<String, Integer> term = new HashMap<>();
    Map<String, Float> pt = new HashMap<>();
    Map<String, Float> ptc = new HashMap<>(); //w+","+ concept_inputted
    Map<String, Float> pct = new HashMap<>(); //concept_inputted+","+w
    Map<String, Float> pcq = new HashMap<>(); 
    Map<String, Float> pc_tfidf = new HashMap<>(); 
    Map<String, Float> pc_wtfidf = new HashMap<>(); 
    Map<String, Integer> term_doc_freq = new HashMap<>();
    public ArrayList<document> doc_array = new ArrayList<>();
    static Set<document> query_containing_doc=new HashSet();
    static int total_words=0;
    static int con_total_words=0;
    static Map<String, Float> ptq= new HashMap();
    FileWriter fw;
    String pwdtext= "result/pwdtextfile.txt";
    String pqdtext= "result/pqdtextfile.txt";
    String pwd_i_text="result/pwd_i_text.txt";
    String pcdtext= "result/pcdtext.txt";
    String pcqtext= "result/pcqtext.txt";
    String pcttext= "result/pcttext.txt";
    String ptctext= "result/ptctext.txt";
    String tfidf= "result/tfidf.txt";
    String wtfidf= "result/wtfidf.txt";
    String pttext= "result/pttext.txt";
    float parameter=(float) 0.7;
        static boolean isStopword(String w)
    {
	for(String word: stopword)
	if(w.equalsIgnoreCase(word))
	  return true;
		return false;
    }
    public Collection( File file, String p){
        parameter=Float.parseFloat(p);
        query_name=file.getName();
        //"C:\Users\pooja\Documents\1990 fisa wiretap applications"
        directory_path= file.getAbsolutePath();
        String snip_file_name, Concepts_unformated_name;
        snip_file_name = directory_path+ "/snip_final.txt";
        Concepts_unformated_name=directory_path+ "/Concepts_unformated.txt";
        collection_input_conceptfile_update(Concepts_unformated_name); //common concept file
        collection_update(snip_file_name); //filling docs in collection
        calc_pwd_and_print(); //to make pwd file and analyze parameter
        calc_pqd();
        fw_pwd_inputted_parameter();
        fw_pcd();
        calc_pt();        
        calc_ptc();
        calc_ptq();
        calc_pcq(); 
        calc_tfidf();
    }

    
    public void fw_pwd_inputted_parameter(){
        try {
            fw= new FileWriter(pwd_i_text);
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
    Iterator<document> ditr=doc_array.iterator();
    document d;
    Set<Map.Entry<String, Float>> term_map;
           Iterator t_itr;
            Map.Entry<String, Float> term_entry;
           
    while(ditr.hasNext()){
        
        d=ditr.next();
        term_map=d.term_pwd.entrySet();
        t_itr= term_map.iterator();
        while(t_itr.hasNext()){
              term_entry=(Map.Entry<String, Float>) t_itr.next();
              //System.out.println(c.getKey()+": "+c.getValue());
            try {
                fw.write(term_entry.getKey()+": "+term_entry.getValue());
            } catch (IOException ex) {
                Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    }
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void fw_pcd(){
        try {
            fw= new FileWriter(pcdtext);
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
    Iterator<document> ditr=doc_array.iterator();
    document d;
    Set<Map.Entry<String, Float>> concept_map;
           Iterator c_itr;
            Map.Entry<String, Float> concept_entry;
           
    while(ditr.hasNext()){
        
        d=ditr.next();
        concept_map=d.concept_pwd.entrySet();
        c_itr= concept_map.iterator();
        while(c_itr.hasNext()){
              concept_entry=(Map.Entry<String, Float>) c_itr.next();
              //System.out.println(c.getKey()+": "+c.getValue());
            try {
                fw.write(concept_entry.getKey()+": "+concept_entry.getValue());
            } catch (IOException ex) {
                Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    }
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void calc_ptq(){
        Float pwd, sum; document d;
        Set<Map.Entry<String, Integer>> term_map=term.entrySet();
           Iterator t_itr= term_map.iterator();
           String w;
           while(t_itr.hasNext()){
               Map.Entry<String, Integer> c=(Map.Entry<String, Integer>) t_itr.next();
               w=c.getKey();
               sum=(float)0.0;
                for (Iterator<document> it = query_containing_doc.iterator(); it.hasNext();) {
                    d = it.next();
                    pwd=d.term_pwd.get(w);
                    if(pwd==null) 
                        pwd=(float)(0);
                    sum=sum+pwd*d.pqd;
                }
                ptq.put(w,sum);
                    }
    }
    private void calc_pcq(){
        try {
            fw= new FileWriter(pcqtext);
            //fw.write("welcome");
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
        String s;
        Set<Map.Entry<String, Integer>> term_map=term.entrySet();
           Iterator t_itr= term_map.iterator();
           String term_in_coll;
           Map.Entry<String, Integer> term_entry;
           float sum;
        
        for(String inputted_concept: input_concept){ //for each concept
            t_itr= term_map.iterator();
            sum=(float)0;
            while(t_itr.hasNext()){  //for each term in coll
               term_entry=(Map.Entry<String, Integer>) t_itr.next();
               term_in_coll=term_entry.getKey();
               s=inputted_concept+","+term_in_coll;
               sum=sum+ pct.get(s)*ptq.get(term_in_coll);
            }
            pcq.put(inputted_concept,sum);
            try {
                if(sum!=0)
                fw.write(inputted_concept+": "+sum+System.lineSeparator());
               // System.out.println("pcq"+inputted_concept+": "+sum);
            } catch (IOException ex) {
                Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void calc_ptc(){
        FileWriter f_ptc = null;
        try {
            fw= new FileWriter(pcttext); 
            f_ptc= new FileWriter(ptctext); 
            //fw.write("welcome");
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
        float sum=0, res=0;
           Set<Map.Entry<String, Integer>> term_map=term.entrySet();
           Iterator t_itr;
           Map.Entry<String, Integer> term_entry;
           Float pt_for_ptc;
           Iterator<document> ditr;
           String term_in_coll;
           int not=0;
        for(String concept_inputted: input_concept){
            t_itr= term_map.iterator();
            while(t_itr.hasNext()){
                term_entry=(Map.Entry<String, Integer>) t_itr.next();
                sum=0;
               
               term_in_coll=term_entry.getKey();
               not=0;
               ditr=doc_array.iterator();
               while(ditr.hasNext()){
                   document d=ditr.next();
                   if(d.doc_term.get(term_in_coll)!=null){
               sum=sum+d.calc_ptc(term_in_coll,concept_inputted);
                   not++;
                   }
               }
               term_doc_freq.put(term_in_coll,not);
               sum=sum/(float)not;
               String appendedString=new String(concept_inputted+","+term_in_coll);
               ptc.put(appendedString, sum);
               pt_for_ptc=pt.get(term_in_coll);
               
               if(pt_for_ptc==0||pt_for_ptc==null)
                   pct.put(appendedString,(float)0);
               else{
                   res=sum/pt_for_ptc;
                   pct.put(appendedString, res );
               }
                try {
                    if(!(sum==0))
                    {
                        fw.write(appendedString+": "+res+System.lineSeparator());
                        f_ptc.write(appendedString+": "+sum+System.lineSeparator());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
        try {
            fw.close();
            f_ptc.close();
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void calc_tfidf(){
       FileWriter fw_tfidf=null; 
        try {
            fw= new FileWriter(tfidf); 
           fw_tfidf= new FileWriter(wtfidf);
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
        float multiplier,res, sum=0;
        
           
           Iterator<document> ditr;
           document d;
           int f=0,noc=0;
           int nod=doc_array.size();
        for(String concept_inputted: input_concept){            
               noc=0; f=0;
               ditr=doc_array.iterator();
               while(ditr.hasNext()){                   
                   d=ditr.next();
                   if(d.concept.get(concept_inputted)!=null){
                   f=  d.concept.get(concept_inputted); 
                   if(f>0){
               sum=sum+(float)f/(float)d.con_total_words;
               noc++;
                   }
                   }
               }
               if(noc!=0)
               {
               multiplier=(float)nod/(float)noc;
               res=sum*(float)Math.log(multiplier);
               pc_tfidf.put(concept_inputted, res);
               pc_wtfidf.put(concept_inputted, res/multiplier);
               try {
                fw.write(concept_inputted+":"+ res+System.lineSeparator());
                fw_tfidf.write(concept_inputted+":"+ res/multiplier+System.lineSeparator());
                //System.out.println(c.getKey()+": "+f);
            } catch (IOException ex) {
                Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            }
                      
        try {
            fw.close();
            fw_tfidf.close();
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void collection_input_conceptfile_update(String file_path) //read each line and call collection_add_document(String doc_str) 
    {
       try
    {
	BufferedReader br = new BufferedReader(new FileReader(file_path));
        
        String sCurrentLine;
        String line=br.readLine();
        line=line.toUpperCase();
        
        while ((sCurrentLine = br.readLine()) != null) 
        {
            sCurrentLine=sCurrentLine.toUpperCase();
             line=line+","+sCurrentLine;              
              
        }
        
        input_concept=line.split(",");
        for(String t:input_concept) t=t.trim();
        Set<String> set = new LinkedHashSet<String>(Arrays.asList(input_concept));
        set.remove("");
        
        String[] result = new String[set.size()];
        set.toArray(result);
        input_concept=result;        
        
        br.close();
     }
     catch(Exception e)
     {
        System.out.println(e+"file could not be read");
     }
    }
   
    private void calc_pt(){
        try {
            fw= new FileWriter(pttext,false);
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map.Entry<String, Integer> c;
        Set<Map.Entry<String, Integer>> term_map=term.entrySet();
           Iterator c_itr= term_map.iterator();
           
           while(c_itr.hasNext()){
               c=(Map.Entry<String, Integer>) c_itr.next();
               int c_freq=c.getValue();
               float f=(float)c_freq/(float)total_words;
               pt.put(c.getKey(),f);
            try {
                fw.write(c.getKey()+": "+f+System.lineSeparator());
                //System.out.println(c.getKey()+": "+f);
            } catch (IOException ex) {
                Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
            }
           }
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void calc_pqd(){
        try {
            fw= new FileWriter(pqdtext);
            //fw.write("welcome");
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Iterator<document> ditr = doc_array.iterator();
        int count_doc=1;   
            while(ditr.hasNext()){
           
                
                document curr = ditr.next();
                curr.calc_pcd_doc_inputted_parameter(concept,parameter);
                curr.calc_pwd_doc_inputted_parameter(term,parameter);
                curr.calc_pqd(count_doc,query_name, fw);
                if(curr.pqd!=0)
                    query_containing_doc.add(curr);
                count_doc++;
            } 
        try {
            fw.close(); 
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void calc_pwd_and_print(){ //to make pwd file and analyze parameter
        try {
            fw= new FileWriter(pwdtext);
        } catch (IOException ex) {
            Logger.getLogger(Collection.class.getName()).log(Level.SEVERE, null, ex);
        }
        Iterator<document> ditr = doc_array.iterator();
            
            while(ditr.hasNext()){
                document curr = ditr.next();
                curr.calc_pwd_doc_all_parameter(term, fw);
            } 
        
    }
    
    private void collection_update(String snip_file_name) //read each line and call collection_add_document(String doc_str) 
    {        
    try
    {	
        BufferedReader br = new BufferedReader(new FileReader(snip_file_name));
	String sCurrentLine;
        while ((sCurrentLine = br.readLine()) != null) 
        {
            if(sCurrentLine.length()<5){ 
                              sCurrentLine = br.readLine();
                          }
            nod++;
            sCurrentLine.toUpperCase();
              collection_add_document(sCurrentLine);           
                          
             }
        br.close();
        }      
             
     catch(Exception e)
     {
        //System.out.println(e+"file could not be read");
     }
        //System.out.println("check");
    }
    public void collection_add_document(String doc_str)
    {
        
        //System.out.println(doc_str);
        document d = new document(doc_str , input_concept, concept, term); 
        doc_array.add(d);   
    }     
}
