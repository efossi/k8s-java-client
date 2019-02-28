package com.eliefossi.k8s.k8sclient;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.eliefossi.k8s.k8sclient.exec.ExecClient;
import com.google.common.base.Strings;
import org.springframework.boot.DefaultApplicationArguments;

public class K8sClient {    

    private static final String DEFAULT_NAMESPACE="default";
    private static final String DEFAULT_COMMAND="sh";
    private static final String EXEC_ARG="exec";
    private static final String NAMESPACE_OPTION_ARG="ns";
    private static final String POD_OPTION_ARG="pod";
    private static final String CONTAINER_OPTION_ARG="con";
    private static final String COMMAND_OPTION_ARG="cmd";

    public static void main(String[] args) {
        // SpringApplication.run(K8sClient.class, args);
        try{
            run(new  DefaultApplicationArguments(args));
        }catch(Exception e){
            System.out.println("Error:"+e);
        }
    }
    
    private static void usage(){
        System.out.println("Usage:\n\tjava -jar k8s-client-0.0.1.jar <request-type> [parameter]"
            +" \n\trequest-type: "+EXEC_ARG
            +" \n\t\t--"+NAMESPACE_OPTION_ARG+"=namespace"
            +" \n\t\t--"+POD_OPTION_ARG+"=pod"
            +" \n\t\t--"+CONTAINER_OPTION_ARG+"=container"
            +" \n\t\t--"+COMMAND_OPTION_ARG+"=command --"+COMMAND_OPTION_ARG+"=param1 --"+COMMAND_OPTION_ARG+"=param2 ..."
            );
    }
    
    private static String getFirstOptionValue(String argName,
                        ApplicationArguments args){
        List<String> values=args
                        .getOptionValues(argName);
        if(values!=null && values.size()>0){
            return values.get(0);
        }else{
            return null;
        }

    }

    // @Override
    public static void run(ApplicationArguments args) throws Exception {

        if(args.getNonOptionArgs().size()<1){
            System.out.println("Invalid Parameter: one non-option argument is expected");
            usage();
            System.exit(1);
        }
        if(args.getNonOptionArgs().size()>1){
            System.out.println("Invalid Parameter: Only one non-option argument is supported");
            usage();
            System.exit(1);
        }

        if(args.getNonOptionArgs().contains(EXEC_ARG)){

            String namespace=getFirstOptionValue(NAMESPACE_OPTION_ARG,
                        args);

            if (Strings.isNullOrEmpty(namespace)){
                namespace=DEFAULT_NAMESPACE;
            }

            String pod=getFirstOptionValue(POD_OPTION_ARG,
                        args);

            if (Strings.isNullOrEmpty(pod)){

                System.out.println("Invalid Parameter: --pod is required for this request-type");

                usage();
                System.exit(1);
            }

            String container=getFirstOptionValue(CONTAINER_OPTION_ARG,
                        args);

            List<String> commands=args
                    .getOptionValues(COMMAND_OPTION_ARG);

            if (null==commands || commands.size()==0){
                commands=List.of(DEFAULT_COMMAND);
            }

            new ExecClient().executeRequest(namespace,

                            pod,container,commands);

        }else{

            System.out.println("Invalid request-type:"+ 
                args.getNonOptionArgs());

            usage();
        }

    }

}