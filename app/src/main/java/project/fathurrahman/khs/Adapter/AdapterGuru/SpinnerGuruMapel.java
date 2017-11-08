package project.fathurrahman.khs.Adapter.AdapterGuru;

/**
 * Created by Fathurrahman on 06/12/2016.
 */

public class SpinnerGuruMapel {
    private String id;
    private String name;

    public SpinnerGuruMapel(){}

    public SpinnerGuruMapel(String id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
}
