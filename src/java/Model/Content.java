/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class Content {
    private int storyId;
    private String storyName;
    private String content;

    public Content() {
    }

    public Content(int storyId, String storyName, String content) {
        this.storyId = storyId;
        this.storyName = storyName;
        this.content = content;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Content{" + "storyId=" + storyId + ", storyName=" + storyName + ", content=" + content + '}';
    }

    
}
