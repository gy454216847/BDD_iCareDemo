package thebigbang.com.icare.Model;

/**
 * Created by Piyal on 6/20/2015.
 */
public class Vaccination {
    private int id;
    private int profileId;
    private String name;
    private int totalDose;
    private int completeDose;
    private int remainingDose;
    private long eventTime;
    private String nextDate;
    private String careCenter;

    public Vaccination() {
    }

    public Vaccination(int id, String name,String nextDate) {
        this.id=id;
        setNextDate(nextDate);
        setName(name);
    }

    public Vaccination(int completeDose, int remainingDose, String nextDate, String careCenter) {
       setCompleteDose(completeDose);
       setRemainingDose(remainingDose);
       setNextDate(nextDate);
       setCareCenter(careCenter);
    }

    public Vaccination(int profileId,String name, int totalDose, int completeDose, int remainingDose,String nextDate,String careCenter,long eventTime) {
        setName(name);
        setEventTime(eventTime);
        setProfileId(profileId);
        setTotalDose(totalDose);
        setCompleteDose(completeDose);
        setRemainingDose(remainingDose);
        setNextDate(nextDate);
        setCareCenter(careCenter);
    }

    public Vaccination(int id,int profileId, String name, int totalDose, int completeDose, int remainingDose, String nextDate,String careCenter,long eventTime) {
        this(profileId,name,totalDose,completeDose,remainingDose,nextDate,careCenter,eventTime);
        this.id = id;

    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
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

    public int getTotalDose() {
        return totalDose;
    }

    public void setTotalDose(int totalDose) {
        this.totalDose = totalDose;
    }

    public int getCompleteDose() {
        return completeDose;
    }

    public void setCompleteDose(int completeDose) {
        this.completeDose = completeDose;
    }

    public int getRemainingDose() {
        return remainingDose;
    }

    public void setRemainingDose(int remainingDose) {
        this.remainingDose = remainingDose;
    }

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getCareCenter() {
        return careCenter;
    }

    public void setCareCenter(String careCenter) {
        this.careCenter = careCenter;
    }
}
