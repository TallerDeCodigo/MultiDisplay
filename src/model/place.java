package model;

/**
 * Place model
 * @author John Falcon for TDC
 * @version 0.1.1
 */
public class Place {

    Integer id;
    String name;
    String latlong;
    String image;
    String video;
    String description;
    String details;
    String address;
    String location;
    Integer group;
    Integer order;
    Boolean cluster;

    public Place(Integer id, String name, String latlong, String image, String video, String description, Integer group ){
        this.setId(id);
        this.setName(name);
        this.setLatlong(latlong);
        this.setImage(image);
        this.setVideo(video);
        this.setDescription(description);
        this.setGroup(group);
        setCluster(false);
    }

    public Place(){
        this.setId(null);
        this.setName(null);
        this.setLatlong(null);
        this.setImage(null);
        this.setVideo(null);
        this.setDescription(null);
        this.setDetails(null);
        this.setAddress(null);
        this.setGroup(null);
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

    public Boolean getCluster() {
        return cluster;
    }

    public void setCluster(Boolean cluster) {
        this.cluster = cluster;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
