package merge_pdf;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.io.RandomAccessStreamCacheImpl;

public class Merge_pdfs
{
    public static void main(String[] args)
    {
        Ejecutar();
    }

    static void Ejecutar()
    {
        String INPUT_PATH = System.getProperty("user.dir") + "\\pdf_In";
        String OUTPUT_PATH = System.getProperty("user.dir") + "\\pdf_Out";

        File dir = new File(INPUT_PATH);
        File[] files = dir.listFiles();
        if (files == null) return;

        Arrays.sort(files, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        PDFMergerUtility pdfMerger = new PDFMergerUtility();
        pdfMerger.setDestinationFileName(OUTPUT_PATH + "\\Out.pdf");

        for (File f : files)
        {
            if (f.getName().endsWith(".pdf"))
            {
                try
                {
                    pdfMerger.addSource(f);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        try
        {
            pdfMerger.mergeDocuments(
                () -> {
                    return new RandomAccessStreamCacheImpl();
                }
            );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
