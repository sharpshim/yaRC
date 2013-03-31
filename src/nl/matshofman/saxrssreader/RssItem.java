/*
 * Copyright (C) 2011 Mats Hofman <http://matshofman.nl/contact/>
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
/*
 * Modified by Bojun Shim(sragent@gmail.com)
 * Allowed Multiple dateFormatStrings and added a few formats
 */

package nl.matshofman.saxrssreader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RssItem implements Comparable<RssItem> {

	private RssFeed feed;
	private String title;
	private String link;
	private Date pubDate;
	private String description;
	private String content;
	
	private static final String[] dateFormatStrings 
		= {"EEE, dd MMM yyyy HH:mm:ss Z", "EEE,dd MMM yyyy HH:mm:ss Z",
			"yyyy-MM-dd  HH:mm:ss", "yyyy-MM-dd HH:mm:ss", "EEE dd MMM yyyy HH:mm:ss Z"};
	private static final DateFormat[] dateFormats = new DateFormat[dateFormatStrings.length];
	
	static {
		for (int i = 0; i < dateFormatStrings.length; i++) {
			dateFormats[i] = new SimpleDateFormat(dateFormatStrings[i], Locale.ENGLISH);
		}
	}

	public RssFeed getFeed() {
		return feed;
	}

	public void setFeed(RssFeed feed) {
		this.feed = feed;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public void setPubDate(String pubDate) {
		pubDate = pubDate.trim();
		this.pubDate = null;
		for (DateFormat dateFormat : dateFormats) {
			try {
				this.pubDate = dateFormat.parse(pubDate);
				if (this.pubDate != null)
					break;
			} catch (ParseException e) {
				// do nothing
			}
		}
		if (this.pubDate == null) {
			System.err.println("unparsable pubDate : " + pubDate);
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int compareTo(RssItem another) {
		if(getPubDate() != null && another.getPubDate() != null) {
			return getPubDate().compareTo(another.getPubDate());
		} else { 
			return 0;
		}
	}
	
}
