package com.can.bimuprojects.network.utils;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.can.bimuprojects.network.beans.JsonPost;
import com.can.bimuprojects.network.beans.JsonReceive;

/**
 * Bean类和JsonObject互相转化的工具类
 */
public class JsonParseUtil {

	private static final ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD,JsonAutoDetect.Visibility.ANY);

	/**
	 * 先将request封装成JsonPost，再将JsonPost对象放入JsonObject中
	 * 
	 * @param method
	 *            请求的接口名
	 * @param request
	 *            请求的实体类
	 * @param mContext
	 *            应用的上下文
	 * @return 封装后的JsonObject
	 */
	public static JSONObject beanParseJson(String method, Object request,
			Context mContext) {
		// 实例化一个JsonPost,填充数据
		JsonPost post = new JsonPost(method, mContext, request);
		// 将JsonPost中的所有元素放入json中
		JSONObject json = null;
		try {
			String str = objectMapper.writeValueAsString(post);
			json = new JSONObject(str);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 将JsonObject对象解析为JsonReceive
	 * 
	 * @param json
	 *            服务器返回的JSONObject
	 * @param responses
	 *            响应的实体类class数组
	 * @return 解析出来的JsonReceive
	 */
	public static JsonReceive jsonParseBean(JSONObject json,
			Class<?>... responses) {
		JsonReceive receive = new JsonReceive();
		try {
			receive.setMethod(json.getString("method"));
			receive.setStatus(json.getInt("status"));
			receive.setTimes_used(json.getLong("times_used"));
			receive.setTimestamp(json.getLong("timestamp"));
			receive.setError(json.getString("error"));
			Object obj = null;
			if(responses.length > 0){
				 obj = jsonParseResponse(
							(JSONObject) json.get("response"), responses);
			}else{
				LogUtil.log(LogType.ERROR, JsonParseUtil.class, "未传入响应的Response实体类class对象");
			}
			if(obj==null){

			}else
				receive.setResponse(obj);
		} catch (JSONException e) {
			e.printStackTrace();
			LogUtil.log(LogType.ERROR, JsonParseUtil.class, "响应的json串解析错误");
		}
		return receive;
	}

	/**
	 * 将JsonObject递归放入response中
	 * 
	 * @param json
	 *            要解析的JSONObject
	 * @param responses
	 *            响应的实体类class数组
	 * @return 解析好的response
	 */
	public static Object jsonParseResponse(JSONObject json, Class<?>... responses) {
		Object obj = null;
			try {
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
				obj = objectMapper.readValue(json.toString(), responses[0]);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
		}
		return obj;
	}




}
