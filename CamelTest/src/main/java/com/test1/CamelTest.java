package com.test1;

import java.io.InputStream;
import java.io.OutputStream;

//import javax.jms.Message;
//import javax.jms.MessageListener;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
//import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import scala.actors.threadpool.Arrays;

public class CamelTest
{
   private static Logger log = Logger.getLogger(CamelTest.class.getName());
   
   /*
    https://examples.javacodegeeks.com/enterprise-java/apache-camel/apache-camel-hello-world-example/
    http://camel.apache.org/activemq.html
    http://camel.apache.org/processor.html
    http://fabian-kostadinov.github.io/2016/01/10/reading-from-and-writing-to-files-in-apache-camel/
    */
   public static class MyProcessor implements Processor
   {
      public void process(Exchange exchange) throws Exception
      {
         String tmp = (String)exchange.getIn().getBody(String.class);
         log.info(tmp);
      }
   }
   
   public static class SampleDataConverter implements DataFormat
   {
      @Override
      public void marshal(Exchange exchange, Object obj, OutputStream outStream) throws Exception
      {
         // Instead, use Camel type converters like this:
         String s = exchange.getContext().getTypeConverter().mandatoryConvertTo(String.class, obj);
         
         //get data and write it as bytes to out stream.
         byte[] bytes = s.getBytes();

         IOUtils.write(bytes, outStream);

         // Don't close output stream here
      }

      @Override
      public Object unmarshal(Exchange exchange, InputStream inStream) throws Exception
      {
         byte[] bytes = IOUtils.toByteArray(inStream);
         Arrays.fill(bytes, (byte)-1);
         
         // If we want, we can set the unmarshalled text back into the exchange's out message
         Message out = exchange.getOut();
         out.setBody(bytes);//set data as bytes.

         // Don't close input stream here
         
         return bytes;
      }
   }
   
   public static void main(String[] args) throws Exception
   {
      final String file_path = "C:/Users/menaj/Desktop/mars_workspace/CamelTest/doc/test.txt";
      
      CamelContext context = new DefaultCamelContext();
      log.info("started apache camel context.");
      final String topic = "jimmy.topic1";
      try
      {         
         //context.addComponent("activemq", ActiveMQComponent.activeMQComponent("vm://localhost?broker.persistent=false"));//sample active mq connection.
         context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));//active mq jms connection
         
         context.addRoutes(new RouteBuilder()
         {

            @Override
            public void configure() throws Exception
            {
               MyProcessor processor = new MyProcessor();
               log.info("file path = "+file_path);
               
               from("file://C:/github_repo1/CamelTest/doc/?fileName=test.txt&charset=utf-8&noop=true")
               .unmarshal(new SampleDataConverter())
               .to("file://C:/github_repo1/CamelTest/doc/?fileName=myout.dat")
               .process(processor);
               //.to("stream:out");
               //.to("file://C:/out/?fileName=MyFile.txt&charset=utf-8");
               
               //MyProcessor processor = new MyProcessor();
               //from("activemq:topic:"+topic).to("stream:out");//for jms topic
               //from("activemq:topic:"+topic).process(processor);
               //from("activemq:queue:"+topic).to("stream:out");//for jms queue
               
//               from("timer://myTimer?period=2000")
//               .setBody()
//               .simple("Hello World Camel fired at ${header.firedTime}")
//               .to("stream:out");
            }
         });

         //ProducerTemplate template = context.createProducerTemplate();
         context.start();
         //template.sendBody("activemq:"+topic, "Hello World");
         //while(true)
         {
            Thread.sleep(10000);
         }
      }
      finally
      {
         context.stop();
      }
   }
}
