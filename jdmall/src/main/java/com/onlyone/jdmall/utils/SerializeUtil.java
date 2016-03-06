package com.onlyone.jdmall.utils;

import android.support.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.utils
 * 创建者:	落地开花
 * 创建时间:	3/6/2016 10:32
 * 描述:		序列化保存对象的工具类
 */
public final class SerializeUtil {
	private static final String TAG = "SerializeUtil";

	private SerializeUtil() {
	}

	/**
	 * 将对象反序列化到磁盘进行保存
	 *
	 * @param tag    重新序列化该对象的时候使用的标签
	 * @param target 需要进行反序列化保存的对象
	 */
	public static void deserializeObject(String tag, @NonNull Serializable target) {

		//序列化保存的文件夹是Cache文件夹
		String cachePath = FileUtil.getCachePath();

		ObjectOutputStream objOutput = null;

		try {
			objOutput = new ObjectOutputStream(
					new FileOutputStream(cachePath + tag));

			objOutput.writeObject(target);

		} catch (IOException e) {
			LogUtil.e(TAG, e);
		} finally {
			IOUtil.close(objOutput);
		}

	}

	/**
	 * 根据给定的tag序列化特定的对象
	 *
	 * @param tag 标记对象的标签
	 * @param <T> 序列化的类型
	 * @return 序列化出来的对象, 序列化失败的时候返回null
	 */
	public static <T> T serializeObject(String tag) {
		String cachePath = FileUtil.getCachePath();

		ObjectInputStream objInput = null;

		try {
			objInput = new ObjectInputStream(new FileInputStream(cachePath + tag));

			//noinspection unchecked
			return (T) objInput.readObject();
		} catch (IOException | ClassNotFoundException e) {
			LogUtil.i(TAG, e);
		} finally {
			IOUtil.close(objInput);
		}

		return null;
	}
}
