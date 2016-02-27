package model;

/**
 * Place model
 * @author John Falcon for TDC
 * @version 0.1.1
 */
public class place {

    Integer id;
    String name;
    String latlong;
    String image;
    String video;
    String description;
    Integer group;

    public place( Integer id, String name, String latlong, String image, String video, String description, Integer group ){
        this.setId(id);
        this.setName(name);
        this.setLatlong(latlong);
        this.setImage(image);
        this.setVideo(video);
        this.setDescription(description);
        this.setGroup(group);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }
}
