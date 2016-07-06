/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.investdata.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.ClassUtils;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * Servlet 2.3/2.4 Filter that allows one to specify a character encoding for
 * requests. This is useful because current browsers typically do not set a
 * character encoding even if specified in the HTML page or form.
 *
 * <p>This filter can either apply its encoding if the request does not
 * already specify an encoding, or enforce this filter's encoding in any case
 * ("forceEncoding"="true"). In the latter case, the encoding will also be
 * applied as default response encoding on Servlet 2.4+ containers (although
 * this will usually be overridden by a full content type set in the view).
 *
 */
public class CharacterEncodingFilter extends OncePerRequestFilter {
	private Logger log = Logger.getLogger(CharacterEncodingFilter.class);
	
	// Determine whether the Servlet 2.4 HttpServletResponse.setCharacterEncoding(String)
	// method is available, for use in the "doFilterInternal" implementation.
	private final static boolean setCharacterEncodingAvailable = ClassUtils.hasMethod(
			HttpServletResponse.class, "setCharacterEncoding", new Class[] {String.class});


	private String encoding;

	private boolean forceEncoding = false;

	private String gbkEncodingUrl;
	


	public void setGbkEncodingUrl(String gbkEncodingUrl) {
		this.gbkEncodingUrl = gbkEncodingUrl;
	}

	/**
	 * Set the encoding to use for requests. This encoding will be passed into a
	 * {@link javax.servlet.http.HttpServletRequest#setCharacterEncoding} call.
	 * <p>Whether this encoding will override existing request encodings
	 * (and whether it will be applied as default response encoding as well)
	 * depends on the {@link #setForceEncoding "forceEncoding"} flag.
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Set whether the configured {@link #setEncoding encoding} of this filter
	 * is supposed to override existing request and response encodings.
	 * <p>Default is "false", i.e. do not modify the encoding if
	 * {@link javax.servlet.http.HttpServletRequest#getCharacterEncoding()}
	 * returns a non-null value. Switch this to "true" to enforce the specified
	 * encoding in any case, applying it as default response encoding as well.
	 * <p>Note that the response encoding will only be set on Servlet 2.4+
	 * containers, since Servlet 2.3 did not provide a facility for setting
	 * a default response encoding.
	 */
	public void setForceEncoding(boolean forceEncoding) {
		this.forceEncoding = forceEncoding;
	}


	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {  
            request.setCharacterEncoding(this.encoding);//设置字符集编码  
            if (this.forceEncoding) {  
                response.setCharacterEncoding(this.encoding);  
            }  
        }  
        filterChain.doFilter(request, response);//激活下一个过滤器  
		
		/*
		if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {
			request.setCharacterEncoding(this.encoding);
			if (this.forceEncoding && setCharacterEncodingAvailable) {
				response.setCharacterEncoding(this.encoding);
			}
		}
		filterChain.doFilter(request, response);
		*/
	}

}
