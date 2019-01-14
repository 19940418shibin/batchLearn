package com.amarsoft.batchlearn.util;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.xml.StaxWriterCallback;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * 在输出的xml文件尾部加入内容
 */
public class FooterStaxWriterCallback extends StepExecutionListenerSupport implements StaxWriterCallback{

    private StepExecution stepExecution;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        super.beforeStep(stepExecution);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        return super.afterStep(stepExecution);
    }

    @Override
    public void write(XMLEventWriter xmlEventWriter) throws IOException {
        XMLEventFactory factory = XMLEventFactory.newInstance();
        try {
//            int writeCount = stepExecution.getWriteCount();
//            xmlEventWriter.add(factory.createComment("Total write count = " + writeCount + ";Person 20181224 end."));
            xmlEventWriter.add(factory.createComment("person 20181225 end"));
        }catch (XMLStreamException e){
            e.printStackTrace();
        }
    }

}
