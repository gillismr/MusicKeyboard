import java.util.HashMap;
import java.util.HashSet;

import image.*;
import world.sound.SoundWorld;

/**Represents a world for playing and displaying basic chords*/
public class ChordWorld extends SoundWorld{
    public static void main(String[] args){
        new ChordWorld().bigBang();
    }
    
    /**Represents a keyboard */
    Keyboard theKeyboard = new Keyboard();
    
    /**Represents each key that corresponds to a note on a keyboard and that note's offset of the base C*/
    HashMap<String, Integer> keys = new HashMap<String, Integer>();
    
    /**Represents the keyboard keys that are currently pressed down*/
    HashSet<String> downKeys = new HashSet<String>();

    /**The most recent chord that was played*/
    OurChord current = new OurChord(60, 0, 0);
    
    /**The MIDI frequency of the low C on the keyboard*/
    int baseC = 60;
    
    /**The instrument*/
    int channel = 1;
    
    /**Initializes key mappings*/
    ChordWorld(){
        //Mapping of keys to note values(offsets)
        this.keys.put("a", 0);
        this.keys.put("w", 1);
        this.keys.put("s", 2);
        this.keys.put("e", 3);
        this.keys.put("d", 4);
        this.keys.put("f", 5);
        this.keys.put("t", 6);
        this.keys.put("g", 7);
        this.keys.put("y", 8);
        this.keys.put("h", 9);
        this.keys.put("u", 10);
        this.keys.put("j", 11);
        this.keys.put("k", 12);
        
    }
    
    /**Key Down - changes variables of this ChordWorld and current based on 
     * the given key, then tells theKeyboard which keys are down and plays the chord*/
    public void onKey(String key){
        //Set the root value of the current chord
    	if(this.keys.containsKey(key) && !downKeys.contains(key)){
        	this.keyTunes.clearBucket();
        	this.keyTunes.clearTunes();
        	this.downKeys.clear();
        	this.downKeys.add(key);
        	this.current.setBase(this.keys.get(key) + this.baseC);
            this.theKeyboard.setDowns(this.current, this.baseC);
            this.keyTunes.addChord(this.channel, current.makeChord());
        }
        
        // Set the instrument
        if(key.equals("up")){
            this.channel++;
        }
        if(key.equals("down") && channel > 0){
            this.channel--;
        }
        
        // Set the chord mode
        if(key.equals("z")){
            this.current.setMode(0);
        }
        if(key.equals("x")){
            this.current.setMode(1);
        }
        if(key.equals("c")){
            this.current.setMode(2);
        }
        if(key.equals("v")){
            this.current.setMode(3);
        }
        
        // Change the octave
        if((key.equals("=")) && (this.baseC < 96)){
            this.baseC += 12;
        }
        if((key.equals("-")) && (this.baseC > 24)){
            this.baseC -= 12;
        }
        
        // Change the inversion mode
        if(key.equals(".")){
            this.current.increaseInv();
        }
        if(key.equals(",")){
            this.current.decreaseInv();
        }

    }
    
    /**Key Up - if the key is a note key, remove it from down*/
    public void onRelease(String key){
        if(this.keys.containsKey(key))
        {
        	this.keyTunes.clearBucket();
        	this.keyTunes.clearTunes();
        	this.downKeys.remove(key);
        }
    }    
    
    /**Creates an empty scene, draws a keyboard, draws the mode labels, and if a chord is playing draw its label*/
    public Scene onDraw(){
        Scene acc = new EmptyScene(680, 300);
        acc = this.theKeyboard.drawKeyboard(acc);
        acc = this.drawMode(acc);
        acc = this.drawOctave(acc);
        acc = this.drawInversion(acc);
        if(this.downKeys.isEmpty())
        	this.theKeyboard.down.clear();
        else acc = this.drawChord(acc);
        return acc;
    }
    

    /**Draws labels for the four modes into the lower left corner, colors the current mode red*/
    public Scene drawMode(Scene acc){
        acc = acc.placeImage(new Text("Major", 15, modeColor(0)), 40, 200);
        acc = acc.placeImage(new Text("Minor", 15, modeColor(1)), 40, 220);
        acc = acc.placeImage(new Text("Augmented", 15, modeColor(2)), 40, 240);
        acc = acc.placeImage(new Text("Diminished", 15, modeColor(3)), 40, 260);
        return acc;
    }
    
    /**If the given int is the same as current's mode, return "red"*/
    public String modeColor(int modeComp){
        if(modeComp == this.current.getMode()){
            return "red";}
        return "black";         
    }

    
    /**Draws the current octave number */
    public Scene drawOctave(Scene acc){
        acc = acc.placeImage(new Text("Octave: " + ((this.baseC - 12)/12), 15, "black"), 40, 280);
        return acc;
        
    }
    
    /**Draws the current inversion mode*/
    public Scene drawInversion(Scene acc){
        if(this.current.getInv() == 0){
            acc = acc.placeImage(new Text("Root chords", 15, "black"), 200, 280);
        }
        if(this.current.getInv() == 1){
            acc = acc.placeImage(new Text("First inversion chords", 15, "black"), 200, 280);
        }
        if(this.current.getInv() == 2){
            acc = acc.placeImage(new Text("Second inversion chords", 15, "black"), 200, 280);
        }
        return acc;
    }

    /**Draws the label for the Chord that is currently playing*/
    public Scene drawChord(Scene acc){
        acc = acc.placeImage(new Text(this.current.toString(), 25, "black"), 300, 220);
        return acc;
    }
    
}