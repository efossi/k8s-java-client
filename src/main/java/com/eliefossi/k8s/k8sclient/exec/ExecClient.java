package com.eliefossi.k8s.k8sclient.exec;
import java.io.IOException;
import java.util.List;

import com.google.common.io.ByteStreams;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.Exec;
import io.kubernetes.client.util.Config;

public class ExecClient{

public void executeRequest(String namespace,
							String podName, 
							String container,List<String> commands) 
							throws IOException, ApiException, InterruptedException {

    ApiClient client = Config.defaultClient();
    // client.setDebugging(true)
    Configuration.setDefaultApiClient(client);

    Exec exec = new Exec();
    boolean tty = System.console() != null;

    final Process proc =
        exec.exec(
            namespace,
            podName,
            commands.isEmpty()
                ? new String[] {"sh"}
                : commands.toArray(new String[commands.size()]),
            container,
            true,
            tty);

    Thread inThread =
        new Thread(
            new Runnable() {
              public void run() {
                try {
                  ByteStreams.copy(System.in, proc.getOutputStream());
                } catch (IOException ex) {
                  ex.printStackTrace();
                } catch (Exception ex) {
                  ex.printStackTrace();
                }
              }
            });
	    inThread.start();

	  Thread out =
        new Thread(
            new Runnable() {
              public void run() {
                try {
                  ByteStreams.copy(proc.getInputStream(), System.out);
                } catch (IOException ex) {
                  ex.printStackTrace();
                }catch (Exception ex) {
                  ex.printStackTrace();
                }
              }
            });
	    out.start();

	    proc.waitFor();

	    // wait for any last output; no need to wait for input thread
	    out.join();

	    proc.destroy();
	    System.exit(proc.exitValue());
  }
}