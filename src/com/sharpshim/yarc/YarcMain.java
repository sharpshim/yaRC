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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.sharpshim.yarc.util.Config;
import com.sharpshim.yarc.util.UrlQueue;

public class YarcMain {
	private static final Logger logger = LogManager.getLogger(YarcMain.class.getName());
	private static final Config config = Config.getInstance();

	/**
	 * @param args
	 * @throws IOException
	 * @throws SAXException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) {
		logger.info("Crawler started");
		long start = System.currentTimeMillis();

		try {
			UrlQueue urlQ = new UrlQueue();
			logger.info("Rss url list file: " + config.get("URL_FILE"));
			BufferedReader br = new BufferedReader(new FileReader(config.get("URL_FILE")));
			String line = null;
			while ((line = br.readLine()) != null) {
				try {
					urlQ.offer(new URL(line));
				} catch (MalformedURLException e) {
					logger.error(e.getMessage(), e);
				}
			}
			br.close();
			urlQ.setEnd();

			int threadCount = Integer.parseInt(config.get("THREAD_COUNT"));
			Thread[] threads = new Thread[threadCount];

			for (int i = 0; i < threadCount; i++) {
				Thread thread = new Thread(new RssDocumentCrawlerThread(urlQ));
				threads[i] = thread;
				thread.start();
			}

			for (int i = 0; i < threadCount; i++) {
				threads[i].join();
			}
		}

		catch (IOException | InterruptedException e) {
			logger.error(e.getMessage(), e);
		}

		long end = System.currentTimeMillis();
		logger.info("Crawl ended");
		logger.info("Elapsed time: " + (end - start));
	}
}
