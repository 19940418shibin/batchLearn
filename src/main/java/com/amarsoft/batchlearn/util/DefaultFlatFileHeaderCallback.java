package com.amarsoft.batchlearn.util;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

/**
 * 在文件头部写入自定义的内容
 */
public class DefaultFlatFileHeaderCallback implements FlatFileHeaderCallback {

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write("##Person 20181217 begin.");
    }
}
