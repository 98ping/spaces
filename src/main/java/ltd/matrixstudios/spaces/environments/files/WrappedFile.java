package ltd.matrixstudios.spaces.environments.files;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;

/**
 * Class created on 5/25/2024
 *
 * @author Max C.
 * @project spaces
 * @website https://solo.to/redis
 *
 * Don't even begin to ask why thymeleaf restricts so many IO things
 * in the main java package so we are literally just gonna use this to
 * wrap every file we pass into the site
 */
@Getter @Setter
public class WrappedFile {
    private String name;
    private boolean directory;
    private long lastModified;
    private long size;
    private String fullPath;

    public WrappedFile(File file) {
        this.name = file.getName();
        this.directory = file.isDirectory();
        this.lastModified = file.lastModified();
        this.fullPath = file.getPath();

        try (FileChannel channel = FileChannel.open(file.toPath(), StandardOpenOption.READ)) {
            this.size = channel.size();
        } catch (IOException e) {
            size = -1L;
        }
    }

    public String readableFileSize() {
        if(size <= 0) return "Undefined";
        final String[] units = new String[] { "B", "kB", "MB", "GB", "TB", "PB", "EB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
