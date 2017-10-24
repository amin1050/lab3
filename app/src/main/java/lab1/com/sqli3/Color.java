package lab1.com.sqli3;

/**
 * Created by Eokinyi on 10/25/2017.
 */

public class Color {
    //private variables
    int _cid;
    String _cname;

    public Color(){}
    public Color(int id, String name){
        this._cid = id;
        this._cname = name;
    }
    public Color(String name){
        this._cname = name;
    }

    public int getCID() {
        return _cid;
    }

    public void setCID(int _cid) {
        this._cid = _cid;
    }

    public String getCName() {
        return _cname;
    }

    public void setCName(String _cname) {
        this._cname = _cname;
    }

}

