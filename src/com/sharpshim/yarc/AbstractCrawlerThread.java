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

package com.sharpshim.yarc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sharpshim.yarc.util.Config;
import com.sharpshim.yarc.util.URLUtil;
import com.sharpshim.yarc.util.UrlQueue;

/**
 * This Abstract crawler is designed for any kind of crawler. <br>
 * It will follow the politeness via IP ticketing
 * 
 * @author sharpshim
 *
 */
public abstract class AbstractCrawlerThread implements Runnable {
	/** Log4j logger */
	private static final Logger logger = LogManager.getLogger(AbstractCrawlerThread.class.getName());
	/** Configurations */
	private static final Config config = Config.getInstance();
	/** This map keep the last timestamp of connecting to each IP */
	private static final Map<String, Long> ipTimeMap = new HashMap<String, Long>();
	/** Crawl Period */
	private static final int crawlPeriod = Integer.parseInt(config.get("CRAWL_PERIOD")); 
	/** The global url Queue which might be shared with other threads */ 
	protected UrlQueue urlQ;
	
	public AbstractCrawlerThread() {}
	
	public AbstractCrawlerThread(UrlQueue q) throws IOException {
		urlQ = q;
	}
	
	@Override
	public void run() {
		while(true) {
			URL url = null;
			synchronized(urlQ) {
				if (urlQ.isEmpty()) {
					if (urlQ.isEnd())
						break;
					else
						continue;
				}
				url = urlQ.poll();
			}
			String ip = null;
			try {
				ip = URLUtil.getIP(url);
				boolean ipTicket;
				synchronized(ipTimeMap) {
					ipTicket = ipTicketing(ip);
				}
				if (ipTicket) {
					try {
						crawlAndWrite(url);
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
				else {
					urlQ.offer(url);
				}
			} catch (InterruptedException | UnknownHostException | MalformedURLException  e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	private boolean ipTicketing(String ip) throws InterruptedException {
		Thread.sleep(1000);
		long curTimeStamp = System.currentTimeMillis();
		Long ipLastTimeStamp = ipTimeMap.get(ip);
		if (ipLastTimeStamp != null && (curTimeStamp - ipLastTimeStamp) < crawlPeriod) {
			return false;
		}
		ipTimeMap.put(ip, curTimeStamp);
		
		return true;
	}
	
	protected abstract void crawlAndWrite(URL url) throws IOException;
}
