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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sharpshim.yarc.RssDocumentCrawlerThread;

public class Config {
	private static final Config INSTANCE = new Config();
	private static final Logger logger = LogManager.getLogger(RssDocumentCrawlerThread.class.getName());
	private Properties props;
	
	private Config() {
		props = new Properties();
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		URL url = cl.getResource("yaRC.conf");
		try {
			BufferedReader br = new BufferedReader(new FileReader(url.getFile()));
			props.load(br);
			br.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			System.exit(-1);
		}
	}
	
	public static Config getInstance() {
		return INSTANCE;
	}
	
	public String get(String key) {
		return props.getProperty(key);
	}
	

}
