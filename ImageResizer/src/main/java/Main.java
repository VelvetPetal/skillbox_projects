
import java.io.File;

public class Main {
    public static final int newWidth = 300;

    public static void main(String[] args) {
        String srcFolder = "C:\\Users\\user\\Desktop\\image folder\\src";
        String dstFolder = "C:\\Users\\user\\Desktop\\image folder\\dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();
        assert files != null;
        int cores = 6;
        int part = (int)Math.round((double) files.length / cores);

        for (int i = 0; i < cores - 1; i++) {
            File[] files1 = new File[part];
            System.arraycopy(files, part *  i, files1, 0, files1.length);
            ImageResizer resizer1 = new ImageResizer(files1, newWidth, dstFolder, start);
            resizer1.start();
        }

        File[] files6 = new File[files.length - part * 5];
        System.arraycopy(files, part * 5, files6, 0, files6.length);
        ImageResizer resizer6 = new ImageResizer(files6, newWidth, dstFolder, start);
        resizer6.start();
    }
}
