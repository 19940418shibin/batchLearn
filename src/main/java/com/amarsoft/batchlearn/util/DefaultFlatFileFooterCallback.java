package com.amarsoft.batchlearn.util;

import org.springframework.batch.item.file.FlatFileFooterCallback;

import java.io.IOException;
import java.io.Writer;

/**
 * 在文件尾部写入自定义的内容
 */
public class DefaultFlatFileFooterCallback implements FlatFileFooterCallback {

    @Override
    public void writeFooter(Writer writer) throws IOException {
        writer.write("##Person 20181218 end.");
    }
}
