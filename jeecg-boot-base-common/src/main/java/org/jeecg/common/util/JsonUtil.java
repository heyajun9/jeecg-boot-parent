package org.jeecg.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @author Admin
 * @create 2019-11-12 17:25
 * @desc 自动解析json
 **/
public class JsonUtil {
    //递归遍历解析方法一
    public static boolean iteraJson(String str, Map res){
        //因为json串中不一定有逗号，但是一定有：号，所以这里判断没有则已经value了
        if(str.toString().indexOf(":") == -1){
            return true;
        }
        JSONObject fromObject =  JSONObject.parseObject(str);
        Iterator keys = fromObject.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next().toString();
            Object value = fromObject.get(key);
            if(iteraJson(value.toString(),res)){
                res.put(key, value);
            }
        }
        return false;
    }

    //递归遍历解析方法一
    public static boolean iteraByJson(String str, JSONObject js){
        //因为json串中不一定有逗号，但是一定有：号，所以这里判断没有则已经value了
        if(str.toString().indexOf(":") == -1){
            return true;
        }
        JSONObject fromObject =  JSONObject.parseObject(str);
        Iterator keys = fromObject.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next().toString();
            Object value = fromObject.get(key);
            if(iteraJson(value.toString(),js)){
                js.put(key, value);
            }
        }
        return false;
    }
    //递归遍历解析方法二
    public static boolean iteraJsonOrArray(String source,Map map){
        if(source.indexOf(":") == -1){
            return true;
        }
        JSONObject fromObject =  JSONObject.parseObject(source);
        Iterator keys = fromObject.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next().toString();
            Object value = fromObject.get(key);
            String val = value.toString();
            if(val.indexOf("[{") == -1){
                //说明不存在数组json即格式为："[{" 开头的数据。可以允许是[10,11,12]的非json数组
                if(val.indexOf(":") == -1  || val.matches("[\\d]{4}-[\\d]{2}-[\\d]{2}\\s[\\d]{2}:[\\d]{2}:[\\d]{2}")){
                    //当字符串中不存在：说明已经是值了，如果存在：也可能是日期类型的数据，所以用正则表达式匹配，如果是日期，就直接放入Map中
                        map.put(key, val);
                }else{
                    iteraJson(val,map);
                }
            }else if(val.indexOf("[{") != -1){
                //说明存在数组json即格式为：[{开头的json数组
                if(val.indexOf("[{") == 0){
                    //说明当前value就是一个json数组
                    List list=new ArrayList();
                    Map temMap=null;
                    JSONArray jsonArray = JSONArray.parseArray(val);
                    for(int i=0;i<jsonArray.size();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        temMap=new HashMap<String,Object>();
                        //查看split[i]值
                        iteraJsonOrArray(jsonObject.toString(),temMap);//符合当前递归条件
                        list.add(temMap);
                    }
                    map.put(key,list);
                }else{
                    //说明value可能是一个json，这个json中任然包含数组。例如：{inner:[{a:1,b:2,c:3}]}
                    iteraJsonOrArray(val,map);//符合当前递归条件
                }
            }

        }

        return false;
    }
    //递归遍历解析方法二
    public static boolean iteraJsonOrArrayByJson(String source,JSONObject jsonObject){
        if(source.indexOf(":") == -1){
            return true;
        }
        JSONObject fromObject =  JSONObject.parseObject(source);
        Iterator keys = fromObject.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next().toString();
            Object value = fromObject.get(key);
            String val = value.toString();
            if(val.indexOf("[{") == -1){
                //说明不存在数组json即格式为："[{" 开头的数据。可以允许是[10,11,12]的非json数组
                if(val.indexOf(":") == -1  || val.matches("[\\d]{4}-[\\d]{2}-[\\d]{2}\\s[\\d]{2}:[\\d]{2}:[\\d]{2}")){
                    //当字符串中不存在：说明已经是值了，如果存在：也可能是日期类型的数据，所以用正则表达式匹配，如果是日期，就直接放入Map中
                    jsonObject.put(key,val);
                }else{
                    iteraByJson(val,jsonObject);
                }
            }else if(val.indexOf("[{") != -1){
                //说明存在数组json即格式为：[{开头的json数组
                if(val.indexOf("[{") == 0){
                    //说明当前value就是一个json数组
                    List list=new ArrayList();
                    JSONObject object=null;
                    JSONArray jsonArray = JSONArray.parseArray(val);
                    for(int i=0;i<jsonArray.size();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        object=new JSONObject();
                        //查看split[i]值
                        iteraJsonOrArrayByJson(jsonObject1.toString(),object);//符合当前递归条件
                        list.add(object);
                    }
                    jsonObject.put(key,list);
                }else{
                    //说明value可能是一个json，这个json中任然包含数组。例如：{inner:[{a:1,b:2,c:3}]}
                    iteraJsonOrArrayByJson(val,jsonObject);//符合当前递归条件
                }
            }

        }

        return false;
    }


}
