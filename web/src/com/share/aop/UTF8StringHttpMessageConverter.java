/**
 * 
 */
package com.share.aop;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.FileCopyUtils;

/**
 * AOP： [SpringMVC的@ResponseBody注解,可以将请求方法返回的对象直接转换成JSON对象，但是当返回值是String的时候，中文会乱码]
 *
 * @author Ancai email：ancai0729@gmail.com
 * @since 2012-8-25 下午3:12:10
 * @version 1.0
 */
public class UTF8StringHttpMessageConverter
		extends
			AbstractHttpMessageConverter<String> {
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private final List<Charset> availableCharsets;
	
	private boolean writeAcceptCharset = true;  
	
	public UTF8StringHttpMessageConverter() {  
        super(new MediaType("text", "plain", DEFAULT_CHARSET), MediaType.ALL);  
        this.availableCharsets = new ArrayList<Charset>(Charset.availableCharsets().values());  
    }  
	
	@Override
	protected String readInternal(Class<? extends String> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		// TODO Auto-generated method stub
		Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());  
        return FileCopyUtils.copyToString(new InputStreamReader(inputMessage.getBody(), charset));  
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return String.class.equals(clazz);
	}

	@Override
	protected void writeInternal(String s, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		// TODO Auto-generated method stub
		 if (writeAcceptCharset) {  
            outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());  
        }  
        Charset charset = getContentTypeCharset(outputMessage.getHeaders().getContentType());  
        FileCopyUtils.copy(s, new OutputStreamWriter(outputMessage.getBody(), charset)); 
	}
	
	/** 
     * Indicates whether the {@code Accept-Charset} should be written to any outgoing request. 
     * <p>Default is {@code true}. 
     */  
    public void setWriteAcceptCharset(boolean writeAcceptCharset) {  
        this.writeAcceptCharset = writeAcceptCharset;  
    }
	
    /** 
     * Return the list of supported {@link Charset}. 
     * 
     * <p>By default, returns {@link Charset#availableCharsets()}. Can be overridden in subclasses. 
     * 
     * @return the list of accepted charsets 
     */  
    protected List<Charset> getAcceptedCharsets() {  
        return this.availableCharsets;  
    }  
    
	private Charset getContentTypeCharset(MediaType contentType) {  
        if (contentType != null && contentType.getCharSet() != null) {  
            return contentType.getCharSet();  
        }  
        else {  
            return DEFAULT_CHARSET;  
        }  
    }  

}
