package thebigbang.com.icare.Model;

/**
 * Created by Jakir on 6/18/2015.
 */
public class Diseases {
    private int id;
    private int userID;
    private String name;
    private String symptoms;
    private String comments;
    private String medicines;
    private String date;

    public Diseases() {
    }
    public Diseases(int id,String name) {
        setName(name);
        this.id=id;
    }

    public Diseases(String name, String symptoms, String comments, String medicines, String date) {
        setName(name);
        setSymptoms(symptoms);
        setComments(comments);
        setMedicines(medicines);
        setDate(date);
    }

    public Diseases(int userID, String name, String symptoms, String comments, String medicines, String date) {
        this(name,symptoms,comments,medicines,date);
        this.userID = userID;

    }

    public Diseases(int id, int userID, String name, String symptoms, String comments, String medicines, String date) {
        this(userID,name,symptoms,comments,medicines,date);
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
