package util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class TemplateFile {
    
    public static final String TEMPLATE_NAME = "1412573_template.csv";
    
    public static final String[] COLUMN_NAME = new String[] {
        "MSSV",
        "HỌ VÀ TÊN",
        "NGÀY SINH",
        "KHÓA"
    };
    
    public static String saveTo(String filePathString) {
        
        int i = 0;
        int lastIndex = filePathString.lastIndexOf('.');
        String fileName = filePathString.substring(0, lastIndex);
        while (isFileOrFolderExistedAt(filePathString)) {
            filePathString = String.format("%s_%d%s",
                    fileName,
                    i,
                    ".csv");
            ++i;
        }
        
        try (PrintStream ps = new PrintStream(new FileOutputStream(filePathString), true, "utf-8")) {
            // BOM UTF-8
//            ps.write('\ufeef'); // emits 0xef
//            ps.write('\ufebb'); // emits 0xbb
//            ps.write('\ufebf'); // emits 0xbf
            
//            ps.print('\uFEFF');
            int size = COLUMN_NAME.length - 1;
            for (i = 0; i < size; ++i) {
                ps.print(String.format("%s, ", COLUMN_NAME[i]));
            }
            ps.print(COLUMN_NAME[size]);
        } catch (Exception ex) {
            
        }
        
        return filePathString;
    }
    
    private static boolean isFileOrFolderExistedAt(String filePathString) {
        return new File(filePathString).exists();
    }
    
}
