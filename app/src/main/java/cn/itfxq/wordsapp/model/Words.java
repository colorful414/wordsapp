package cn.itfxq.wordsapp.model;

import java.time.LocalDateTime;

public class Words {
    private Long id;
    private int imageId;
    private String name;
    private String info;
    private String fy;
    private String category;
    private String imageUrl;
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFy() {
        return fy;
    }

    public void setFy(String fy) {
        this.fy = fy;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Words(Long id, int imageId, String name, String info, String fy, LocalDateTime createTime) {
        this.id = id;
        this.imageId = imageId;
        this.name = name;
        this.info = info;
        this.createTime = createTime;
        this.fy = fy;
    }

    public Words(int imageId, String name, String info) {
        this.imageId = imageId;
        this.name = name;
        this.info = info;
    }

    public Words() {
    }
}
