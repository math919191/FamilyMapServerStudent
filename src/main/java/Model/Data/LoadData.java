package Model.Data;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class LoadData {

    LocationData locationData;
    SNamesData sNamesData;
    FNamesData fNamesData;
    MNamesData mNamesData;

    public LoadData() throws FileNotFoundException {
        Gson gson = new Gson();

        Reader reader = new FileReader("json/locations.json");
        locationData = (LocationData) gson.fromJson(reader, LocationData.class);

        reader = new FileReader("json/snames.json");
        sNamesData = (SNamesData) gson.fromJson(reader, SNamesData.class);

        reader = new FileReader("json/fnames.json");
        fNamesData = (FNamesData) gson.fromJson(reader, FNamesData.class);

        reader = new FileReader("json/mnames.json");
        mNamesData = (MNamesData) gson.fromJson(reader, MNamesData.class);
    }

    public LocationData getLocationData() {
        return locationData;
    }

    public void setLocationData(LocationData locationData) {
        this.locationData = locationData;
    }

    public SNamesData getsNamesData() {
        return sNamesData;
    }

    public void setsNamesData(SNamesData sNamesData) {
        this.sNamesData = sNamesData;
    }

    public FNamesData getfNamesData() {
        return fNamesData;
    }

    public void setfNamesData(FNamesData fNamesData) {
        this.fNamesData = fNamesData;
    }

    public MNamesData getmNamesData() {
        return mNamesData;
    }

    public void setmNamesData(MNamesData mNamesData) {
        this.mNamesData = mNamesData;
    }
}
