package Assignment1PLC23WS;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializedArticleDAO implements ArticleDAO {
    private String filename;
    private List<Article> articles;

    public SerializedArticleDAO(String filename) {
        this.filename = filename;
        this.articles = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            articles = (List<Article>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error during deserialization.");
            //System.exit(1);
        }
    }

    @Override
    public List<Article> getArticleList() {
    	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Article>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error during deserialization.");
            //System.exit(1);
        }
    	return new ArrayList<>();
    }

    @Override
    public Article getArticle(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null; // Article not found
    }

    @Override
    public void saveArticle(Article article) {
        try (ObjectOutputStream oos = new  ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(articles);
        } catch (IOException e) {
            System.err.println("Error during serialization.");
            //System.exit(1);
        }


        try{
            if(getArticle(article.getId())==null){
                articles.add(article);
                System.out.println("Info: Article " + article.getId() + " added.");
            }
            
            else{
                System.out.println("Error: Article already exists. (id=" + article.getId() + ")");
            }
        }
        catch (RuntimeException e){
            System.err.println("Error during serialization.");
            return;
        }
        writeArticles();
    }

    @Override
    public void deleteArticle(int id) {

        Article article = getArticle(id);
        
        
        if (article != null) {
            articles.remove(article);
            writeArticles();
            System.out.println("Info: Article " + id + " deleted.");
        } else {
            System.out.println("Error: Article not found. (id=" + id + ")");
        	//System.err.println("Error: Article not found. (id="+id+")");
            //System.exit(1);
        }
    }
    

    // Save articles to the file using Java Object Serialization
    private void writeArticles() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(articles);
        } catch (IOException e) {
            System.err.println("Error during serialization.");
            //System.exit(1);
        }
    }
}
    




