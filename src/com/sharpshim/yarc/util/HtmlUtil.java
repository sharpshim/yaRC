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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

public class HtmlUtil {
	private static Pattern scriptPattern = Pattern.compile("\\<script.*\\</script\\>", Pattern.DOTALL);
	private static Pattern hiddenPattern = Pattern.compile("\\<div[^>]*hidden.*?\\</div>", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
	
	public static String stripHtml(String html){
		if (html == null)
			return "";
		Matcher matcher = scriptPattern.matcher(html);
		html = matcher.replaceAll("");
		matcher = hiddenPattern.matcher(html);
		html = matcher.replaceAll("");
		html = html.replaceAll("\\<.*?\\>","");
		html = StringEscapeUtils.unescapeHtml4(html);
		html = html.replaceAll("[\n]+", "\n");
		html = clearString(html);
		return html.trim();
	}

	private static String clearString(String str) {
		StringBuffer sb = new StringBuffer();
		for (final Character c: str.toCharArray()) {
			if (c != null && c != '\uFEFF') {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(HtmlUtil.stripHtml("abcdq<div align=asfas Hidden>asfda\\r\\nsfdasf</div>wasfdasf<div>xxxxxxx</div>"));
	}
}
