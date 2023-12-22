package Assignment1PLC23WS;

import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class ArticleCLI {
    public static void main(String[] args) {

try {
    if (args.length < 2) {
        throw new IllegalArgumentException("Error: Invalid parameter.");
    }

} catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
    return;
}


        String filename = args[0];
        String command = args[1];

        // Depending on the specified command, perform the appropriate action
        switch (command) {
            case "add":
                handleAddCommand(filename, args);
                break;
            case "list":
                if (args.length == 2) {
                    handleListAllCommand(filename);
                } 
                else if (args.length == 3) {
                    int id = Integer.parseInt(args[2]);
                    handleListByIdCommand(filename, id);
                }
                break;
            case "delete":
                if (args.length == 3) {
                    int id = Integer.parseInt(args[2]);
                    handleDeleteCommand(filename, id);
                }
                break;
            case "count":
                if (args.length == 2) {
                    handleCountAllCommand(filename);
                } else if (args.length == 3) {
                    String type = args[2];
                    handleCountByTypeCommand(filename, type);
                }
                break;
            case "meanprice":
                handleMeanPriceCommand(filename);
                break;
            case "oldest":
                handleOldestCommand(filename);
                break;
            default:
                System.out.println("Error: Invalid parameter.");
                return;
        }
        

    }

    private static void handleAddCommand(String filename, String[] args) {
        if (args.length < 9) {
            System.out.println("Error: Invalid parameter.");
            return;
        }
        int id;
        String articleType = args[2];
        try{
            id = Integer.parseInt(args[3]);
        }
        catch(IllegalArgumentException e){
            System.out.println("Error: Invalid parameter.");
            return;
        }
        String title = args[4];
        String publisher = args[5];
        int releaseYear;
        double basePrice;

        try{
            releaseYear = Integer.parseInt(args[6]);
            basePrice = Double.parseDouble(args[7]);
        } catch (NumberFormatException e){
            System.out.println("Error: Invalid parameter.");        
            return;
        }

        if (articleType.equals("book")) {
            int pages = Integer.parseInt(args[8]);
            try{
            Article article = new Book(id, title, releaseYear, publisher, basePrice, pages);
            ArticleDAO articleDAO = new SerializedArticleDAO(filename);
            articleDAO.saveArticle(article);

            }
            catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
                return;
            }
        } 
        
        else if (articleType.equals("dvd")) {
            int length = Integer.parseInt(args[8]);
            int ageRating = Integer.parseInt(args[9]);
            try{
            Article article = new DVD(id, title, releaseYear, publisher, basePrice, length, ageRating);
            ArticleDAO articleDAO = new SerializedArticleDAO(filename);
            articleDAO.saveArticle(article);
            
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
                return;
            }
        }
        
        else
            System.out.println("Error: Invalid parameter.");
        
    }

    private static void handleListAllCommand(String filename) {
    	ArticleDAO articleDAO = new SerializedArticleDAO(filename);
        List<Article> articles = articleDAO.getArticleList();
        
        for (Article article : articles) {
            System.out.println(article.toString());
        }
    }

    private static void handleListByIdCommand(String filename, int id) {
    	ArticleDAO articleDAO = new SerializedArticleDAO(filename);
        Article article = articleDAO.getArticle(id);
        try{
        if (article != null)
            System.out.println(article.toString());
        
        else
            throw new Exception("Error: Article not found. (id=" + id + ")");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void handleDeleteCommand(String filename, int id) {
    	ArticleDAO articleDAO = new SerializedArticleDAO(filename);
        articleDAO.deleteArticle(id);
    }

    private static void handleCountAllCommand(String filename) {
    	ArticleDAO articleDAO = new SerializedArticleDAO(filename);
        List<Article> articles = articleDAO.getArticleList();
        System.out.println(articles.size());
    }

    private static void handleCountByTypeCommand(String filename, String type) {
    	ArticleDAO articleDAO = new SerializedArticleDAO(filename);
        List<Article> articles = articleDAO.getArticleList();

        long count = articles.stream()
            .filter(article -> {
                if ("book".equalsIgnoreCase(type)) {
                    return article instanceof Book;
                } 
                
                else if ("dvd".equalsIgnoreCase(type)) {
                    return article instanceof DVD;
                } 
                
                else
                    return false;
                
            })
            .count();

            System.out.println(count);
    }

    private static void handleMeanPriceCommand(String filename) {
    	ArticleDAO articleDAO = new SerializedArticleDAO(filename);
        List<Article> articles = articleDAO.getArticleList();
        
        if (articles.isEmpty()) {
            System.out.println("No articles found.");
            return;
        }
        
        double totalPrice = articles.stream()
            .mapToDouble(Article::getPrice)
            .sum();
        
        double meanPrice = totalPrice / articles.size();

        DecimalFormat df = new DecimalFormat("#.##");
        String roundedPrice = df.format(meanPrice);
        
        System.out.println(roundedPrice);
    }

private static void handleOldestCommand(String filename) {
    ArticleDAO articleDAO = new SerializedArticleDAO(filename);
    List<Article> articles = articleDAO.getArticleList();

    if (articles.isEmpty()) {
        System.out.println("No articles found.");
        return;
    }

    int oldestYear = articles.stream()
        .mapToInt(Article::getReleaseYear)
        .min()
        .getAsInt();

    for (Article article : articles) {
        if (article.getReleaseYear() == oldestYear) {
            System.out.println("Id: " + article.getId());
        }
    }
}

    
}
