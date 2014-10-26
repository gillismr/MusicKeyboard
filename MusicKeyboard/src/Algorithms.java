import world.sound.tunes.Note;

/**Represents a single method so it can be used by both Keyboard and OurChord*/
public class Algorithms{
	
	
	public String noteName(int i){
        String s = "";
		Note n = new Note(i);
        char letter = n.toString().charAt(6);
        char mod = n.toString().charAt(8);
        if(mod == 'n')
            s += letter;
        if(mod == 's')
            s += letter + "#/" + this.noteName(i+1) + "b";
        return s;
	}

}