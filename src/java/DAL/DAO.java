package DAL;

import Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;

public class DAO {
    public static DAO INSTANCE = new DAO();
    private Connection con;

    private DAO() {
        con = new DBContext().connect;
    }
    
   public ArrayList<Role> loadRole() {
        ArrayList<Role> roleList = new ArrayList<>();

        String sql = "SELECT * FROM Role";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int roleId = rs.getInt("roleID");
                String roleName = rs.getString("roleName");
                roleList.add(new Role(roleId, roleName));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roleList;
   }

    public ArrayList<Account> loadAccount() {
        ArrayList<Account> accountList = new ArrayList<>();

        String sql = "SELECT * FROM Account";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int accountId = rs.getInt("accountID");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int roleId = rs.getInt("roleID");
                accountList.add(new Account(accountId, username, password, roleId));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accountList;
    }
    
    public Account register(String username, String password, int roleId){
        String checkDupedSql = "SELECT * FROM Account WHERE username = ?";
        String registerSql = "INSERT INTO Account(username,[password],roleID) values (?,?,?)";
        String fetchSql = "SELECT * FROM Account WHERE username = ? AND password = ?";
        
        try {
            PreparedStatement dupedPs = con.prepareStatement(checkDupedSql);
            dupedPs.setString(1, username);
            ResultSet dupedRs = dupedPs.executeQuery();
            while (dupedRs.next()) {
                return null;
            }
            
            PreparedStatement registerPs = con.prepareStatement(registerSql);
            registerPs.setString(1, username);
            registerPs.setString(2,password);
            registerPs.setInt(3,roleId);
            registerPs.executeUpdate();
            
            PreparedStatement fetchPs = con.prepareStatement(fetchSql);
            fetchPs.setString(1, username);
            fetchPs.setString(2, password);
            ResultSet rs = fetchPs.executeQuery();
            while (rs.next()) {
                //sua lai cai nay theo db
                int accountId = rs.getInt("accountID");
                return new Account(accountId, username, password, roleId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Account login(String username, String password) {
        String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //sua lai cai nay theo db
                int accountId = rs.getInt("accountID");
                String user = rs.getString("username");
                String pass = rs.getString("password");
                int roleId = rs.getInt("roleID");
                return new Account(accountId, user, pass, roleId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void updateAccount(int accountId, int roleId){
        String sql = "Update Account SET roleID = ? where accountID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, roleId);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteAccount(int accountId){
        String fetchSql = "Select * from Story where accountID = ?";
        
        String deleteSql = "Delete from Account where AccountID = ?";
        try {
            PreparedStatement fetchPs = con.prepareStatement(fetchSql);
            fetchPs.setInt(1, accountId);
            ResultSet fetchRs = fetchPs.executeQuery();
            while(fetchRs.next()){
                int storyId = fetchRs.getInt("storyID");
                DAO.INSTANCE.deleteStory(storyId);
            }
            PreparedStatement deletePs = con.prepareStatement(deleteSql);
            deletePs.setInt(1, accountId);
            deletePs.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    public ArrayList<Story> loadStory() {
        ArrayList<Story> storyList = new ArrayList<>();

        String storySql = "SELECT s.*, a.username AS accountName " +
                          "FROM Story s " +
                          "JOIN Account a ON s.accountID = a.accountID";

        try {
            PreparedStatement psStory = con.prepareStatement(storySql);
            ResultSet rsStory = psStory.executeQuery();

            while (rsStory.next()) {
                int storyId = rsStory.getInt("storyID");
                String storyName = rsStory.getString("storyName");
                String description = rsStory.getString("description");
                String authorName = rsStory.getString("authorName");
                int accountId = rsStory.getInt("accountID");
                String accountName = rsStory.getString("accountName");
                Date uploadTime = rsStory.getDate("uploadTime");

                ArrayList<Tag> tags = getTag(storyId);

                storyList.add(new Story(storyId, storyName, description, authorName, accountId, accountName, uploadTime, tags));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return storyList;
    }

    public Story loadStory(int storyId) {
        Story story = null;

        String storySql = "SELECT s.*, a.username AS accountName " +
                          "FROM Story s " +
                          "JOIN Account a ON s.accountID = a.accountID " +
                          "WHERE storyID = ?";

        try {
            PreparedStatement psStory = con.prepareStatement(storySql);
            psStory.setInt(1, storyId);
            ResultSet rsStory = psStory.executeQuery();

            while (rsStory.next()) {
                String storyName = rsStory.getString("storyName");
                String description = rsStory.getString("description");
                String authorName = rsStory.getString("authorName");
                int accountId = rsStory.getInt("accountID");
                String accountName = rsStory.getString("accountName");
                Date uploadTime = rsStory.getDate("uploadTime");

                ArrayList<Tag> tags = getTag(storyId);

                story = new Story(storyId, storyName, description, authorName, accountId, accountName, uploadTime, tags);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return story;
    }

    public ArrayList<Story> loadStory(String keyword) {
        ArrayList<Story> storyList = new ArrayList<>();

        String storySql = "SELECT s.*, a.username AS accountName " +
                          "FROM Story s " +
                          "JOIN Account a ON s.accountID = a.accountID " +
                          "WHERE storyName LIKE ?";

        try {
            PreparedStatement psStory = con.prepareStatement(storySql);
            psStory.setString(1, "%" + keyword + "%");
            ResultSet rsStory = psStory.executeQuery();

            while (rsStory.next()) {
                int storyId = rsStory.getInt("storyID");
                String storyName = rsStory.getString("storyName");
                String description = rsStory.getString("description");
                String authorName = rsStory.getString("authorName");
                int accountId = rsStory.getInt("accountID");
                String accountName = rsStory.getString("accountName");
                Date uploadTime = rsStory.getDate("uploadTime");

                ArrayList<Tag> tags = getTag(storyId);

                storyList.add(new Story(storyId, storyName, description, authorName, accountId, accountName, uploadTime, tags));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return storyList;
    }

    public ArrayList<Story> loadStory(String keyword, String[] selectedTag) {
        ArrayList<Story> storyList = new ArrayList<>();

        String storySql = "SELECT DISTINCT s.*, a.username AS accountName " +
                          "FROM Story s " +
                          "JOIN Account a ON s.accountID = a.accountID " +
                          "JOIN TagStory ts ON s.storyID = ts.storyID " +
                          "WHERE storyName LIKE ?";

        if (selectedTag != null) {
            storySql += " AND (ts.tagID = ?";
            for (int i = 1; i < selectedTag.length; i++) {
                storySql += " OR ts.tagID = ?";
            }
            storySql += ")";
        }

        try {
            PreparedStatement psStory = con.prepareStatement(storySql);
            psStory.setString(1, "%" + keyword + "%");
            if (selectedTag != null) {
                for (int i = 0; i < selectedTag.length; i++) {
                    psStory.setInt(2 + i, Integer.parseInt(selectedTag[i]));
                }
            }
            ResultSet rsStory = psStory.executeQuery();

            while (rsStory.next()) {
                int storyId = rsStory.getInt("storyID");
                String storyName = rsStory.getString("storyName");
                String description = rsStory.getString("description");
                String authorName = rsStory.getString("authorName");
                int accountId = rsStory.getInt("accountID");
                String accountName = rsStory.getString("accountName");
                Date uploadTime = rsStory.getDate("uploadTime");

                ArrayList<Tag> tags = getTag(storyId);

                storyList.add(new Story(storyId, storyName, description, authorName, accountId, accountName, uploadTime, tags));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return storyList;
    }

public ArrayList<Story> loadStoryAuthor(int accountId) {
        ArrayList<Story> storyList = new ArrayList<>();

        String storySql = "SELECT s.*, a.username AS accountName \n" +
"                          FROM Story s, Account a\n" +
"                          where s.accountID = a.accountID\n" +
"                          and a.accountID = ?";

        try {
            PreparedStatement psStory = con.prepareStatement(storySql);
            psStory.setInt(1,accountId);
            ResultSet rsStory = psStory.executeQuery();

            while (rsStory.next()) {
                int storyId = rsStory.getInt("storyID");
                String storyName = rsStory.getString("storyName");
                String description = rsStory.getString("description");
                String authorName = rsStory.getString("authorName");
                String accountName = rsStory.getString("accountName");
                Date uploadTime = rsStory.getDate("uploadTime");

                ArrayList<Tag> tags = getTag(storyId);

                storyList.add(new Story(storyId, storyName, description, authorName, accountId, accountName, uploadTime, tags));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return storyList;
    }

    //public Story loadStoryAuthor(int storyId, int accountId) {
    //        Story story = null;
    //
    //        String storySql = "SELECT s.*, a.username AS accountName " +
    //                          "FROM Story s, Account a " +
    //                          "WHERE s.accountID = a.accountID and storyID = ? and accountID = ?";
    //
    //        try {
    //            PreparedStatement psStory = con.prepareStatement(storySql);
    //            psStory.setInt(1, storyId);
    //            psStory.setInt(2,accountId);
    //            ResultSet rsStory = psStory.executeQuery();
    //
    //            while (rsStory.next()) {
    //                String storyName = rsStory.getString("storyName");
    //                String description = rsStory.getString("description");
    //                String authorName = rsStory.getString("authorName");
    //                String accountName = rsStory.getString("accountName");
    //                Date uploadTime = rsStory.getDate("uploadTime");
    //
    //                ArrayList<Tag> tags = getTag(storyId);
    //
    //                story = new Story(storyId, storyName, description, authorName, accountId, accountName, uploadTime, tags);
    //            }
    //        } catch (SQLException ex) {
    //            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
    //        }
    //
    //        return story;
    //    }

    public ArrayList<Story> loadStoryAuthor(String keyword,int accountId) {
        ArrayList<Story> storyList = new ArrayList<>();

        String storySql = "SELECT s.*, a.username AS accountName " +
                          "FROM Story s, Account a " +
                          "WHERE s.accountID = a.accountID and storyName LIKE ? and a.accountID = ?";

        try {
            PreparedStatement psStory = con.prepareStatement(storySql);
            psStory.setString(1, "%" + keyword + "%");
            psStory.setInt(2,accountId);
            ResultSet rsStory = psStory.executeQuery();

            while (rsStory.next()) {
                int storyId = rsStory.getInt("storyID");
                String storyName = rsStory.getString("storyName");
                String description = rsStory.getString("description");
                String authorName = rsStory.getString("authorName");
                String accountName = rsStory.getString("accountName");
                Date uploadTime = rsStory.getDate("uploadTime");

                ArrayList<Tag> tags = getTag(storyId);

                storyList.add(new Story(storyId, storyName, description, authorName, accountId, accountName, uploadTime, tags));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return storyList;
    }

    public ArrayList<Story> loadStoryAuthor(String keyword, String[] selectedTag, int accountId) {
        ArrayList<Story> storyList = new ArrayList<>();

        String storySql = "SELECT DISTINCT s.*, a.username AS accountName " +
                          "FROM Story s, Account a, TagStory ts " +
                          "WHERE s.accountID = a.accountID and ts.storyID = s.storyID and a.accountID = ? and storyName LIKE ? ";

        if (selectedTag != null) {
            storySql += " AND (ts.tagID = ?";
            for (int i = 1; i < selectedTag.length; i++) {
                storySql += " OR ts.tagID = ?";
            }
            storySql += ")";
        }

        try {
            PreparedStatement psStory = con.prepareStatement(storySql);
            psStory.setInt(1,accountId);
            psStory.setString(2, "%" + keyword + "%");
            if (selectedTag != null) {
                for (int i = 0; i < selectedTag.length; i++) {
                    psStory.setInt(3 + i, Integer.parseInt(selectedTag[i]));
                }
            }
            
            ResultSet rsStory = psStory.executeQuery();

            while (rsStory.next()) {
                int storyId = rsStory.getInt("storyID");
                String storyName = rsStory.getString("storyName");
                String description = rsStory.getString("description");
                String authorName = rsStory.getString("authorName");
                String accountName = rsStory.getString("accountName");
                Date uploadTime = rsStory.getDate("uploadTime");

                ArrayList<Tag> tags = getTag(storyId);

                storyList.add(new Story(storyId, storyName, description, authorName, accountId, accountName, uploadTime, tags));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return storyList;
    }
    
    public ArrayList<Tag> getTag(){
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        
        String sql = "Select * from Tag";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int tagId = rs.getInt("tagID");
                String tagName = rs.getString("tagName");
                tagList.add(new Tag(tagId, tagName));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tagList;
    }
    
    public ArrayList<Tag> getTag(String[] selectedTag) {
        ArrayList<Tag> tagList = new ArrayList<Tag>();

        // Build the IN clause for SQL query
        StringBuilder tagIds = new StringBuilder();
        for (int i = 0; i < selectedTag.length; i++) {
            if (i > 0) {
                tagIds.append(",");
            }
            tagIds.append(selectedTag[i]);
        }

        String sql = "SELECT * FROM Tag WHERE tagId IN (" + tagIds.toString() + ")";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int tagId = rs.getInt("tagID");
                String tagName = rs.getString("tagName");
                tagList.add(new Tag(tagId, tagName));
            }
            ps.close(); // Close resources properly
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tagList;
    }


    private ArrayList<Tag> getTag(int storyId) {
        ArrayList<Tag> tags = new ArrayList<>();

        String tagSql = "SELECT t.* FROM Tag t " +
                        "JOIN TagStory ts ON t.tagID = ts.tagID " +
                        "WHERE ts.storyID = ?";

        try {
            PreparedStatement psTag = con.prepareStatement(tagSql);
            psTag.setInt(1, storyId);
            ResultSet rsTag = psTag.executeQuery();

            while (rsTag.next()) {
                int tagId = rsTag.getInt("tagID");
                String tagName = rsTag.getString("tagName");
                tags.add(new Tag(tagId, tagName));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tags;
    }

    public void insertStory(Story story, String[] tagId, String content) {
        String addStorySql = "INSERT INTO Story (storyName, description, authorName, accountID, uploadTime) " +
                             "VALUES (?, ?, ?, ?, ?)";

        String insertContentSql = "INSERT INTO Content (storyID, content) VALUES (?, ?)";

        String insertTagsSql = "INSERT INTO TagStory (storyID, tagID) VALUES (?, ?)";
        try {
            // Use RETURN_GENERATED_KEYS to get the auto-generated storyID
            PreparedStatement addStoryPs = con.prepareStatement(addStorySql, Statement.RETURN_GENERATED_KEYS);
            addStoryPs.setString(1, story.getStoryName());
            addStoryPs.setString(2, story.getDescription());
            addStoryPs.setString(3, story.getAuthorName());
            addStoryPs.setInt(4, story.getAccountId());
            addStoryPs.setDate(5, new java.sql.Date(story.getUploadTime().getTime()));
            addStoryPs.executeUpdate();

            // Retrieve the generated storyID
            ResultSet generatedKeys = addStoryPs.getGeneratedKeys();
            int storyId = 0;
            if (generatedKeys.next()) {
                storyId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating story failed, no ID obtained.");
            }

            // Insert tags
            PreparedStatement insertTagPs = con.prepareStatement(insertTagsSql);
            for (String tag : tagId) {
                insertTagPs.setInt(1, storyId);
                insertTagPs.setInt(2, Integer.parseInt(tag));
                insertTagPs.addBatch();
            }
            insertTagPs.executeBatch();

            // Insert content
            PreparedStatement insertContentPs = con.prepareStatement(insertContentSql);
            insertContentPs.setInt(1, storyId);
            insertContentPs.setString(2, content);
            insertContentPs.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void updateStory(Story update, String content) {
        int updateId = update.getStoryId();
        ArrayList<Tag> updateTags = update.getTag();

        String updateSql = "UPDATE Story " +
                           "SET storyName = ?, description = ?, authorName = ? " +
                           "WHERE storyID = ?";

        String deleteTagsSql = "DELETE FROM TagStory WHERE storyID = ?";

        String insertTagsSql = "INSERT INTO TagStory (storyID, tagID) VALUES (?, ?)";
        
        String contentSql = "UPDATE Content SET content = ? WHERE storyID = ?";

        try {
            // Update the Story
            PreparedStatement updatePs = con.prepareStatement(updateSql);
            updatePs.setString(1, update.getStoryName());
            updatePs.setString(2, update.getDescription());
            updatePs.setString(3, update.getAuthorName());
            updatePs.setInt(4, updateId);
            updatePs.executeUpdate();

            // Delete existing tags
            PreparedStatement deletePs = con.prepareStatement(deleteTagsSql);
            deletePs.setInt(1, updateId);
            deletePs.executeUpdate();

            // Insert new tags
            PreparedStatement insertPs = con.prepareStatement(insertTagsSql);
            for (Tag tag : updateTags) {
                insertPs.setInt(1, updateId);
                insertPs.setInt(2, tag.getTagId());
                insertPs.addBatch(); // Add this statement to the batch
            }
            insertPs.executeBatch(); // Execute all statements as a batch
            
            PreparedStatement contentPs = con.prepareStatement(contentSql);
            contentPs.setString(1, content);
            contentPs.setInt(2, updateId);
            contentPs.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Content getContent(int storyId){
        String sql = "Select * from Content c, Story s where c.storyID = ? "
                + "and s.storyID = c.storyID";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, storyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String content = rs.getString("content");
                String storyName = rs.getString("storyName");
                return new Content(storyId, storyName, content);
            }
 
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public void deleteStory(int storyId){
        String deleteContentSql = "delete from Content where storyID = ?";
        
        String deleteTagSql = "delete from TagStory where storyID = ?";
        
        String deleteStorySql = "delete from Story where storyID = ?";
        
        try {
            PreparedStatement deleteContentPs = con.prepareStatement(deleteContentSql);
            deleteContentPs.setInt(1, storyId);
            deleteContentPs.executeUpdate();
            
            PreparedStatement deleteTagPs = con.prepareStatement(deleteTagSql);
            deleteTagPs.setInt(1, storyId);
            deleteTagPs.executeUpdate();
            
            PreparedStatement deleteStoryPs = con.prepareStatement(deleteStorySql);
            deleteStoryPs.setInt(1, storyId);
            deleteStoryPs.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }

    public static void main(String[] args) {
//        ArrayList<Tag> tagList = new ArrayList<Tag>();
//        tagList.add(new Tag(1,"Adventure"));
//        tagList.add(new Tag(6,"Thriller"));
//        Story update = new Story(1,"tag","2","3",tagList);
        System.out.println(DAO.INSTANCE.loadAccount());
//        System.out.println(DAO.INSTANCE.login("Mashiriko", "1234"));
//        System.out.println(DAO.INSTANCE.loadStory()); 
        String[] test = {"6","2"};
//        System.out.println(DAO.INSTANCE.getTag(test));
//        System.out.println(DAO.INSTANCE.loadStory("The",test));
//        Story temp = DAO.INSTANCE.loadStory(1);
//        String content = "Helkp";
//        DAO.INSTANCE.updateStory(update,content);
//        System.out.println(DAO.INSTANCE.loadStory(1));
//        System.out.println(DAO.INSTANCE.getContent(1));
//        DAO.INSTANCE.updateStory(temp,DAO.INSTANCE.getContent(1).getContent());
//        System.out.println(DAO.INSTANCE.loadStory(1));
//        System.out.println(DAO.INSTANCE.getContent(1));
////      DAO.INSTANCE.deleteStory(14);
////      System.out.println(DAO.INSTANCE.loadStory()); 
          System.out.println(DAO.INSTANCE.loadStoryAuthor(2));
//          System.out.println(DAO.INSTANCE.loadStoryAuthor("The", 2));
//          System.out.println(DAO.INSTANCE.loadStoryAuthor("", test, 2));
//          System.out.println(DAO.INSTANCE.register("Dip", "12345", 3));
        DAO.INSTANCE.updateAccount(14, 2);
        System.out.println(DAO.INSTANCE.loadAccount());
        System.out.println(DAO.INSTANCE.loadRole());
        DAO.INSTANCE.deleteAccount(2);
         System.out.println(DAO.INSTANCE.loadStory());
    }
}
