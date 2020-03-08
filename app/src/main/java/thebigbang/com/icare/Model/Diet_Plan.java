package thebigbang.com.icare.Model;

/**
 * Created by Jakir on 6/24/2015.
 */
public class Diet_Plan {
    private int id;
    private int profileId;
    private String date;
    private String day;
    private String breakFast;
    private String lunch;
    private String afternoonSnacks;
    private String dinner;
    private String notes;

    public Diet_Plan() {
    }

    public Diet_Plan(int id, String date,String day) {
        this.id = id;
        setDate(date);
        setDay(day);
    }

    public Diet_Plan(int profileId,String date,String day, String breakFast, String lunch, String afternoonSnacks, String dinner, String notes) {
        setProfileId(profileId);
        setDate(date);
        setDay(day);
        setBreakFast(breakFast);
        setLunch(lunch);
        setAfternoonSnacks(afternoonSnacks);
        setDinner(dinner);
        setNotes(notes);
    }

    public Diet_Plan(int id,int profileId, String date,String day, String breakFast, String lunch, String afternoonSnacks, String dinner, String notes) {
        this(profileId,date,day,breakFast,lunch,afternoonSnacks,dinner,notes);
        this.id = id;

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBreakFast() {
        return breakFast;
    }

    public void setBreakFast(String breakFast) {
        this.breakFast = breakFast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getAfternoonSnacks() {
        return afternoonSnacks;
    }

    public void setAfternoonSnacks(String afternoonSnacks) {
        this.afternoonSnacks = afternoonSnacks;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

