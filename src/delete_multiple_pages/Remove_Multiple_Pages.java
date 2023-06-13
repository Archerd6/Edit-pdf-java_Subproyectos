package delete_multiple_pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

public class Remove_Multiple_Pages {
    public static void main(String[] args) throws IOException {
        String inputFile = "page_list.txt";
        String inputPath = System.getProperty("user.dir");
        String inputFilePath = inputPath + File.separator + inputFile;
        int numero_de_iteracion = 0;

        File file = new File(inputFilePath);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int page = Integer.parseInt(line.trim());
                    page = page - numero_de_iteracion;
                    execute(page);
                    numero_de_iteracion = numero_de_iteracion + 1;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid page number - " + line);
                }
            }
            reader.close();
        } else {
            System.out.println("Error: File not found - " + inputFilePath);
        }
    }

    static void execute(int page) throws IOException {
        String inputPath = System.getProperty("user.dir") + File.separator + "pdf_In";
        String outputPath = System.getProperty("user.dir") + File.separator + "pdf_Out";
        
        
        File inputDir = new File(inputPath);
        inputDir.mkdirs();
        File outputDir = new File(outputPath);
        outputDir.mkdirs();

        File[] files = inputDir.listFiles();
        if (files != null) {
            for (File inputFile : files) {
                String name = inputFile.getName();
                if (name.endsWith(".pdf")) {
                	
                    PDDocument document = Loader.loadPDF(inputFile);
                    
                    // Listing the number of existing pages
                    int numberOfPages = document.getNumberOfPages();
                    System.out.println("Número de páginas en " + name + ": " + numberOfPages);

                    // Removing the page
                    if (page >= 1 && page <= numberOfPages) {
                        document.removePage(page - 1);
                        System.out.println("Página " + page + " eliminada de " + name);
                    } else {
                        System.out.println("Error: Invalid page number - " + page + " in " + name);
                    }

                    // Saving the modified document with the suffix "_modified"
                    document.save(new File(outputPath + File.separator + name.replace(".pdf", ".pdf")));
                    
                    // Replace
                    FileUtils.copyFile(new File(outputPath + File.separator + name), new File(inputPath + File.separator + name));
                    
                    // Closing the document
                    document.close();
                }
            }
        }
    }
}
