package conceptual_modelling;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class document 
{
    //Set<word> terms = new HashSet<>(); // document vector.
    public Map<String, Integer> doc_term = new HashMap<>();
    Map<String, Float> term_pwd = new HashMap<>();
    public Map<String, Integer> concept = new HashMap<>();
    Map<String, Float> concept_pwd = new HashMap<>();
    public float pqd;
    Map<String, Float> tf = new HashMap<>();
    Map<String, Float> tfidf = new HashMap<>(); 
    //Set<concept> concepts = new HashSet<>();
    
    //ArrayList<ArrayList<Float>> pcqt = new ArrayList<>(); //one term of P(C|Q) (pcqt[i]->concepts[i]) i.e, P(c|D)*P(D|Q)
    
    public static String[] stopword={"a","....", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves", "the"};
    int total_words=0;
    int con_total_words=0;
    //float pqd[] = new float[11]; //P(Q|D)                                                                

    
     
    //name, frequency and pml of words of a document are calculated, stored in "terms" 
    //total words of the doc are also calculated, total words of the collection are updated.
    //collection terms and coll word list is updated too.
    
    document(String doc_str, String[] input_concept,Map<String, Integer> c_concept, Map<String, Integer> c_term ) //, Set<word> c_terms, Set<String> c_word_list)  
    {
        int fromIndex=0; int temp=0; int count=0; Integer con_freq=0;
        for(String concept_in_doc: input_concept){
            fromIndex=0; temp=0; count=0; 
            while(fromIndex<(doc_str.length()-concept_in_doc.length())){
            temp=doc_str.indexOf(concept_in_doc, fromIndex);
            if(temp>-1){
            fromIndex=temp+concept_in_doc.length();
            count++;            
            }
            else //not found
            break;
            }
            concept.put(concept_in_doc, count);
            con_freq=c_concept.get(concept_in_doc);
            if(con_freq==null) con_freq=0;
            con_freq=con_freq+count;
            c_concept.put(concept_in_doc, con_freq);
            con_total_words=con_total_words+count;
            
        }//end of for
	
         String[] words = doc_str.split(" ");
         for(String t:words) t=t.trim();
         Integer freq;
         for(int i=0; i<words.length;i++){
                 if (!isStopword(words[i])) {
                    freq = doc_term.get(words[i]);
                    if (freq == null) {
                        freq = 0;
                    }
                    ++freq;
                    try{
                        doc_term.put(words[i], freq);
                        total_words=total_words+freq;
                        con_freq=c_term.get(words[i]);
                        if(con_freq==null) con_freq=0;
                        con_freq=con_freq+freq;
                        c_term.put(words[i], con_freq);
                        Collection.total_words=Collection.total_words+con_freq;
                    }  catch(Exception e){
                        System.out.println("map add error");
                    }
                    
                }//end of if
         }//end of for  
         Collection.con_total_words=Collection.con_total_words+con_total_words;
                       
    }

    
    static boolean isStopword(String w)
    {
	for(String word: stopword)
	if(w.equalsIgnoreCase(word))
	  return true;
		return false;
    }
    public void calc_td(){
    float f;
        String w; Integer i=0, max=0;
        Set<Map.Entry<String, Integer>> term_map=doc_term.entrySet();
           Iterator t_itr= term_map.iterator();
           Entry<String, Integer> c;
           while(t_itr.hasNext()){
               c=(Entry<String, Integer>) t_itr.next();
               w=c.getKey();
               i=doc_term.get(c);
               if(i>max) max=i;
           }
           t_itr= term_map.iterator();
           
           while(t_itr.hasNext()){
               c=(Entry<String, Integer>) t_itr.next();
               w=c.getKey();
               f=(float) (0.5+(0.5*doc_term.get(c)/(float)i));
               tf.put(w,f);
    }
    }
    public void calc_tfidf(Map<String, Integer> c_term_doc_freq){
       
         String w; float i,c_i;
        Set<Map.Entry<String, Integer>> term_map=doc_term.entrySet();
           Iterator t_itr= term_map.iterator();
           Entry<String, Integer> c;
           while(t_itr.hasNext()){
               c=(Entry<String, Integer>) t_itr.next();
               w=c.getKey();
               i=tf.get(c);
               c_i=c_term_doc_freq.get(c);
                tfidf.put(w,i*c_i);
           }
    }
    public float calc_ptc(String term_in_coll, String concept_inputted){
       //System.out.println("calc_ptc"); 
    float prod;
            Float c_pwd=concept_pwd.get(concept_inputted);
            Float t_pwd=term_pwd.get(term_in_coll);
    if(c_pwd==null){
        //System.out.println(w+":"+ "null");
        return 0;
    }
    if(t_pwd==null){
        //System.out.println(concept_inputted+":"+ "null");
        return 0;
    }
    prod=c_pwd*t_pwd;
    //System.out.println(prod);
        return prod;
    }
    public void calc_pwd_doc_all_parameter(Map<String, Integer> c_term, FileWriter fw){
        float pwd= (float) 0.0;
        int c_freq, freq;
        float prob, c_prob;
        Set<Map.Entry<String, Integer>> term_map=doc_term.entrySet();
           Iterator t_itr= term_map.iterator();
           Entry<String, Integer> c;
           while(t_itr.hasNext()){
               c=(Entry<String, Integer>) t_itr.next();
               String w=c.getKey();
                for(float i=0;i<=1;i=(float) (i+0.1)){
                   freq=doc_term.get(w);
                   c_freq=c_term.get(w);
                   if(total_words!=0){
                   prob=(float)freq/(float)total_words;
                   c_prob=(float)c_freq/(float)Collection.total_words;            
                   pwd= i*prob+(1-i)*c_prob;
                   }
                   else
                       pwd=0;
                   try {
                       fw.write(w+": "+ pwd+System.getProperty("line.separator"));
                   } catch (IOException ex) {
                       Logger.getLogger(document.class.getName()).log(Level.SEVERE, null, ex);
                   }
        
        }// endof inner for
    }// end of outer for
    }//endof func
    public boolean hasconcept(String inputted_concept){         
    return doc_term.containsValue(inputted_concept);
    }
    
    public void calc_pwd_doc_inputted_parameter(Map<String, Integer> c_term, float i){
        float pwd= (float) 0.0;
        int c_freq, freq;
        float prob, c_prob;
        Set<Map.Entry<String, Integer>> term_map=doc_term.entrySet();
           Iterator c_itr= term_map.iterator();
           
           while(c_itr.hasNext()){
               Entry<String, Integer> c=(Entry<String, Integer>) c_itr.next();
               String w=c.getKey();
               freq=doc_term.get(w);
               c_freq=c_term.get(w);
               if(total_words!=0){
               prob=(float)freq/(float)total_words;
               c_prob=(float)c_freq/(float)Collection.total_words;            
               pwd= i*prob+(1-i)*c_prob;
               }
               else
                   pwd=0;
              term_pwd.put(w, pwd);
              
    }// end of outer for
    }//endof func
    public void calc_pcd_doc_inputted_parameter(Map<String, Integer> c_concept, float i){
        float pwd= (float) 0.0;
        int c_freq, freq;
        float prob, c_prob;
        Set<Map.Entry<String, Integer>> concept_map=concept.entrySet();
           Iterator c_itr= concept_map.iterator();
           
           while(c_itr.hasNext()){
               Entry<String, Integer> c=(Entry<String, Integer>) c_itr.next();
               String w=c.getKey();
               freq=concept.get(w);
               c_freq=c_concept.get(w);
               if(total_words!=0){
               prob=(float)freq/(float)con_total_words;
               c_prob=(float)c_freq/(float)Collection.con_total_words;            
               pwd= i*prob+(1-i)*c_prob;
               }
               else
                   pwd=0;
              concept_pwd.put(w, pwd);
              
    }// end of outer for
    }//endof func
    public void calc_pqd(int count_doc,String query_name, FileWriter fw){
        pqd=1;
        String[] words=query_name.split(" ");
        Float p;
        for(String word: words){
            
            p=term_pwd.get(word);
            if(p==null) p=(float)0;
            if(p==(float)0) return;
            pqd=pqd*p;
        }
        try {
                       fw.write(count_doc+": "+ pqd+System.getProperty("line.separator"));
                   } catch (IOException ex) {
                       Logger.getLogger(document.class.getName()).log(Level.SEVERE, null, ex);
                   }
    }
}
