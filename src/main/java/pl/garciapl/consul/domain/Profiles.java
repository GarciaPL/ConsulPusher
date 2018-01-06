package pl.garciapl.consul.domain;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Profiles {

    @SerializedName("profiles")
    private List<ProfileEntry> profiles;

    public List<ProfileEntry> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfileEntry> profiles) {
        this.profiles = profiles;
    }
}
