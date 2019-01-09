package com.tool.slideshowview;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemConfig {
	private static Properties props = null;

	public static String get(String key) {
		if (props == null)
			reloadProps();
		Object o = props.get(key);
		if (o != null)
			return o.toString();
		return null;
	}

	public static long getLong(String key) {
		if (props == null)
			reloadProps();
		Object o = props.get(key);
		if (o != null) {
			try {
				long l = Long.parseLong(o.toString());
				return l;
			} catch (NumberFormatException e) {
			}
		}
		return 0;
	}

	public static void reloadProps() {
		props = new Properties();
		InputStream in = null;
		try {
			in = SystemConfig.class.getClassLoader().getResourceAsStream("config.properties");
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
