package org.credit.iframe.util;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class FreeMarkerUtil {
	private static Configuration cfg = null;
	
	private FreeMarkerUtil() {
	}
	
	public static Configuration getInstance(File folder) {
		if(cfg ==null) {
			try {
				//Do not needlessly re-create Configuration instances; 
				//it's expensive, among others because you lose the template cache. 
				//Configuration instances meant to be application-level singletons.
				cfg= new Configuration(Configuration.VERSION_2_3_26);
				//Specify the source where the template files come from.
				cfg.setDirectoryForTemplateLoading(folder);
				cfg.setDefaultEncoding("UTF-8");
				// Sets how errors will appear.
				// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
				cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);//RETHROW_HANDLER
				// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
				cfg.setLogTemplateExceptions(false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return cfg;
	}
	public static UpperCaseDevice getUpperCase() {
		return new FreeMarkerUtil().new UpperCaseDevice();
	}
	public static LowerCaseDevice getLowerCase() {
		return new FreeMarkerUtil().new LowerCaseDevice();
	}
	class UpperCaseDevice implements TemplateDirectiveModel{
		@Override
		public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
				throws TemplateException, IOException {
			// Check if no parameters were given:
	        if (!params.isEmpty()) {
	            throw new TemplateModelException(
	                    "This directive doesn't allow parameters.");
	        }
	        if (loopVars.length != 0) {
	            throw new TemplateModelException(
	                "This directive doesn't allow loop variables.");
	        }
	        // If there is non-empty nested content:
	        if (body != null) {
	            // Executes the nested body. Same as <#nested> in FTL, except
	            // that we use our own writer instead of the current output writer.
	            body.render(new UpperCaseFilterWriter(env.getOut()));
	        } else {
	            throw new RuntimeException("missing body");
	        }
			
		}
	}
	class LowerCaseDevice implements TemplateDirectiveModel{
		@Override
		public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
				throws TemplateException, IOException {
			// Check if no parameters were given:
	        if (!params.isEmpty()) {
	            throw new TemplateModelException(
	                    "This directive doesn't allow parameters.");
	        }
	        if (loopVars.length != 0) {
	            throw new TemplateModelException(
	                "This directive doesn't allow loop variables.");
	        }
	        // If there is non-empty nested content:
	        if (body != null) {
	            // Executes the nested body. Same as <#nested> in FTL, except
	            // that we use our own writer instead of the current output writer.
	            body.render(new LowerCaseFilterWriter(env.getOut()));
	        } else {
	            throw new RuntimeException("missing body");
	        }
			
		}
	}
	class UpperCaseFilterWriter extends Writer{
		private final Writer out;

        UpperCaseFilterWriter (Writer out) {
            this.out = out;
        }
        public void write(char[] cbuf, int off, int len)
                throws IOException {
            char[] transformedCbuf = new char[len];
            for (int i = 0; i < len; i++) {
                transformedCbuf[i] = Character.toUpperCase(cbuf[i + off]);
            }
            out.write(transformedCbuf);
        }
        public void flush() throws IOException {
            out.flush();
        }
        public void close() throws IOException {
            out.close();
        }
	}
	class LowerCaseFilterWriter extends Writer{
		private final Writer out;
		LowerCaseFilterWriter (Writer out) {
            this.out = out;
        }
        public void write(char[] cbuf, int off, int len)
                throws IOException {
            char[] transformedCbuf = new char[len];
            for (int i = 0; i < len; i++) {
                transformedCbuf[i] = Character.toLowerCase(cbuf[i + off]);
            }
            out.write(transformedCbuf);
        }
        public void flush() throws IOException {
            out.flush();
        }
        public void close() throws IOException {
            out.close();
        }
	}
	
}
