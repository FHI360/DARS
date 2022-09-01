/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.test;
import java.io.File;
import java.io.IOException;
//import java.nio.file.Paths;
import org.apache.jasper.tagplugins.jstl.core.Redirect;
import org.apache.taglibs.standard.extra.spath.Path;
/**
 *
 * @author smomoh
 */
public class RunCurl 
{
    private static void execute() {
	/**
	* Vars
	*/
	String dest = "2600.com";
	String workingDirectory = System.getProperty("java.io.tmpdir");
	
	/**
	* Paths
	*/
	/*Path out = Paths.get(workingDirectory, new StringBuilder().append(dest).append("_output.txt").toString());
	Path err = Paths.get(workingDirectory, new StringBuilder().append(dest).append("_error.txt").toString());
	File redirectFile = out.toFile();
	File redirectErrorFile = err.toFile();*/
	
	/**
	* @debug
	*/
	//System.out.println("Out @ " + out);
	//System.out.println("Err @ " + err);
	
	/**
	* ProcessBuilder
	*/
	String curlOutFile = new StringBuilder().append(dest).append(".txt").toString();
	ProcessBuilder pb = new ProcessBuilder("curl", "-XGET", dest, "-o", curlOutFile)
	//.inheritIO()
	.directory(new File(workingDirectory));
	//.redirectOutput(Redirect.appendTo(redirectFile))
	//.redirectError(Redirect.appendTo(redirectErrorFile));
	
	/**
	* Invoke process
	*/
	try {
	Process p = pb.start();
	
	//assert pb.redirectInput() == Redirect.PIPE;
	//assert pb.redirectOutput().file() == redirectFile;
	assert p.getInputStream().read() == -1;
	
	int waitFor = p.waitFor();
	
	int exitValue = p.exitValue();
	
	/**
	* @debug
	*/
	System.out.println("Wait -> " + waitFor);
	System.out.println("exitValue -> " + exitValue);
	System.out.println("Ouput saved @ " + workingDirectory);
	
	} catch (IOException e) {
	e.printStackTrace();
	} catch (InterruptedException e) {
	e.printStackTrace();
	}
	}
	
	public static void main(String[] args) {
	execute();
	}
}
