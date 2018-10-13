package be.kdg.processor.violation;

import be.kdg.processor.processing.ProcessorListener;

public interface ViolationStrategy extends ProcessorListener {

    void detect();
}
