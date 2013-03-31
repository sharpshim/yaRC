/*
 * Copyright (C) 2013 Bojun Shim <sragent@gmail.com>
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

package com.sharpshim.yarc.util;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class URLUtil {
	
	public static final String getIP(URL url) throws UnknownHostException, MalformedURLException {
		InetAddress address;
		address = InetAddress.getByName(url.getHost());
		return address.getHostAddress();
	}
	
	public static void main(String[] args) throws MalformedURLException, UnknownHostException {
		System.out.println(URLUtil.getIP(new URL("http://www.example.com")));
	}
}
