package Model.Data;

import java.util.ArrayList;
import java.util.Random;

public class NamesData {

    ArrayList<String> data;

    NamesData(){
        this.data = new ArrayList<>();
    }
    void addData(String item){
        data.add(item);
    }

    String getRandomItem(){
        Random rand = new Random();
        return data.get(rand.nextInt(data.size()));
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
}
