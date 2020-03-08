package thebigbang.com.icare.Model;

/**
 * Created by Piyal on 6/27/2015.
 */
public class Doctors {
    private int id;
    private int profileId;
    private String name;
    private String specialities;
    private String phone;
    private String email;
    private String hospital;
    private String chamberAddress;
    private String fee;

    public Doctors() {

    }
    public Doctors(String name, String specialities, String fee) {
        setName(name);
        setSpecialities(specialities);
        setFee(fee);
    }

    public Doctors(int id,String name, String specialities, String fee) {
        this(name,specialities,fee);
        this.id=id;

    }

    public Doctors(int profileId,String name, String specialities, String phone, String email, String hospital, String chamberAddress, String fee) {
        this(name, specialities, fee);
        setProfileId(profileId);
        setPhone(phone);
        setEmail(email);
        setHospital(hospital);
        setChamberAddress(chamberAddress);
    }

    public Doctors(int id,int profileId, String name, String specialities, String phone, String email, String hospital, String chamberAddress, String fee) {
        this(profileId,name, specialities, phone, email, hospital, chamberAddress, fee);
        this.id=id;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getChamberAddress() {
        return chamberAddress;
    }

    public void setChamberAddress(String chamberAddress) {
        this.chamberAddress = chamberAddress;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
