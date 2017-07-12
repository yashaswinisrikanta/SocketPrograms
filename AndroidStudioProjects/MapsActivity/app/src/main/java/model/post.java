package model;

//import com.google.android.gms.ads.formats.NativeAd;
import android.media.Image;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by yash on 21/02/17.
 */

public class post {
    private String name;
    private LatLng location;
    private Image image;
    private int availability;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

}
