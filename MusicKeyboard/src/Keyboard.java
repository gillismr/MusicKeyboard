import image.Rectangle;
import image.Scene;
import image.Text;

import java.util.HashMap;
import java.util.HashSet;

/**Represents a visual keyboard with information about which 
 * keys are pressed and what pitch offsets those keys correspond to*/
public class Keyboard{
	
	Algorithms algo = new Algorithms();
	
    /**Represents the offset of the notes that are currently being played down*/
    HashSet<Integer> down = new HashSet<Integer>();
    
    /**Represents the order of keys to be drawn mapped to their note offsets*/
    HashMap<Integer, Integer> draw = new HashMap<Integer, Integer>();

    /**Initializes key mappings and drawing order*/
    Keyboard(){
        //Mapping of drawing order to note values
        //White keys first
        this.draw.put(0,  0);
        this.draw.put(1,  2);
        this.draw.put(2,  4);
        this.draw.put(3,  5);
        this.draw.put(4,  7);
        this.draw.put(5,  9);
        this.draw.put(6,  11);
        this.draw.put(7,  12);
        this.draw.put(8,  14);
        this.draw.put(9,  16);
        this.draw.put(10, 17);
        this.draw.put(11, 19);
        this.draw.put(12, 21);
        this.draw.put(13, 23);
        this.draw.put(14, 24);

        //Now black keys
        this.draw.put(15,  1);
        this.draw.put(16,  3);
        //SKIP
        this.draw.put(18, 6);
        this.draw.put(19, 8);
        this.draw.put(20, 10);
        //SKIP
        this.draw.put(22, 13);
        this.draw.put(23, 15);
        //SKIP
        this.draw.put(25, 18);
        this.draw.put(26, 20);
        this.draw.put(27, 22);
        
    }
    
    /**EFFECT: for each note in the given chord, add it's pitch offset relative to the baseC note to down*/
    public void setDowns(OurChord chord, int baseC){
        this.down.clear();
    	for(int n:chord.getPitches()){
            this.down.add(n-baseC);
        }
    }

    /**Draw a keyboard into the given scene with the appropriate red keys and labels for notes that are playing*/
    public Scene drawKeyboard(Scene acc){
        //White keys first
        for(int i = 0; i < 15; i++){
            acc = acc.placeImage(new Rectangle(40, 120, keyMode(i), keyColor(i)), 60+i*40, 100);
            if(this.down.contains(this.draw.get(i))){
                acc = acc.placeImage(new Text(this.algo.noteName(this.draw.get(i)), 15, "black"), 60+i*40, 173);
            }
        }
        
        //Black keys
        for(int i = 0; i < 13; i++){
            if(i != 2 && i != 6 && i != 9){
                acc = acc.placeImage(new Rectangle(30, 60, "solid", keyColor(i+15)), 80+i*40, 60);
                if(this.down.contains(this.draw.get(i+15))){
                    acc = acc.placeImage(new Text(this.algo.noteName(this.draw.get(i+15)), 15, "black"), 80+i*40, 173);
                }
            }
        }
        return acc;
    }
    
    /**Paint solid if the key is down or it's a black key*/
    public String keyMode(int i){
        if(i >= 15 || this.down.contains(this.draw.get(i)))
            return "solid";
        return "outline";
    }
    
    /**Use the color red if the key is down*/
    public String keyColor(int i){
        if(this.down.contains(this.draw.get(i)))
            return "red";
        return "black";
    }
    

	
}