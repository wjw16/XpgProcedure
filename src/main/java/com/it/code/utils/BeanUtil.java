package com.it.code.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @CopyRight : 华润河南医药有限公司
 * @Version:1.0
 * @Author wenjianwu
 * @Datetime 2019-05-11 10:16
 * @Modor wenjianwu
 * @ModDesc
 */
public class BeanUtil {

    public static <T> T map2Bean(Class<T> target, Map<String, Object> params) {
        T o=null;
        if (target!=null) {
            Class c=o.getClass();
            try {
                o = target.newInstance();
            }
            catch (Exception ex){
                ex.printStackTrace();
                return  null;
            }
            List<Field> fs = getFields(c);
            Set<String> keys = params.keySet();
            Iterator var6 = keys.iterator();

            while(true) {
                while(var6.hasNext()) {
                    String _k = (String)var6.next();
                    Object tvalue = params.get(_k);
                    Iterator var9 = fs.iterator();

                    while(var9.hasNext()) {
                        Field _f = (Field)var9.next();
                        if (!"serialVersionUID".equals(_f.getName()) && _k.equalsIgnoreCase(_f.getName())) {
                            try {
                                _f.setAccessible(true);
                                Class _cf = _f.getType();
                                if (_cf == BigDecimal.class) {
                                    _f.set(o, new BigDecimal(tvalue != null && !"".equals(tvalue) ? tvalue.toString().trim() : "0"));
                                } else if (_cf != Integer.TYPE && _cf != Integer.class) {
                                    if (_cf != Boolean.TYPE && _cf != Boolean.class) {
                                        if (_cf == Character.class) {
                                            _f.set(o, tvalue != null && !"".equals(tvalue) ? tvalue.toString().charAt(0) : "");
                                        } else if (_cf == Short.class) {
                                            _f.set(o, Short.parseShort(tvalue != null && !"".equals(tvalue) ? tvalue.toString().trim() : "0"));
                                        } else if (_cf == Long.class) {
                                            _f.set(o, Long.parseLong(tvalue != null && !"".equals(tvalue) ? tvalue.toString().trim() : "0"));
                                        } else if (_cf == Double.class) {
                                            _f.set(o, Double.parseDouble(tvalue != null && !"".equals(tvalue) ? tvalue.toString().trim() : "0"));
                                        } else if (_cf == Float.class) {
                                            _f.set(o, Float.parseFloat(tvalue != null && !"".equals(tvalue) ? tvalue.toString().trim() : "0"));
                                        } else if (_cf != Byte.class) {
                                            if (_cf == String.class) {
                                                _f.set(o, tvalue == null ? "" : tvalue.toString().trim());
                                            } else if (_cf == Date.class) {
                                                _f.set(o, (Date)tvalue);
                                            } else {
                                                _f.set(o, params.get(_k));
                                            }
                                        } else {
                                            _f.set(o, Byte.parseByte(tvalue != null && !"".equals(tvalue) ? tvalue.toString().trim() : "0"));
                                        }
                                    } else {
                                        _f.set(o, Boolean.parseBoolean(tvalue != null && !"".equals(tvalue) ? tvalue.toString().trim() : ""));
                                    }
                                } else {
                                    _f.set(o, Integer.parseInt(tvalue != null && !"".equals(tvalue) ? tvalue.toString().trim() : "0"));
                                }
                            } catch (Exception var11) {
                                ;
                            }
                            break;
                        }
                    }
                }


            }
        }
        return  o;
    }

    public static List<Field> getFields(Class clazz) {
        List<Field> fList = new ArrayList();
        if (clazz != null && clazz != Object.class) {
            Field[] fs = clazz.getDeclaredFields();
            Field[] var6 = fs;
            int var5 = fs.length;

            for(int var4 = 0; var4 < var5; ++var4) {
                Field f = var6[var4];
                fList.add(f);
            }

            if (clazz != Object.class) {
                fList.addAll(getFields(clazz.getSuperclass()));
            }

            return fList;
        } else {
            return fList;
        }
    }
}
