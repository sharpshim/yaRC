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

import java.net.URL;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UrlQueue {
	private Queue<URL> q;
	private boolean end;
	
	public UrlQueue() {
		q = new ConcurrentLinkedQueue<URL>();
		end = false;
	}

	public void offer(URL url) {
		q.offer(url);
	}

	public URL poll() {
		return q.poll();
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd() {
		end = true;
	}

	public boolean isEmpty() {
		return q.isEmpty();
	}

}
