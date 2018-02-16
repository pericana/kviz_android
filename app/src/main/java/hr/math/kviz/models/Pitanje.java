package hr.math.kviz.models;

import java.io.Serializable;

public class Pitanje implements Serializable{

    public long id;
    public int idCat;
    public String text;
    public String odgA;
    public String odgB;
    public String odgC;
    public String odgD;
    public int odgTocan;
    public int brPojavljivanja;

}
