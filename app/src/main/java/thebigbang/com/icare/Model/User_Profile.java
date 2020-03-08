package thebigbang.com.icare.Model;

/**
 * Created by Jakir on 6/9/2015.
 */
public class User_Profile {
    private int id;
    private String name;
    private int age;
    private String dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String bloodPressure;
    private String BMI;
    private double height;
    private double weight;
    private String phone;
    private String email;

    public User_Profile() {
    }

    public User_Profile(int id, String name) {
        this.id=id;
        setName(name);
    }

    public User_Profile(int id, String name, int age, String dateOfBirth, String gender, String bloodGroup, String bloodPressure, String BMI, double height, double weight, String phone, String email) {
       this(name,age,dateOfBirth,gender,bloodGroup,bloodPressure,BMI,height,weight,phone,email);
       this.id = id;

    }

    public User_Profile(String name, int age, String dateOfBirth, String gender, String bloodGroup, String bloodPressure, String BMI, double height, double weight, String phone, String email) {
        setName(name);
        setAge(age);
        setDateOfBirth(dateOfBirth);
        setGender(gender);
        setBloodGroup(bloodGroup);
        setBloodPressure(bloodPressure);
        setBMI(BMI);
        setHeight(height);
        setWeight(weight);
        setPhone(phone);
        setEmail(email);
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getBMI() {
        return BMI;
    }

    public void setBMI(String BMI) {
        this.BMI = BMI;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
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
}
