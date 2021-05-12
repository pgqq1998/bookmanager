package com.imooc.bookmanager.global;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GlobalController extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
        String[] paths = path.split("/");
        try{
            String controllerStr = null;
            String methodStr = null;

            //    /xxx.do   ----->   DefaultController
            if(paths.length == 2){
                controllerStr = "Default";
                methodStr = paths[1];
            }
            //    /Category/xxx.do
            //    /Book/xxx.do
            //    尚未使用
            else if(paths.length == 3){
                controllerStr = paths[1];
                methodStr = paths[2];
            }
            //    /admin/Category/xxx.do
            //    /admin/Book/xxx.do
            else if(paths.length == 4){
                controllerStr = paths[2];
                methodStr = paths[3];
            }
            //    输入错误
            else{
                throw new PageNotFoundException();
            }
            //    全类名
            controllerStr = "com.imooc.bookmanager.controller." + controllerStr + "Controller";
            //    方法名
            methodStr = methodStr.substring(0,methodStr.lastIndexOf("."));
            //    输出
            System.out.println("controller:"+controllerStr);
            System.out.println("method:"+methodStr);
            //    调用函数
            Class clazz = Class.forName(controllerStr);
            Method method = clazz.getMethod(methodStr,HttpServletRequest.class,HttpServletResponse.class);
            Object obj = clazz.newInstance();
            method.invoke(obj,request,response);
        } catch (PageNotFoundException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
            System.out.println("出错的路径："+path);
            request.getRequestDispatcher("/WEB-INF/pages/404.html").forward(request,response);
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    private class PageNotFoundException extends Exception{
        public PageNotFoundException(){
            super();
        }
        public PageNotFoundException(String info){
            super(info);
        }
        public PageNotFoundException(Exception e){
            super(e);
        }
        public PageNotFoundException(String info,Exception e){
            super(info,e);
        }
    }
}
