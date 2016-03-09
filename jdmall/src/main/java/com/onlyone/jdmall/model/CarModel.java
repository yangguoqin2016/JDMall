package com.onlyone.jdmall.model;

import com.onlyone.jdmall.bean.CarProduct;
import com.onlyone.jdmall.bean.CartBean;
import com.onlyone.jdmall.constance.Serialize;
import com.onlyone.jdmall.utils.FileUtil;
import com.onlyone.jdmall.utils.LogUtil;
import com.onlyone.jdmall.utils.SerializeUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.model
 * 创建者:	LuoDi
 * 创建时间:	3/6/2016 12:42
 * 描述:		${TODO}
 */
public class CarModel {
	private static final String TAG     = "CarModel";
	private static final String CAR_TAG = Serialize.TAG_CAR;
	private static CarModel sInstance;

	private CarModel() {
	}

	public static CarModel getInstance() {
		if (sInstance == null) {
			synchronized (CarModel.class) {
				if (sInstance == null) {
					sInstance = new CarModel();
				}
			}
		}

		return sInstance;
	}

	/**
	 * 添加商品到购物车
	 *
	 * @param userName 添加到指定用户名下的购物车
	 * @param goods    进行添加的商品
	 */
	public synchronized void addToCar(String userName, CarProduct goods) {
		String tag = CAR_TAG + userName;
		HashMap<CarProduct, Integer> result = SerializeUtil.serializeObject(tag);
		if (result == null) {
			result = new HashMap<>();
		}

		int afterCount = 1;
		if (result.containsKey(goods)) {
			//先前购物车里面有该商品了,需要对数量进行增加操作
			afterCount = result.get(goods); //获取该商品先前的数量
			afterCount++;
		}

		result.put(goods, afterCount);

		SerializeUtil.deserializeObject(tag, result);
	}

	public void refreshCar(String userName, HashMap<CarProduct, Integer> data) {
		String tag = CAR_TAG + userName;
		if (data != null) {
			SerializeUtil.deserializeObject(tag, data);
		}
	}

	public void clearCar(String userName) {
		String tag = CAR_TAG + userName;
		File file = new File(FileUtil.getCachePath()+tag);
		if (file.exists()) {
			LogUtil.i(TAG, "存在文件并且已经删除");
			file.delete();
		}
	}

	public HashMap<CarProduct, Integer> transformData(List<CartBean.CartEntity> cartEntities) {
		if (cartEntities == null) {
			return null;
		} else {
			HashMap<CarProduct, Integer> result = new HashMap<>();
			for (CartBean.CartEntity cartEntity : cartEntities) {
				//读取商品的属性
				List<CartBean.CartEntity.ProductEntity.ProductPropertyEntity> productProperty =
						cartEntity.product.productProperty;
				int[] props = new int[productProperty.size()];
				for (int i = 0; i < props.length; i++) {
					props[i] = productProperty.get(i).id;
				}

				//生成一个商品属性
				CarProduct carProduct = new CarProduct(cartEntity.product.id, props);
				//获取对应商品的数量
				int prodNum = cartEntity.prodNum;
				result.put(carProduct, prodNum);
			}
			return result;
		}
	}

	/**
	 * 添加商品到购物车
	 *
	 * @param userName    添加到指定用户名的购物车下
	 * @param productId   商品Id
	 * @param productProp 商品属性
	 */
	public synchronized void addToCar(String userName, int productId, int[] productProp) {
		CarProduct product = new CarProduct(productId, productProp);
		addToCar(userName, product);
	}

	/**
	 * 查询指定用户名下的购物车信息
	 *
	 * @param userName 用户名
	 * @return 包含购物车内商品信息的集合, 指定用户下没有购物车则返回null
	 */
	public synchronized HashMap<CarProduct, Integer> queryCar(String userName) {
		return SerializeUtil.serializeObject(CAR_TAG + userName);
	}
}

