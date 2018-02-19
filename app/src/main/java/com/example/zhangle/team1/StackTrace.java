package com.example.zhangle.team1;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by zhangle on 21/12/17.
 */

public class StackTrace {
    public static String trace(Exception ex) {
        StringWriter outStream = new StringWriter();
        ex.printStackTrace(new PrintWriter(outStream));
        return outStream.toString();
    }
}
