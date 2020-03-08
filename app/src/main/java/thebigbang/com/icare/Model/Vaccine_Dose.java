package thebigbang.com.icare.Model;

/**
 * Created by Jakir on 6/21/2015.
 */
public class Vaccine_Dose {
    private int id;
    private int vaccineId;
    private String date;
    private String careCenter;

    public Vaccine_Dose() {
    }

    public Vaccine_Dose(int vaccineId, String date, String careCenter) {
        setVaccineId(vaccineId);
        setDate(date);
        setCareCenter(careCenter);
    }

    public Vaccine_Dose(int id, int vaccineId, String date, String careCenter) {
       this(vaccineId,date,careCenter);
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCareCenter() {
        return careCenter;
    }

    public void setCareCenter(String careCenter) {
        this.careCenter = careCenter;
    }
}
