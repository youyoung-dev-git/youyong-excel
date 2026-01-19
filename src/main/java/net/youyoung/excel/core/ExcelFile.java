package net.youyoung.excel.core;

/**
 * Container for Excel file data with filename.
 */
public class ExcelFile {

    private final byte[] content;
    private final String fileName;

    public ExcelFile(byte[] content, String fileName) {
        this.content = content;
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public String getFileName() {
        return fileName;
    }

    public int getSize() {
        return content != null ? content.length : 0;
    }
}
