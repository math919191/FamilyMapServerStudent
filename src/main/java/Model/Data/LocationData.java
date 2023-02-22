package Model.Data;

import java.util.ArrayList;
import java.util.Random;

public class LocationData {
    ArrayList<Location> data;

    LocationData(){
        this.data = new ArrayList<>();
    }
    void addData(Location item){
        data.add(item);
    }

    Location getRandomItem(){
        Random rand = new Random();
        return data.get(rand.nextInt(data.size()));
    }

    public ArrayList<Location> getData() {
        return data;
    }

    public void setData(ArrayList<Location> data) {
        this.data = data;
    }

}
