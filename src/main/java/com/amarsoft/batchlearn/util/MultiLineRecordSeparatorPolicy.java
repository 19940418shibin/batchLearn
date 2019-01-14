package com.amarsoft.batchlearn.util;

import org.springframework.batch.item.file.separator.RecordSeparatorPolicy;

public class MultiLineRecordSeparatorPolicy implements RecordSeparatorPolicy {

    private String delimiter = ",";
    private int count = 0;

    @Override
    public boolean isEndOfRecord(String line) {
        return countDelimiter(line) == count;
    }

    @Override
    public String postProcess(String record) {
        return record;
    }

    @Override
    public String preProcess(String record) {
        return record;
    }

    private int countDelimiter(String s){
        String tmp = s;
        int index = -1;
        int count = 0;
        while((index=tmp.indexOf(","))!=-1){
            tmp = tmp.substring(index+1);
            count++;
        }
        return count;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
