package com.xlteam.socialcaption.external.utility.logger;

public interface LogWriter {
    void v(String msg);

    void d(String msg);

    void i(String msg);

    void w(String msg);

    void e(String msg);

    void beginSection(String methodName);

    void beginSectionAppLog(String methodName);

    void endSection();
}
