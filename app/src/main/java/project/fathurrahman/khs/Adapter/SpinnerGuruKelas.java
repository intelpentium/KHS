package project.fathurrahman.khs.Adapter;

/**
 * Created by Fathurrahman on 04/08/2016.
 */
public class SpinnerGuruKelas {
    private String id;
    private String name;

    public SpinnerGuruKelas(){}

    public SpinnerGuruKelas(String id, String name){
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
