package com.onlyone.jdmall.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.utils
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 15:19
 * 描述:		关闭流的工具类
 */
public final class IOUtil {
	private IOUtil() {
	}

	/**
	 * 关闭流
	 *
	 * @param io 流对象
	 * @return 返回关闭结果
	 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				LogUtil.e(e);
			}
		}
		return true;
	}
}

