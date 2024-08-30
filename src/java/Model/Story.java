package Model;

import java.util.ArrayList;
import java.util.Date;

public class Story {
    private int storyId;
    private String storyName;
    private String description;
    private String authorName;
    private int accountId;
    private String accountName;
    private Date uploadTime;
    private ArrayList<Tag> tag;

    public Story() {
    }

    public Story(String storyName, String description, String authorName, int accountId, Date uploadTime) {
        this.storyName = storyName;
        this.description = description;
        this.authorName = authorName;
        this.accountId = accountId;
        this.uploadTime = uploadTime;
    }

    public Story(int storyId, String storyName, String description, String authorName, ArrayList<Tag> tag) {
        this.storyId = storyId;
        this.storyName = storyName;
        this.description = description;
        this.authorName = authorName;
        this.tag = tag;
    }

    public Story(int storyId, String storyName, String description, String authorName, int accountId, String accountName, Date uploadTime, ArrayList<Tag> tag) {
        this.storyId = storyId;
        this.storyName = storyName;
        this.description = description;
        this.authorName = authorName;
        this.accountId = accountId;
        this.accountName = accountName;
        this.uploadTime = uploadTime;
        this.tag = tag;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public ArrayList<Tag> getTag() {
        return tag;
    }

    public void setTag(ArrayList<Tag> tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Story{" +
                "storyId=" + storyId +
                ", storyName='" + storyName + '\'' +
                ", description='" + description + '\'' +
                ", authorName='" + authorName + '\'' +
                ", accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", uploadTime=" + uploadTime +
                ", tag=" + tag +
                "}\n";
    }
}
