package thebigbang.com.icare.Model;

/**
 * Created by Jakir on 6/19/2015.
 */
public class Prescriptions {
    private int id;
    private int diseasesId;
    private String title;
    private byte[] image;

    public Prescriptions() {
    }

    public Prescriptions(String title, byte[] image) {
        setTitle(title);
        setImage(image);
    }

    public Prescriptions(int diseasesId, String title, byte[] image) {
        setDiseasesId(diseasesId);
        setTitle(title);
        setImage(image);
    }


    public Prescriptions(int id, int diseasesId, String title, byte[] image) {
        this(diseasesId,title,image);
        this.id = id;

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getDiseasesId() {
        return diseasesId;
    }

    public void setDiseasesId(int diseasesId) {
        this.diseasesId = diseasesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
